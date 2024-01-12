import React from 'react';
import { NavLink, Outlet } from 'react-router-dom';
import { Flex, Box, Text } from '@chakra-ui/react';
import Navbar from '../Components/Navbar';

function LayoutNavbar() {
    return (
        <>
            <header> <Navbar /> </header>

            <Box bg="black"> {/* Add a black background */}
                <Flex as="nav" align="center" justify="space-between" padding="1rem" h="1vh" top="25%" left="0" right="0" color='white'>
                    <Box>
                        <NavLink to='/Login'>
                            <Text color="white">Login</Text>
                        </NavLink>
                    </Box>
                    <Box>
                        <NavLink to='/recommend'>
                            <Text color="white">Recommend</Text>
                        </NavLink>
                    </Box>
                    <Box>
                        <NavLink to='/FindClubs'>
                            <Text color="white">Find Clubs</Text>
                        </NavLink>
                    </Box>
                    <Box>
                        <NavLink to='/FindEvents'>
                            <Text color="white">Find Events</Text>
                        </NavLink>
                    </Box>
                    <Box>
                        <NavLink to='/admin'>
                            <Text color="white">Admin</Text>
                        </NavLink>
                    </Box>
                </Flex>
            </Box>

            <Outlet />
        </>
    );
}

export default LayoutNavbar;