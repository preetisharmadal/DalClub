import { Box, Flex, Text, Icon } from '@chakra-ui/react';
import { FaUser } from 'react-icons/fa';
import React from 'react';
import { NavLink } from 'react-router-dom';
import '../DalClubCommons';

function Navbar() {
  return (

    <Flex as="nav" align="center" justify="space-between"  padding="1rem" bg={global.DalClubCommons.yellow} color={global.DalClubCommons.black} h="8vh">
      <Box>
        <NavLink to='/'>
          <div style={{ fontFamily: 'Pacifico, cursive' }}>
            <Text fontSize='6xl' paddingLeft="1rem" textShadow="2px 2px 6px rgba(28,28,28,0.5)">Dal Clubs</Text>
          </div>
        </NavLink>
      </Box>
      <Flex>
        <Box>
        <Icon as={FaUser} boxSize={6} />
        </Box>
      </Flex>
    </Flex>
  );
}

export default Navbar;
