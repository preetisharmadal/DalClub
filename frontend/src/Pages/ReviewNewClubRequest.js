import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Box, Button, Flex, Text, useToast } from '@chakra-ui/react';
import axios from 'axios';
import Cookies from 'js-cookie';
import React, { useEffect, useState } from 'react';


function ReviewNewClubRequest() {



  const [pendingClubRequest, setPendingClubRequest] = useState([]);
  const jwt = "Bearer " + Cookies.get('jwt');
  const toast = useToast();



  useEffect(() => {

    fetchpendingclubs()
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])


  const fetchpendingclubs = async () => {
    try {
      const response = await axios.get(`/admin/getAllNewClubRequests?requestStatus=PENDING`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': jwt
        }
      });
      const data = await response.data
      console.log(data);
      setPendingClubRequest(data)
    }
    catch (e) {
      console.error(e.message)
    }
  }

  const handleReject = async (requestID) => {
    console.log(requestID)
    try {
      const response = await axios.put(`/admin/rejectClubRequest/${requestID}`, {}, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': jwt
        }
      });
      if (response.status === 200) {
        toast({
          title: 'Successful',
          description: 'CLUB REJECTED SUCCESSFULLY.',
          status: 'success',
          duration: 5000,
          isClosable: true,
        });
        fetchpendingclubs();
      }
      return response.data;

    } catch (error) {
      console.log(error);
      return null;
    }
  }
  const handleApproval = async (requestID) => {

    try {
      const response = await axios.put(`/admin/approveClubRequest/${requestID}`, {}, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': jwt
        }
      });

      if (response.status === 200) {
        toast({
          title: 'Successful',
          description: 'CLUB ACCEPTED SUCCESSFULLY.',
          status: 'success',
          duration: 5000,
          isClosable: true,
        });
      }
      return response.data;

    } catch (error) {
      console.log(error);
      return null;
    }
  }

  return (
    <Flex align="center" justify="start" direction='column' fontSize='2rem' h="90vh">
      <Flex alignItems="end" justifyContent="center" h="20vh" bgColor='black' width='100%' >
        <Flex h="18vh" bgColor='white' width='60%' alignItems='center' justifyContent='center'>
          <Flex h='10vh' bgColor='#ECC94B' width='40%' justifyContent='center' alignItems='center'>
            <Text fontSize='2xl' fontWeight='extrabold'> New Club Request</Text>
          </Flex>
        </Flex>
      </Flex>
      <Flex direction='column' alignItems='center' mt='80px' width='60%'>
        {
          pendingClubRequest.map((pendingClub, ind) => {
            return (<Accordion allowToggle key={ind} width='90%'>
              <AccordionItem>
                <h2>
                  <AccordionButton>
                    <Box as="span" flex='1' textAlign='left'>
                      {pendingClub.clubName}
                    </Box>
                    <AccordionIcon />
                  </AccordionButton>
                </h2>
                <AccordionPanel pb={4} >
                  <Flex direction='column'>
                    <Flex direction='row' justifyContent='space-between'>
                      <Flex width='30%'>
                        <Text fontSize='md'>Club Name:</Text>
                      </Flex>
                      <Flex width='70%'>
                        <Text fontSize='md'>{pendingClub.clubName}</Text>
                      </Flex>
                    </Flex>
                    <Flex direction='row' justifyContent='space-between'>
                      <Flex width='30%'>
                        <Text fontSize='md'>Club Description:</Text>
                      </Flex>
                      <Flex width='70%'>
                        <Text fontSize='md'>{pendingClub.description}</Text>
                      </Flex>
                    </Flex>

                    <Flex direction='row' justifyContent='space-between'>
                      <Flex width='30%'>
                        <Text fontSize='md'>Requestor Email ID:</Text>
                      </Flex>
                      <Flex width='70%'>
                        <Text fontSize='md'>{pendingClub.requesterEmailID}</Text>
                      </Flex>

                    </Flex>
                    <Flex direction='row' justifyContent='space-between'>
                      <Flex width='20%'>
                        <Button mt='40px' type="submit" onClick={() => handleApproval(pendingClub.requestID)} bg="green">Approve</Button>
                      </Flex>
                      <Flex width='80%'>
                        <Button mt='40px' type="submit" onClick={() => handleReject(pendingClub.requestID)} bg="red">Reject</Button>
                      </Flex>
                    </Flex>
                  </Flex>
                </AccordionPanel>
              </AccordionItem>


            </Accordion>)

          })
        }

      </Flex>
    </Flex>
  );
}

export default ReviewNewClubRequest;