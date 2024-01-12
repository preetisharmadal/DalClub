import { Container } from '@chakra-ui/react';
import React from 'react';
import { Outlet } from 'react-router-dom';
import Navbar from '../Components/navbar';


function Layout() {
  return (
    <>
        <header>
            <Navbar />
        </header>
        <Outlet />
    </>
  );
}

export default Layout;