import React, { useState, useEffect } from 'react';
import axios, { axiosPrivate } from '../axiosConfiguration';
import { useParams, NavLink } from 'react-router-dom';
import { Box, Flex, Text, Button, useToast } from '@chakra-ui/react';

const fetchEventDetails = async (eventName) => {
  try {
    const response = await axios.get(`/unauthenticated/getEventDetails/${eventName}`);
    return response.data[0]; 
  } catch (error) {
    console.log(error);
    return null;
  }
};

const registerEventHandler = async (eventID) => {
  try {
    var emailID = "swit@dal.ca";
    const response = await axiosPrivate.post(`/unauthenticated/registerEvents/${eventID}/${emailID}`);
    console.log(response.data);
    return response.data; 
  } catch (error) {
    console.log(error);
    return null;
  }
};

function EventPage() {
  const { eventName } = useParams(); 
  const [eventDetails, setEventDetails] = useState([]);

  const toast = useToast();
  const handleRegistration = async () => {
    const response = await registerEventHandler(eventDetails.eventID);
    if (response) {
        toast({
          title: 'Successful',
          description: 'You have registered for '+eventDetails.eventName+' successfully! See you at the event!',
          status: 'success',
          duration: 10000,
          isClosable: true,
      });
    } else {
      toast({
        title: 'Oops!',
        description: 'Unable to register for this event at the give time. Please try again in some time.',
        status: 'error',
        duration: 10000,
        isClosable: true,
      });
    }
  };

  useEffect(() => {
    const getEventDetails = async () => {
      const details = await fetchEventDetails(eventName);
      setEventDetails(details);
    };
    getEventDetails();
  }, [eventName]); 

  return (
    <>
      <Box position="relative" height="20vh">

        <img src={`data:image/png;base64, ${eventDetails.image}`} alt="" style={{ width: '100%', height: '100%', objectFit: 'cover' }} />

        <Flex position="absolute" top="50%" left="50%" transform="translate(-50%, -50%)">
          <Box bg="white" p="5px" textAlign="center" >
            <Text fontSize="xl" fontWeight="bold">
              {eventName.toUpperCase()}
            </Text>
          </Box>
        </Flex>
      <Box position="relative" left="50%" transform="translateX(-50%)" width="60%" bg="white" p="20px"  rounded="md" h="100%" >
          <Box>
            {/* <Text fontSize="xl" align="center">{eventDetails.eventName.toUpperCase()}</Text> */}
            
            <Text align="center" p="20px" color={global.DalClubCommons.textColor}> {eventDetails.startDate} ({eventDetails.startTime})  <Text display="inline" fontWeight="bold"> to </Text>  {eventDetails.endDate} ({eventDetails.endTime}) </Text>
            <Text fontSize="md">{eventDetails.description}</Text>
            <Text fontSize="md" pt="20px"><Text display="inline" fontWeight="bold">Venue: </Text> {eventDetails.venue}</Text>
            <Text fontSize="md" pt="20px"><Text display="inline" fontWeight="bold">Club Name: </Text>
              <NavLink to={`/club/${eventDetails.clubName}`}  style={{ textDecoration: 'none', color: global.DalClubCommons.green }}>
                {eventDetails.clubID}
              </NavLink>
            </Text>
            <Text fontSize="md" pt="20px"><Text display="inline" fontWeight="bold">Organizer Email ID: </Text>{eventDetails.organizerEmailID}</Text>
            <Text fontSize="md" pt="20px"><Text display="inline" fontWeight="bold">Event Topic: </Text>{eventDetails.eventTopic}</Text>
          </Box>
          
          <Flex mt="50px">
            <Button left="50%" transform="translateX(-50%)" width="200px" onClick={(e) => { e.preventDefault(); handleRegistration(); }} color="white" bg={global.DalClubCommons.black}>REGISTER</Button>
          </Flex>
        </Box>
        <Flex mt="200px" position="relative">
          <Box height="150px"></Box>
        </Flex>
      </Box>
    </>
  );
}

export default EventPage;