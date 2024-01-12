import React, { useState, useEffect} from 'react';
import { Box, Flex, Text, Input, Button, Checkbox, useToast } from '@chakra-ui/react';
import { useParams } from 'react-router-dom';
import { useNavigate } from 'react-router';
import axios, { axiosPrivate } from '../axiosConfiguration';

function UpdateEventDetails() {
  const { eventNameParam } = useParams(); 

  const [eventDetails, setEventDetails] = useState([]); 
  const [loading, setLoading] = useState(true);

  const [organizerEmailID, setOrganizerEmailID] = useState('');
  const [eventName, setEventName] = useState('');
  const [description, setDescription] = useState('');
  const [venue, setVenue] = useState('');
  const [image, setImage] = useState(''); 
  const [updateOrganizerEmailID, setUpdateOrganizerEmailID] = useState(false);
  const [updateEventName, setUpdateEventName] = useState(false);
  const [updateDescription, setUpdateDescription] = useState(false);
  const [updateVenue, setUpdateVenue] = useState(false);
  const [updateImage, setUpdateImage] = useState(false);

  const [toastError, setToastError] = useState('');
  const toast = useToast();
  const navigate = useNavigate();

  const updateEventDetails = async () => {
     try {
       const eventObject = {
      eventID: eventDetails.eventID,
      clubID: eventDetails.clubID,
      organizerEmailID: null,
      eventName: null,
      description: null,
      venue: null,
      image: null,
      startDate: null,
      endDate: null,
      startTime: null,
      endTime: null,
      eventTopic: null,
    };

  if (updateOrganizerEmailID) {
      eventObject.organizerEmailID = organizerEmailID;
    }
    if (updateEventName) {
      eventObject.eventName = eventName;
    }
    if (updateDescription) {
      eventObject.description = description;
    }
    if (updateVenue) {
      eventObject.venue = venue;
    }
    if (updateImage) {
      const base64String = await getBase64StringFromImage(image);
      eventObject.image = base64String;
      console.log(base64String);
    }

       console.log(JSON.stringify(eventObject));
       const response = await axiosPrivate.post('/president/updateEventDetails',
               JSON.stringify(eventObject)
       );
       console.log(response);

       navigate(`/event/${eventDetails.eventName}`);

     } catch (error) {
       console.log(error);
     }
  };

  const handleUpdate = () => {
    let updated = false; 
    if (updateOrganizerEmailID || updateEventName || updateDescription || updateVenue || updateImage ) {

      if (updateOrganizerEmailID && organizerEmailID === eventDetails.organizerEmailID) {
        setToastError('Please update Organizer Email ID');
      } else if (updateEventName && eventName === eventDetails.eventName) {
        setToastError('Please update Event Name');
      } else if (updateDescription && description === eventDetails.description) {
        setToastError('Please update Description');
      } else if (updateVenue && venue === eventDetails.venue) {
        setToastError('Please update Venue');
      } else if (updateImage && image === '') {
        setToastError('Please update Image');
      } else {
        updated = true;
        setToastError(''); 
        updateEventDetails();
      }
    }

    if (updated) {
      toast({
        title: 'Update Successful',
        description: 'Event details updated successfully!',
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

  useEffect(() => {
    const getEventDetails = async () => {
      try {
  
        const response = await axios.get(`/unauthenticated/getEventDetails/${eventNameParam}`);
  
        setEventDetails(response.data[0]);
        console.log(response.data[0]);
        setOrganizerEmailID(response.data[0].organizerEmailID);
        setEventName(response.data[0].eventName);
        setDescription(response.data[0].description);
        setVenue(response.data[0].venue);
        setLoading(false);
      } catch (error) {
        console.log(error);
        setLoading(false);
      }
    };
  
    getEventDetails();
  }, [eventNameParam,loading]);

  return (
    <Box position="relative">
      <img src="/formBackground.jpg" alt="" style={{ width: '100%', height: '400px', objectFit: 'cover' }} />
      <Box position="absolute" top="50px" left="50%" transform="translateX(-50%)" width="60%" bg="white"  rounded="md" h="100%">
      <Flex align="center" justify="center" h="65vh">
          <Box bg="white" rounded="md" width="80%">
            <Text fontSize="4xl" fontWeight="bold" textAlign="center" mb="10px" color="yellow.500">
              UPDATE EVENT DETAILS
            </Text>
            <Text fontSize="md" textAlign="center" mb="20px">
              Please select the checkbox of the field value requiring updation.
            </Text>

            
            <Box mt="10px">
              <Text fontSize="sm" mb="5px" color="yellow.500">
                Organizer Email ID:
              </Text>
              <Flex align="center">
                <Checkbox isChecked={updateOrganizerEmailID} onChange={(e) => setUpdateOrganizerEmailID(e.target.checked)}>
                  Update
                </Checkbox>
                <Input value={organizerEmailID} onChange={(e) => setOrganizerEmailID(e.target.value)} ml="10px" disabled={!updateOrganizerEmailID} />
              </Flex>
            </Box>

            <Box mt="10px">
              <Text fontSize="sm" mb="5px" color="yellow.500">
                Event Name:
              </Text>
              <Flex align="center">
                <Checkbox isChecked={updateEventName} onChange={(e) => setUpdateEventName(e.target.checked)}>
                  Update
                </Checkbox>
                <Input value={eventName} onChange={(e) => setEventName(e.target.value)} ml="10px" disabled={!updateEventName} />
              </Flex>
            </Box>

            <Box mt="10px">
              <Text fontSize="sm" mb="5px" color="yellow.500">
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
              <Text fontSize="sm" mb="5px" color="yellow.500">
                Venue:
              </Text>
              <Flex align="center">
                <Checkbox isChecked={updateVenue} onChange={(e) => setUpdateVenue(e.target.checked)}>
                  Update
                </Checkbox>
                <Input value={venue} onChange={(e) => setVenue(e.target.value)} ml="10px" disabled={!updateVenue} />
              </Flex>
            </Box>

            <Box mt="10px">
              <Text fontSize="sm" mb="5px" color="yellow.500">
                Image:
              </Text>
              <Flex align="center">
                <Checkbox isChecked={updateImage} onChange={(e) => setUpdateImage(e.target.checked)}>
                  Update
                </Checkbox>
                {/* Assuming it's a file input */}
                <Input type="file" onChange={(e) => setImage(e.target.files[0])} ml="10px" disabled={!updateImage} />
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
                UPDATE EVENT DETAILS
              </Button>
            </Box>
          </Box>
        </Flex>
      </Box>
    </Box>
  );
}

export default UpdateEventDetails;
