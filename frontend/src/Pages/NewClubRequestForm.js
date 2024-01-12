import { Button, Flex, FormControl, FormLabel, Input, Menu, MenuButton, MenuItem, MenuList, Text, useToast } from '@chakra-ui/react';
import axios from 'axios';
import React, { useEffect, useState } from 'react';

import { NavLink } from 'react-router-dom';


function NewClubRequestForm() {
    const toast = useToast();
    const [clubRequest, setClubRequest] = useState({

    });

    console.log(clubRequest)
    const [category, setCategory] = useState([]);

    useEffect(() => {
        const fetchCategories = async () => {
            try {
                const response = await axios.get(`unauthenticated/getAllClubCategory`);
                const data = await response.data
                setCategory(data)
            }
            catch (e) {
                console.error(e.message)
            }
        }
        fetchCategories()

    }, [])
    console.log(category)
    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(`/member/registerClub`, clubRequest);
            if (response.status === 200) {
                toast({
                    title: 'Successful',
                    description: 'Your submission was successful.',
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
        <Flex w="100%" align="center" justify="start" direction='column' fontSize='2rem' h="90vh">
            <Flex alignItems="end" justifyContent="center" h="20vh" bgColor='black' width='100%' >
                <Flex h="18vh" bgColor='white' width='60%' alignItems='center' justifyContent='center'>
                    <Flex h='10vh' bgColor='#ECC94B' width='40%' justifyContent='center' alignItems='center'>
                        <Text fontSize='2xl' fontWeight='extrabold'>CLUB Request FORM</Text>
                    </Flex>
                </Flex>
            </Flex>
            <Flex direction='column' mt='20px'>
                <Text fontSize='md' fontWeight='bold'>Please enter the below details to Register a new club</Text>
                <Flex align="center" justify="start" mt='40px' direction='row' fontSize='2rem'>
                    <form onSubmit={handleSubmit}>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Club Name</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.name}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            name: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Club Name" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Category Name</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Menu>
                                    <MenuButton as={Button} colorScheme="teal">
                                        Select an Option
                                    </MenuButton>
                                    <MenuList>{
                                        category.map((categoryItem) => {
                                            return <MenuItem onClick={() => {
                                                setClubRequest({
                                                    ...clubRequest,
                                                    categoryID: categoryItem.categoryID,
                                                    categoryName: categoryItem.categoryName
                                                })
                                            }} value={categoryItem.categoryID}>{categoryItem.categoryName}</MenuItem>

                                        })

                                    }

                                        {/* Add more MenuItems as needed */}
                                    </MenuList>
                                </Menu>
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Description</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.description}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            description: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter club description" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>President Email ID</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.emailID}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            emailID: event.target.value
                                        })
                                    }} width='320' type="email" placeholder="Enter president's email" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Facebook Link</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.fLink}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            fLink: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Facebook URL" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Instagram Link</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.iLink}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            iLink: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Instagram URL" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Category ID</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.categoryID}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            categoryID: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Category ID" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Location</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.location}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            location: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Location" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Meeting Time</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.meetingTime}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            meetingTime: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Meeting Time" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Club Image</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.clubImage}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            clubImage: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter club image url" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Rules</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubRequest.rules}
                                    onChange={(event) => {
                                        setClubRequest({
                                            ...clubRequest,
                                            rules: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Rule" />
                            </FormControl>
                        </Flex>

          <NavLink to={`/`} >
                        <Button mt='40px' type="submit" onSubmit={handleSubmit}>Submit</Button>
              </NavLink>
                    </form>
                </Flex>

            </Flex>
        </Flex>
    );
}

export default NewClubRequestForm;