import { Button, Container, Flex, FormControl, FormLabel, Grid, HStack, Input, Text, useToast } from '@chakra-ui/react';
import React from 'react';
import { NavLink } from 'react-router-dom';


function Subscribe() {
    const toast = useToast();
    const handleSubmit = (event) => {
        event.preventDefault();
        toast({
            title: 'Successful',
            description: 'Your submission was successful.',
            status: 'success',
            duration: 5000,
            isClosable: true,
          });
    };
    return (
        <Flex align="center" justify="start" h="60vh" direction='column' fontSize='2rem' mt='20px'>
            <Text>Subscribe</Text>
            <Flex align="center" justify="start" h="80vh" direction='row' fontSize='2rem'>
                <form onSubmit={handleSubmit}>
                    <Flex justify='center' alignItems="center">
                        <FormControl isRequired mt="8px">
                            <FormLabel>Email</FormLabel>
                        </FormControl>
                        <FormControl isRequired>
                            <Input width='320' type="email" placeholder="Enter your email" />
                        </FormControl>
                    </Flex>
                    <Button mt='40px' type="submit" onSubmit={handleSubmit}>Submit</Button>
                </form>
            </Flex>
        </Flex>
    );
}

export default Subscribe;