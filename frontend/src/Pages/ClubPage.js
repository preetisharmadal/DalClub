import { Box, Button, Divider, Flex, Icon, Link, Text } from '@chakra-ui/react';
import React, { useEffect, useState } from 'react';
import { BsFacebook, BsInstagram } from 'react-icons/bs';
import { NavLink, useParams } from 'react-router-dom';
import axios from '../axiosConfiguration';
import { getEmailID } from '../hooks/useEmail';
// https://chakra-ui.com/docs/components/link
const fetchClubDetails = async (clubName) => {
  try {
    const response = await axios.get(`/unauthenticated/getClubByName/${clubName}`);
    return response.data[0];
  } catch (error) {
    console.log(error);
    return null;
  }
};

const fetchEventDetailsByClub = async (clubID) => {
  try {
    const response = await axios.get(`/unauthenticated/getEventByClub/${clubID}`);
    return response.data;
  } catch (error) {
    console.log(error);
    return null;
  }
};

// const joinClubHandler = async (clubID, emailID) => {
//   console.log(clubID +" clb user " + emailID);
//   try {
//     console.log(clubID +" clb user " + emailID);
//     const response = await axiosPrivate.get(`/member/joinClub/${clubID}/${emailID}`);
//     return response.data;
//   } catch (error) {
//     console.log(error);
//     return null;
//   }
// };

function ClubPage() {
  const { clubName } = useParams(); 
  const [clubDetails, setClubDetails] = useState([]);
  const [eventDetails, setEventDetails] = useState([]);
  const emailID = getEmailID() || null;
  console.log(emailID);

  useEffect(() => {
    const getClubDetails = async () => {
      const clubDetails = await fetchClubDetails(clubName);
      console.log(clubDetails);
      setClubDetails(clubDetails);

      const eventsResponse = await fetchEventDetailsByClub(clubDetails.clubID)
      console.log(eventsResponse);
      setEventDetails(eventsResponse);
    };
    getClubDetails();
  }, [clubName]); 

  // const toast = useToast();
  // const handleRegistration = (clubDetails, emailID) => {
  //   console.log(emailID + " " + !emailID)
  //   // if (!emailID) {
  //       toast({
  //         title: 'Oops!',
  //         description: 'Unable to register for this club at the given time. Please sign in.',
  //         status: 'error',
  //         duration: 10000,
  //         isClosable: true,
  //       });
        
  //   // }
  //   var response = joinClubHandler(clubDetails.clubID, emailID)
  //   console.log(response);
  //   if (response) {
  //     toast({
  //       title: 'Successful',
  //       description: `Registration request for ${clubDetails.clubName} raised successfully!`,
  //       status: 'success',
  //       duration: 10000,
  //       isClosable: true,
  //     });
  //   } else {
  //     toast({
  //       title: 'Oops!',
  //       description: 'Unable to register for this club at the given time. Please try again later.',
  //       status: 'error',
  //       duration: 10000,
  //       isClosable: true,
  //     });
  //   }
  // };

  return (
    <>
      <Box position="relative" height="20vh">
        <img src={`data:image/png;base64, ${clubDetails.clubImage}`} alt="" style={{ width: '100%', height: '100%', objectFit: 'cover' }} />

        <Box position="absolute" top="50px"  left="50%" transform="translateX(-50%)" width="60%" bg="white" p="20px" rounded="md" h="100%">
          <Flex position='relative' bgColor='white' alignItems='center' justifyContent='center'>
              <Text fontSize='2xl' fontWeight='bold' pt="50px">{clubName.toUpperCase()}</Text>
          </Flex>

        <Box position="relative" width="60%" bg="white" pl="60px" pt="40px" rounded="md" h="100%" >
          <Box>
            <Text fontSize="md">
              {/* <Text display="inline" fontWeight="bold">Club category: </Text> {clubDetails.categoryName} */}
            </Text>
            <Text fontSize="md" pt="50px">
              <Text fontWeight="bold">Overview: </Text>  {clubDetails.description}</Text>

            <Text fontSize="md" pt="20px">
              <Text display="inline" fontWeight="bold">President Email ID: </Text> {clubDetails.presidentEmailID}
            </Text>

            <Text fontSize="md" pt="20px" pb="20px">
              You can reach out to us on our Socials:
            </Text>
            <Text ml="25px" color={global.DalClubCommons.green}>
              <Link href={clubDetails.facebookLink} isExternal display="flex" alignItems="center" align="center">
                <Icon as={BsFacebook} color='blackAlpha.900' boxSize={6} />
                <Text ml={2}>Facebook</Text>
              </Link>
            </Text>
            <Text mt="15px" ml="25px" color={global.DalClubCommons.green}>
              <Link href={clubDetails.instagramLink} isExternal display="flex" alignItems="center" align="center">
                <Icon as={BsInstagram} color='blackAlpha.900' boxSize={6} />
                <Text ml={2}>Instagram</Text>
              </Link>
            </Text>

            <Text fontSize="md" pt="20px">
              <Text fontWeight="bold" pb="20px">Club Meeting details: </Text>
              <Text display="inline" fontWeight="bold">Location: </Text> {clubDetails.location}
            </Text>
              <Text display="inline" fontWeight="bold">Meeting Time: </Text> {clubDetails.meetingTime}
          </Box>

          <Flex mt="50px">
          <NavLink to={`/clubmembershipform`}  style={{ textDecoration: 'none', color: global.DalClubCommons.green }}>

            <Button left="50%" transform="translateX(-50%)" width="200px" color="white" bg={global.DalClubCommons.black}>JOIN CLUB</Button>
              </NavLink>
          </Flex>
          <Text mt="25px" mb="15px" fontWeight="bold">Ongoing and Upcoming Events: </Text>
          <Divider />
          {eventDetails.map((eventDetails, index) => (
          <Box key={index} borderWidth="1px" borderRadius="lg" overflow="hidden" m="20px">
               <Box key={index}  bg="white" p="20px" rounded="md">
               <Text fontSize="14px" color={global.DalClubCommons.gray}>
                {eventDetails.eventTopic}
              </Text>

              <Text fontSize="xl" margin="12px 0" >
                {eventDetails.eventName.toUpperCase()}
              </Text>

              <Text fontSize="md" lineHeight="1.6em" margin="12px 0" color={global.DalClubCommons.textColor} >
                {eventDetails.description}
              </Text>

              <Text fontSize="md" lineHeight="1.6em" margin="12px 0" color={global.DalClubCommons.gray} >
                {eventDetails.startDate}
              </Text>
              <Text fontSize="md" lineHeight="1.6em" margin="12px 0" color={global.DalClubCommons.gray} >
                {eventDetails.startTime} - {eventDetails.endTime}
              </Text>

              <NavLink to={`/event/${eventDetails.eventName}`}  style={{ textDecoration: 'none', color: global.DalClubCommons.green }}>
                <Text fontSize="sm">Learn More {'\u279D'}</Text>
              </NavLink>
          </Box>
          </Box>
        ))}

        <Flex mt="200px" position="relative">
          <Box height="150px"></Box>
        </Flex>
        </Box>
      </Box>
        </Box>

    </>
  );
}

export default ClubPage;