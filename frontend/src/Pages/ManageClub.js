import { Accordion, AccordionButton, AccordionIcon, AccordionItem, AccordionPanel, Box, Button, Flex, Text, Toast } from '@chakra-ui/react';
import axios from 'axios';
import Cookies from 'js-cookie';
import React, { useEffect, useState } from 'react';
import { NavLink } from 'react-router-dom';



function ManageClub() {


    const [clubData, setClubsData] = useState([]);
    const jwt = "Bearer " + Cookies.get('jwt');

    useEffect(() => {
        const getAllClubs = async () => {
            try {
                const response = await axios.get('/admin/getAllUpdateCubRlequests?requestStatus=APPROVED', {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': jwt
                    }
                });
                setClubsData(response.data);
            } catch (error) {
                console.log(error);
            }
        };
        getAllClubs();
        // eslint-disable-next-line react-hooks/exhaustive-deps
    }, [])


    const handleDelete = async (clubID) => {

        try {
            const response = await axios.post(`/admin/deleteClub/${clubID}?requestStatus=APPROVED`, {}, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': jwt
                }
            });
            if (response.status === 200) {
                Toast({
                    title: 'Successful',
                    description: 'Your club was successfully deleted.',
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
                        <Text fontSize='2xl' fontWeight='extrabold'> Manage Club </Text>
                    </Flex>
                </Flex>
            </Flex>
            <Flex direction='column' alignItems='center' mt='80px' width='60%'>
                {
                    clubData.map((clubData) => {
                        return (<Accordion allowToggle width='90%'>
                            <AccordionItem>
                                <h2>
                                    <AccordionButton>
                                        <Box as="span" flex='1' textAlign='left'>
                                            {clubData.clubName}
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
                                                <Text fontSize='md'>{clubData.clubName}</Text>
                                            </Flex>
                                        </Flex>
                                        <Flex direction='row' justifyContent='space-between'>
                                            <Flex width='20%'>
                                                <Text fontSize='md'>Club Description:</Text>
                                            </Flex>
                                            <Flex width='80%'>
                                                <Text fontSize='md'>{clubData.description}</Text>
                                            </Flex>
                                        </Flex>
                                        <Flex direction='row' justifyContent='space-between'>

                                            <Flex width='20%'>
                                                <NavLink to="/updateClub/:clubName">
                                                    <Button mt='40px' type="submit" bg="grey">Edit</Button>
                                                </NavLink>
                                            </Flex>

                                            <Flex width='80%'>
                                                <Button mt='40px' type="submit" onClick={() => handleDelete(clubData.clubID)} bg="red">Delete Club</Button>
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

export default ManageClub;