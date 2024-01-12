import React, { useState, useEffect} from 'react';
import { Box, Flex, Text, Input, Button, Checkbox, useToast } from '@chakra-ui/react';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router';
import axios, { axiosPrivate } from '../axiosConfiguration';

function UpdateClubDetails() {
  const { clubNameParam } = useParams(); 
  const [clubDetails, setClubDetails] = useState([]); 
  
  const [loading, setLoading] = useState(true);

  const [clubName, setClubName] = useState(''); 
  const [clubPicture, setClubPicture] = useState(''); 
  const [description, setDescription] = useState('');
  const [location, setLocation] = useState('');
  const [meetingTimes, setMeetingTimes] = useState('');
  const [facebookLink, setFacebookLink] = useState('');
  const [instagramLink, setInstagramLink] = useState('');
  const [updateClubName, setUpdateClubName] = useState(false);
  const [updateClubPicture, setUpdateClubPicture] = useState(false);
  const [updateDescription, setUpdateDescription] = useState(false);
  const [updateLocation, setUpdateLocation] = useState(false);
  const [updateMeetingTimes, setUpdateMeetingTimes] = useState(false);
  const [updateFacebookLink, setUpdateFacebookLink] = useState(false);
  const [updateInstagramLink, setUpdateInstagramLink] = useState(false);

  const [toastError, setToastError] = useState('');
  const toast = useToast();
  const navigate = useNavigate();

  const updateClubDetails = async () => {
    try {
      const clubObject = { clubID: clubDetails.clubID, clubName: null, categoryName: null, description: null, presidentEmailID: null, facebookLink: null, instagramLink: null, categoryID: null, location: null, meetingTime: null, clubImage: null, rules: null};
      if (updateClubName) {
        clubObject.clubName = clubName;
      } if (updateDescription ) {
        clubObject.description = description;
      } if (updateLocation ) {
        clubObject.location = location;
      } if (updateMeetingTimes ) {
        clubObject.meetingTime = meetingTimes;
      } if (updateFacebookLink ) {
        clubObject.facebookLink = facebookLink;
      } if (updateInstagramLink ) {
        clubObject.instagramLink = instagramLink;
      } if (updateClubPicture) {
        const base64String = await getBase64StringFromImage(clubPicture);
        clubObject.clubImage = base64String;
        console.log(base64String);
      }

      console.log(JSON.stringify(clubObject));
      const response = await axiosPrivate.post('/president/updateClubDetails',
              JSON.stringify(clubObject)
      );
      console.log(response);
	
      navigate(`/club/${clubDetails.clubName}`);

    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    const getClubDetails = async () => {
    try {

      const response = await axios.get(`/unauthenticated/getClubByName/${clubNameParam}`);

      setClubDetails(response.data[0]);
      console.log(response.data[0]);
      setClubName(clubDetails.clubName);
      setDescription(clubDetails.description);
      setLocation(clubDetails.location);
      setMeetingTimes(clubDetails.meetingTime);
      setFacebookLink(clubDetails.facebookLink);
      setInstagramLink(clubDetails.instagramLink);
      setLoading(false);
    } catch (error) {
      console.log(error);
      setLoading(false);
    }
  };

    getClubDetails();
  }, [clubNameParam, clubDetails.clubName, clubDetails.description, clubDetails.facebookLink, clubDetails.instagramLink, clubDetails.location, clubDetails.meetingTime, loading]);

  const handleUpdate = () => {
    let updated = false; 
    let isError = false; 
    let errMessage = "";
    if (updateClubName || updateClubPicture || updateDescription || updateLocation || updateMeetingTimes || updateFacebookLink || updateInstagramLink) {
      if (updateClubName && clubName === clubDetails.clubName) {
        errMessage += ('Please update Club Name field. The updated Club Name is same as existing value.<br />');
        isError=true;
      } if (updateClubPicture && clubPicture === '') {
        errMessage += ('Please add a Club Image.');
        isError=true;
      } if (updateDescription && description === clubDetails.description) {
        errMessage += ('Please update Description field. The updated Description is same as existing value.\n <br /> \n');
        isError=true;
      } if (updateLocation && location === clubDetails.location) {
        errMessage += ('Please update Location field. The updated Location is same as existing value.\n');
        isError=true;
      } if (updateMeetingTimes && meetingTimes === clubDetails.meetingTime) {
        errMessage += ('Please update Meeting Times field. The updated Meeting Times is same as existing value.\n');
        isError=true;
      } if (updateFacebookLink && facebookLink === clubDetails.facebookLink) {
        errMessage += ('Please update Facebook Link field. The updated Facebook Link is same as existing value.\n');
        isError=true;
      } if (updateInstagramLink && instagramLink === clubDetails.instagramLink) {
        errMessage += ('Please update Instagram Link field. The updated Instagram Link is same as existing value.\n');
        isError=true;
      } 
      setToastError(errMessage);
      if (!isError) {
        updated = true;
        setToastError(''); 
        updateClubDetails();
      }
    }

    if (updated) {
      toast({
        title: 'Update Successful',
        description: 'Updated club details request raised successfully! Please check back in some time to view your club updates.',
        status: 'success',
        duration: 5000,
        isClosable: true,
      });
    } else {
      toast({
        title: 'Oops!',
        description: 'Please select a field to update by checking the update box! Looks like you are trying to update a field value without updating it.',
        status: 'error',
        duration: 10000,
        isClosable: true,
      });
    }
  };

  const getBase64StringFromImage = (imageFile) => {
    return new Promise((resolve, reject) => {
      const reader = new FileReader();
      reader.onload = () => resolve(reader.result.split(',')[1]);
      reader.onerror = (error) => reject(error);
      reader.readAsDataURL(imageFile);
    });
  };

  return (
    <Box position="relative">
        <img src="/formBackground.jpg" alt="" style={{ width: '100%', height: '400px', objectFit: 'cover' }} />
        <Box position="absolute" top="50px"  left="50%" transform="translateX(-50%)" width="60%" bg="white" p="20px" rounded="md" h="100%">
    <Flex align="center" justify="center" h="75vh">

      <Box  bg="white" rounded="md" width="80%">
        <Text fontSize="4xl" fontWeight="bold" textAlign="center" mb="10px" color="yellow.500">
          UPDATE CLUB DETAILS
        </Text>
        <Text fontSize="md" textAlign="center" mb="20px">
          Please select the checkbox of the field value requiring updation.
        </Text>
  
        <Box>
          <Text fontSize="sm" mb="5px" color="yellow.500" >
            Club Name:
          </Text>
          <Flex align="center">
            <Checkbox isChecked={updateClubName} onChange={(e) => setUpdateClubName(e.target.checked)}>
              Update
            </Checkbox>
            <Input value={clubName} onChange={(e) => setClubName(e.target.value)} ml="10px" disabled={!updateClubName} />
          </Flex>
        </Box>
  
        <Box mt="10px">
          <Text fontSize="sm" mb="5px" color="yellow.500" >
            Club Picture:
          </Text>
          <Flex align="center">
            <Checkbox isChecked={updateClubPicture} onChange={(e) => setUpdateClubPicture(e.target.checked)}>
              Update
            </Checkbox>
            <Input type="file" onChange={(e) => setClubPicture(e.target.files[0])} ml="10px" disabled={!updateClubPicture} />
          </Flex>
        </Box>
  
        <Box mt="10px">
          <Text fontSize="sm" mb="5px" color="yellow.500" >
            Description:
          </Text>
          <Flex align="center">
            <Checkbox isChecked={updateDescription} onChange={(e) => setUpdateDescription(e.target.checked)}>
              Update
            </Checkbox>
            <Input value={description} onChange={(e) => setDescription(e.target.value)} ml="10px" disabled={!updateDescription} />
          </Flex>
        </Box>
  
        <Box mt="10px">
          <Text fontSize="sm" mb="5px" color="yellow.500" >
            Location:
          </Text>
          <Flex align="center">
            <Checkbox isChecked={updateLocation} onChange={(e) => setUpdateLocation(e.target.checked)}>
              Update
            </Checkbox>
            <Input value={location} onChange={(e) => setLocation(e.target.value)} ml="10px" disabled={!updateLocation} />
          </Flex>
        </Box>
  
        <Box mt="10px">
          <Text fontSize="sm" mb="5px" color="yellow.500" >
            Meeting Times:
          </Text>
          <Flex align="center">
            <Checkbox isChecked={updateMeetingTimes} onChange={(e) => setUpdateMeetingTimes(e.target.checked)}>
              Update
            </Checkbox>
            <Input value={meetingTimes} onChange={(e) => setMeetingTimes(e.target.value)} ml="10px" disabled={!updateMeetingTimes} />
          </Flex>
        </Box>
  
        <Box mt="10px">
          <Text fontSize="sm" mb="5px" color="yellow.500" >
            Facebook Link:
          </Text>
          <Flex align="center">
            <Checkbox isChecked={updateFacebookLink} onChange={(e) => setUpdateFacebookLink(e.target.checked)}>
              Update
            </Checkbox>
            <Input value={facebookLink} onChange={(e) => setFacebookLink(e.target.value)} ml="10px" disabled={!updateFacebookLink} />
          </Flex>
        </Box>
  
        <Box mt="10px">
          <Text fontSize="sm" mb="5px" color="yellow.500" >
            Instagram Link:
          </Text>
          <Flex align="center">
            <Checkbox isChecked={updateInstagramLink} onChange={(e) => setUpdateInstagramLink(e.target.checked)}>
              Update
            </Checkbox>
            <Input value={instagramLink} onChange={(e) => setInstagramLink(e.target.value)} ml="10px" disabled={!updateInstagramLink} />
          </Flex>
        </Box>
  
        {toastError && (
          <Box mt="10px" textAlign="center">
            <Text color="red.500" fontSize="sm">
              {toastError}
            </Text>
          </Box>
        )}
  
        <Box mt="20px">
          <Button colorScheme="yellow" onClick={handleUpdate} width="100%">
            UPDATE CLUB DETAILS
          </Button>
        </Box>
      </Box>
    </Flex>
  </Box>
  </Box>
  );
  
        }
export default UpdateClubDetails;
