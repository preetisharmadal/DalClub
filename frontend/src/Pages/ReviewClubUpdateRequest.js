import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Box, Button, Flex, Text, Toast } from '@chakra-ui/react';
import axios from 'axios';
import Cookies from 'js-cookie';
import React, { useEffect, useState } from 'react';



function ReviewClubUpdateRequest() {



    const [updateClubRequest, setUpdateClubRequest] = useState([]);
    const jwt = "Bearer " + Cookies.get('jwt');

    useEffect(() => {
        const fetchpendingclubs = async () => {
            try {
                const response = await axios.get(`/admin/getAllUpdateClubRequests?requestStatus=PENDING`, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': jwt
                    }
                });
                const data = await response.data
                setUpdateClubRequest(data)
            }
            catch (e) {
                console.error(e.message)
            }
        }
        fetchpendingclubs()
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])

    const handleReject = async (requestID) => {

        try {
            const response = await axios.put(`/admin/rejectClubRequest/${requestID}`, {}, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': jwt
                }
            });
            if (response.status === 200) {
                Toast({
                    title: 'Successful',
                    description: 'CLUB REJECTED SUCCESSFULLY.',
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
    const handleApproval = async (requestID) => {

        try {
            const response = await axios.put(`/admin/approveClubRequest/${requestID}`, {}, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': jwt
                }
            });
            if (response.status === 200) {
                Toast({
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
                        <Text fontSize='2xl' fontWeight='extrabold'> Club Update Request</Text>
                    </Flex>
                </Flex>
            </Flex>
            <Flex direction='column' alignItems='center' mt='80px' width='60%'>
                {
                    updateClubRequest.map((pendingClub) => {
                        return (<Accordion allowToggle width='90%'>
                            <AccordionItem>
                                <h2>
                                    <AccordionButton>
                                        <Box as="span" flex='1' textAlign='left'>
                                            {updateClubRequest.clubName}
                                        </Box>
                                        <AccordionIcon />
                                    </AccordionButton>
                                </h2>
                                <AccordionPanel pb={4} >
                                    <Flex direction='column'>
                                        <Flex direction='row' justifyContent='space-between'>
                                            <Flex width='20%'>
                                                <Text fontSize='md'>Club Name:</Text>
                                            </Flex>
                                            <Flex width='80%'>
                                                <Text fontSize='md'>{updateClubRequest.clubName}</Text>
                                            </Flex>
                                        </Flex>
                                        <Flex direction='row' justifyContent='space-between'>
                                            <Flex width='20%'>
                                                <Text fontSize='md'>Club Description:</Text>
                                            </Flex>
                                            <Flex width='80%'>
                                                <Text fontSize='md'>{updateClubRequest.description}</Text>
                                            </Flex>
                                        </Flex>

                                        <Flex direction='row' justifyContent='space-between'>
                                            <Flex width='20%'>
                                                <Text fontSize='md'>Requestor Email ID:</Text>
                                            </Flex>
                                            <Flex width='80%'>
                                                <Text fontSize='md'>{updateClubRequest.requesterEmailID}</Text>
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

export default ReviewClubUpdateRequest;