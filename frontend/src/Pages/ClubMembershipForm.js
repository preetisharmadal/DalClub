import { Button, Flex, FormControl, FormLabel, Input, Menu, MenuButton, MenuItem, MenuList, Text, useToast } from '@chakra-ui/react';
import axios from 'axios';
import React, { useState } from 'react';



function ClubMembershipForm() {
    const toast = useToast();
    const [clubMembership, setClubMembership] = useState({
        name: "",
        firstName: "",
        lastName: "",
        emailID: "",
    });
    const roles = ["admin", "president", "member"]

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(`/unauthenticated/registerMember`, clubMembership);
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
        <Flex align="center" justify="start" direction='column' fontSize='2rem' h="90vh">
            <Flex alignItems="end" justifyContent="center" h="20vh" bgColor='black' width='100%' >
                <Flex h="18vh" bgColor='white' width='60%' alignItems='center' justifyContent='center'>
                    <Flex h='10vh' bgColor='#ECC94B' width='40%' justifyContent='center' alignItems='center'>
                        <Text fontSize='2xl' fontWeight='extrabold'>CLUB MEMBERSHIP FORM</Text>
                    </Flex>
                </Flex>
            </Flex>
            <Flex direction='column' mt='20px'>
                <Text fontSize='md' fontWeight='bold'>Please enter the below details to become a club member</Text>
                <Flex align="center" justify="start" mt='40px' direction='row' fontSize='2rem'>
                    <form onSubmit={handleSubmit}>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Email ID</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubMembership.emailID}
                                    onChange={(event) => {
                                        setClubMembership({
                                            ...clubMembership,
                                            emailID: event.target.value
                                        })
                                    }} width='320' type="email" placeholder="Enter your email" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>First Name</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubMembership.firstName}
                                    onChange={(event) => {
                                        setClubMembership({
                                            ...clubMembership,
                                            firstName: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter First Name" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Last Name</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubMembership.lastName}
                                    onChange={(event) => {
                                        setClubMembership({
                                            ...clubMembership,
                                            lastName: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Last Name" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Member Type</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Menu>
                                    <MenuButton as={Button} colorScheme="teal">
                                        Select an Option
                                    </MenuButton>
                                    <MenuList>{
                                        roles.map((roleItem) => {
                                            return <MenuItem onClick={() => {
                                                setClubMembership({
                                                    ...clubMembership,
                                                    memberType: roleItem.memberType
                                                })
                                            }} value={roleItem}>{roleItem}</MenuItem>

                                        })

                                    }

                                        {/* Add more MenuItems as needed */}
                                    </MenuList>
                                </Menu>
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Program</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubMembership.program}
                                    onChange={(event) => {
                                        setClubMembership({
                                            ...clubMembership,
                                            program: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Club Name" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Term</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubMembership.term}
                                    onChange={(event) => {
                                        setClubMembership({
                                            ...clubMembership,
                                            term: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Club Name" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Mobile No</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubMembership.mobile}
                                    onChange={(event) => {
                                        setClubMembership({
                                            ...clubMembership,
                                            mobile: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Club Name" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Date of Birth</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubMembership.dob}
                                    onChange={(event) => {
                                        setClubMembership({
                                            ...clubMembership,
                                            dob: event.target.value
                                        })
                                    }} width='320' type="text" placeholder="Enter Club Name" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Password</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input value={clubMembership.password}
                                    onChange={(event) => {
                                        setClubMembership({
                                            ...clubMembership,
                                            password: event.target.value
                                        })
                                    }} width='320' type="password" placeholder="Enter Club Name" />
                            </FormControl>
                        </Flex>

                        <Button mt='40px' type="submit" onSubmit={handleSubmit}>Submit</Button>
                    </form>
                </Flex>

            </Flex>
        </Flex>
    );
}

export default ClubMembershipForm;