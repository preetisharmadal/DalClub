import { Button, Flex, Text } from '@chakra-ui/react';
import React from 'react';
import { NavLink } from 'react-router-dom';



function Admin() {
  return (
    <Flex align="center" justify="start" direction='column' fontSize='2rem' h="90vh">
      <Flex alignItems="end" justifyContent="center" h="20vh" bgColor='black' width='100%' >
        <Flex h="18vh" bgColor='white' width='60%' alignItems='center' justifyContent='center'>
          <Flex h='10vh' bgColor='#ECC94B' width='40%' justifyContent='center' alignItems='center'>
            <Text fontSize='2xl' fontWeight='extrabold'> MANAGE CLUBS</Text>
          </Flex>
        </Flex>
      </Flex>
      <Flex direction='column' mt='80px'>
        <Text fontSize='md'>Manage New Club Requests:</Text>
        <NavLink to='/admin/reviewnewclubrequest'>
          <Button bgColor='black' mt='20px' color='white'>New Club Request</Button>
        </NavLink>
        <Text fontSize='md' mt='40px'>Manage Existing Clubs:</Text>
        <NavLink to='/admin/manageclub'>
          <Button bgColor='black' mt='20px' color='white'>Manage Clubs</Button>
        </NavLink>
        <Text fontSize='md' mt='40px'>Review Club Update Requests:</Text>
        <NavLink to='/admin/reviewclubupdaterequest'>
          <Button bgColor='black' mt='20px' color='white'>Club Update Requests</Button>
        </NavLink>
      </Flex>
    </Flex>
  );
}

export default Admin;