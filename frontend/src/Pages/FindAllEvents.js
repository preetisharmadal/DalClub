import React, { useState, useEffect } from 'react';
import { Box, Flex, Button, Input, Text } from '@chakra-ui/react';
import axios from '../axiosConfiguration'
import { NavLink } from 'react-router-dom';

function FindAllEvents() {
  const [searchQuery, setSearchQuery] = useState('');
  const [eventsData, setEventsData] = useState([]); 

  const getAllEvents = async () => {
    try {
      const response = await axios.get('/unauthenticated/getAllEvents');
      console.log(response.data); 
      setEventsData(response.data); 
    } catch (error) {
      console.log(error);
    }
  };

  const findEventByName = async () => {
    try {
      console.log('/getEventDetails/'+searchQuery);
      const response = await axios.get('/unauthenticated/getEventDetails/'+searchQuery);
      console.log(response.data);  
      setEventsData(response.data); 
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    console.log('useEffect called');
    getAllEvents();
  }, []); 

  const setSearchQueryValue = (event) => {
      setSearchQuery(event.target.value);
  };

  return (
    <>
      <Box position="relative">
        <img src="dalBackground.png" alt="" style={{ width: '100%', height: '400px', objectFit: 'cover' }} />
        
        <Box position="absolute" top="50px" left="50%" transform="translateX(-50%)" width="60%" bg="white" p="20px"  rounded="md" h="100%">
          <Flex position='relative' bgColor='white' alignItems='center'  justifyContent='center'>
            <Flex bgColor={global.DalClubCommons.yellow} pl="1" pr="1">
              <Text fontSize='2xl' fontWeight='bold'>FIND EVENTS</Text>
            </Flex>
          </Flex>

          <Flex position='relative'>
            <Box bg="white" p={4}  display="flex" flex="1" w="100%">
              <Flex align="center" width="100%" rounded="md">
                <Input type="text" placeholder="Enter event keywords..." value={searchQuery} onChange={setSearchQueryValue} mr={2}/>
              </Flex>
              <Button onClick={findEventByName} color="white" bg={global.DalClubCommons.black}>SEARCH</Button>
            </Box>
          </Flex>

          {eventsData.map((eventObj, index) => (
            <Box key={index} mt="20px" bg="white" p="20px" rounded="md">
              <Text fontSize="14px" color="rgba(26,26,26,.4)"> 
                {eventObj.eventTopic}
              </Text>

              <Text fontSize="xl" margin="12px 0" > 
                {eventObj.eventName.toUpperCase()}
              </Text>
              
              <Text fontSize="md" lineHeight="1.6em" margin="12px 0" color={global.DalClubCommons.textColor} >
                {eventObj.description}
              </Text>

              <NavLink to={`/event/${eventObj.eventName}`}  style={{ textDecoration: 'none', color: global.DalClubCommons.green }}>
                <Text fontSize="sm">Read More {'\u279D'}</Text>
              </NavLink>
            </Box>
          ))}
        </Box>
      </Box>
    </>
  );
}
  
export default FindAllEvents;