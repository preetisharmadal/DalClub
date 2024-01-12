import React, { useState, useEffect } from 'react';
import { Box, Flex, Button, Text, Divider } from '@chakra-ui/react';
import axios from '../axiosConfiguration'
import { NavLink } from 'react-router-dom';

function RecommenderPage() {
  const [categoryData, setCategoryData] = useState([]); 
  const [clubDetails, setClubDetails] = useState([]);
  const [buttonsSelected, setButtonsSelected] = useState([]);
  const [displayButtons, setDisplayButtons] = useState(true);

  const getAllClubCategories = async () => {
    try {
      const response = await axios.get('/unauthenticated/getAllClubCategory');
      setCategoryData(response.data);
      console.log(response.data);
    } catch (error) {
      console.log(error);
    }
  };

  const handleButtonClick = (buttonId) => {
    if (buttonsSelected.includes(buttonId)) {
      setButtonsSelected((prevButtonsSelected) =>
        prevButtonsSelected.filter((id) => id !== buttonId)
      );
    } else {
      if (buttonsSelected.length < 3) {
        setButtonsSelected((prevButtonsSelected) => [...prevButtonsSelected, buttonId]);
      }
    }
  };

  const fetchClubDetails = async () => {
    try {
      setDisplayButtons(false);
      var responses = {};
      for (let i = 0; i < buttonsSelected.length; i++) {
        console.log(buttonsSelected[i]);
        const response = await axios.get(`/unauthenticated/getClubByCategory/${buttonsSelected[i]}`);
        console.log(response.data);
        console.log(responses[buttonsSelected[i]] );
        if (responses[buttonsSelected[i]] == null) {
          responses[buttonsSelected[i]] = [];
        }
        responses[buttonsSelected[i]] = responses[buttonsSelected[i]].concat(response.data);
      }
      console.log(responses);
      setClubDetails(responses);
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getAllClubCategories();
  }, [setButtonsSelected]);

  return (
    <>
      <Box position="relative" height="25vh">
        <img src="dalRecommend.jpg" alt="" style={{ width: '100%', height: '100%', objectFit: 'cover' }} />
        
        <Box position="absolute" top="50px"  left="50%" transform="translateX(-50%)" width="60%" bg="white" p="20px" rounded="md" h="100%">
          
          <Flex position='relative' bgColor='white' alignItems='center' justifyContent='center' mt="50px">
              <Text fontSize='60' fontWeight="thin" >WHAT ARE YOUR INTERESTS ?</Text>
          </Flex>

          {displayButtons && (
            <>
              <Box mt="50px" bg="white" p="20px" rounded="md">
                <Flex position='relative' bgColor='white' >
                    <Text fontSize='md'>Please choose your interests (Maximum 3):</Text>
                </Flex>
              </Box>
              <Box p="4px">
                <Flex flexWrap="wrap" alignItems='center' justifyContent='center'>
                  {categoryData.map((button) => (
                    <Box key={button.categoryID} m={2}>
                      <Button bg={buttonsSelected.includes(button.categoryID) ? global.DalClubCommons.yellow : global.DalClubCommons.black} color={buttonsSelected.includes(button.categoryID) ? global.DalClubCommons.black : 'white'} onClick={() => handleButtonClick(button.categoryID)}>
                        {button.categoryName}
                      </Button>
                    </Box>
                  ))}

                </Flex>
              </Box>
              <Flex mt="80px" alignItems='center' justifyContent='center'>
                <Button bg={global.DalClubCommons.yellow} color={global.DalClubCommons.black} onClick={fetchClubDetails}>
                  CONTINUE
                </Button>
              </Flex>
            </> 
          )}

          {!displayButtons && (
            <Box p={4} mt="50px">
              <Text>
                Here is the list of recommended clubs:
              </Text>
              {Object.entries(clubDetails).map(([categoryID, clubs]) => (
                <Box key={categoryID}>
                  <Text fontSize="md" fontWeight="bold" my="10" textAlign="left">
                    {categoryData.find((button) => button.categoryID === categoryID)?.categoryName}:
                  </Text>
                  <Flex mt="15px">
                    <Flex flexWrap="wrap" alignItems="center" justifyContent="center">
                      {clubs.map((club) => (
                        <Box key={club.clubID} m="5px" >
                          <NavLink to={`/club/${club.clubName}`} style={{ textDecoration: 'none', color: global.DalClubCommons.green }}>
                            <Button bg={global.DalClubCommons.black} color="white" fontWeight="thin">
                              {club.clubName}
                            </Button>
                          </NavLink>
                        </Box>
                      ))}
                    </Flex>
                  </Flex>
                </Box>
              ))}
              <Box mt="60px" display="flex" alignItems="center" justifyContent="center"></Box>
              <Divider />
              <Box mt="60px" display="flex" alignItems="center" justifyContent="center">
                <Text fontSize="md" >
                  Couldn't find your club of Interest?
                </Text>
                <NavLink to={`/FindClubs`}>
                  <Button bg={global.DalClubCommons.yellow} color={global.DalClubCommons.black} ml="2">
                    FIND YOUR CLUB
                  </Button>
                </NavLink>
              </Box>
              <Box mt="20px" display="flex" alignItems="center" justifyContent="center">
                <Text fontSize="md" alignItems="right" justifyContent="right">
                  Couldn't find your society of Interest?
                  <br />
                  Take the initiative and create your own!
                </Text>
                <NavLink to={`/newclubrequestform`}>
                  <Button bg={global.DalClubCommons.yellow} color={global.DalClubCommons.black} ml="2">
                    CREATE YOUR CLUB 
                  </Button>
                </NavLink>
              </Box>
            </Box>
          )}
        </Box>
      </Box>
    </>
  );
}
  
export default RecommenderPage;