import { Button, Flex, FormControl, FormLabel, Input, Text, useToast } from '@chakra-ui/react';
import axios from 'axios';
import React, { useState } from 'react';



function CreateEventForm() {
    const toast = useToast();
    const [eventData, setEventData] = useState();
    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(`/president/createEvent`, eventData);
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
                        <Text fontSize='2xl' fontWeight='extrabold'>EVENT CREATION</Text>
                    </Flex>
                </Flex>
            </Flex>
            <Flex direction='column' mt='20px'>
                <Text fontSize='md' fontWeight='bold'>Please enter the below details to create an event</Text>
                <Flex align="center" justify="start" mt='40px' direction='row' fontSize='2rem'>
                    <form onSubmit={handleSubmit}>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Event Name</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input
                                    onChange={(event) => {
                                        setEventData({
                                            ...eventData,
                                            eventName: event.target.value
                                        })
                                    }}
                                    width='320'
                                    type="email"
                                    placeholder="Enter the event name"
                                />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Description</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input onChange={(event) => {
                                    setEventData({
                                        ...eventData,
                                        description: event.target.value
                                    })
                                }} width='320' type="email" placeholder="Describe the event" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Location</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input onChange={(event) => {
                                    setEventData({
                                        ...eventData,
                                        location: event.target.value
                                    })
                                }} width='320' type="email" placeholder="Enter the location" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Club Name</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input onChange={(event) => {
                                    setEventData({
                                        ...eventData,
                                        clubName: event.target.value
                                    })
                                }} width='320' type="email" placeholder="Enter the club name" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Event start Date and Time</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input onChange={(event) => {
                                    setEventData({
                                        ...eventData,
                                        startDate: event.target.value
                                    })
                                }} width='320' type="email" placeholder="Enter event start Date and Time" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Event end Date and Time</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input onChange={(event) => {
                                    setEventData({
                                        ...eventData,
                                        startTime: event.target.value
                                    })
                                }} width='320' type="email" placeholder="Enter event end Date and Time" />
                            </FormControl>
                        </Flex>
                        <Flex justify='center' alignItems="center">
                            <FormControl isRequired mt="8px">
                                <FormLabel>Organizer Email ID</FormLabel>
                            </FormControl>
                            <FormControl isRequired>
                                <Input onChange={(event) => {
                                    setEventData({
                                        ...eventData,
                                        email: event.target.value
                                    })
                                }} width='320' type="email" placeholder="Enter organizer's email ID" />
                            </FormControl>
                        </Flex>
                        <Button mt='40px' type="submit" onSubmit={handleSubmit}>Submit</Button>
                    </form>
                </Flex>

            </Flex>
        </Flex>
    );
}

export default CreateEventForm;