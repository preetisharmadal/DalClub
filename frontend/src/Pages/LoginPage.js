import { useRef, useState, useEffect } from 'react';
import useAuth from "../hooks/useAuth";
import axios from '../axiosConfiguration';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { Box, Flex, Input, Button, Text, Checkbox } from '@chakra-ui/react';

const LOGIN_URL = '/unauthenticated/login';

const Login = () => {
  const { setAuth, persist, setPersist } = useAuth();

  const navigate = useNavigate();
  const location = useLocation();
  const from = location.state?.from?.pathname || "/";

  const userRef = useRef();
  const errRef = useRef();

  const [user, setUser] = useState('');
  const [pwd, setPwd] = useState('');
  const [errMsg, setErrMsg] = useState('');

  useEffect(() => {
    userRef.current.focus();
  }, []);

  useEffect(() => {
    setErrMsg('');
  }, [user, pwd]);

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.post(
        LOGIN_URL,
        JSON.stringify({ emailID: user, password: pwd }),
        {
          headers: { 'Content-Type': 'application/json' },
          withCredentials: true,
        }
      );
      console.log(JSON.stringify(response?.data));
      //console.log(JSON.stringify(response));
      const accessToken = response?.data?.accessToken;
      const roles = response?.data?.roles;
      setAuth({ user, roles, accessToken });
      setUser('');
      setPwd('');
      navigate(from, { replace: true });
    } catch (err) {
      if (!err?.response) {
        console.log(err);
        setErrMsg('No Server Response');
      } else if (err.response?.status === 400) {
        setErrMsg('Missing Username or Password');
      } else if (err.response?.status === 401) {
        setErrMsg('Unauthorized');
      } else {
        setErrMsg('Login Failed');
      }
      errRef.current.focus();
    }
  };

  const togglePersist = () => {
    setPersist((prev) => !prev);
  };

  useEffect(() => {
    localStorage.setItem('persist', persist);
  }, [persist]);

  return (
    <Box p="20px">
      <p ref={errRef} color="red" aria-live="assertive">
        {errMsg}
      </p>
      <Text as="h1" fontSize="3xl" fontWeight="bold">
        Sign In
      </Text>
      <form onSubmit={handleSubmit}>
        <Flex direction="column" mt="4">
          <label htmlFor="username">Username:</label>
          <Input
            type="text"
            id="username"
            ref={userRef}
            autoComplete="off"
            onChange={(e) => setUser(e.target.value)}
            value={user}
            required
          />

          <label htmlFor="password" mt="4">Password:</label>
          <Input
            type="password"
            id="password"
            onChange={(e) => setPwd(e.target.value)}
            value={pwd}
            required
          />
          <Button type="submit" mt="4" colorScheme="blue">
            Sign In
          </Button>
          <Flex align="center" mt="4">
            <Checkbox
              id="persist"
              onChange={togglePersist}
              isChecked={persist}
              colorScheme="blue"
            />
            <label htmlFor="persist" ml="2">Trust This Device</label>
          </Flex>
        </Flex>
      </form>
      <Text mt="4">
        Need an Account? 
        <span>
          <Link to="/register" color="blue.500" ml="2">
            Sign Up
          </Link>
        </span>
      </Text>
    </Box>
  );
};

export default Login;
