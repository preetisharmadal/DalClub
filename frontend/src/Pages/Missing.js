import { Link } from 'react-router-dom';
import { Box, Text, Link as ChakraLink } from '@chakra-ui/react';

const Missing = () => {
  return (
    <Box p="100px">
      <Text fontSize="6xl" mb="4">
        OOPS!
      </Text>
      <Text fontSize="xl" mb="4">
        Unfortunately! You are trying to find a page that does not exist!
      </Text>
      <Box flexGrow={1}>
        <ChakraLink as={Link} to="/" color="blue.500">
          Visit Our Homepage
        </ChakraLink>
      </Box>
    </Box>
  );
};

export default Missing;
