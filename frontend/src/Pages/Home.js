import { Box } from '@chakra-ui/react';
import '../DalClubCommons';
import useLogout from '../hooks/useLogout';

import { useNavigate } from 'react-router-dom';
function Home() {
  console.log("In home")
  const navigate = useNavigate();
  const logout = useLogout();

    const signOut = async () => {
        await logout();
        navigate('/');
    }
  return (
    <>
      <Box position="relative">
        <img src="dalBackground.png" alt="" style={{ width: '100%', height: '400px', objectFit: 'cover' }} />
      </Box>
      <div className="flexGrow">
        <button onClick={signOut}>Sign Out</button>
      </div>
    </>
  );
}

export default Home;
