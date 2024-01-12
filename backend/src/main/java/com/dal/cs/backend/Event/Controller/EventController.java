package com.dal.cs.backend.Event.Controller;
import com.dal.cs.backend.Event.EventObject.Event;
import com.dal.cs.backend.Event.ServiceLayer.IEventServiceLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "*", allowedHeaders="*")
@RestController
public class EventController {
    private static final Logger logger = LogManager.getLogger(EventController.class);
    IEventServiceLayer iEventServiceLayer;

    @Autowired
    public EventController(IEventServiceLayer iEventServiceLayer) {
        this.iEventServiceLayer = iEventServiceLayer;
    }

    /**
     * This receives request to retrieve a list of all events in DalClubs
     *
     * @return list of events
     */
    @RequestMapping(method = RequestMethod.GET, value = "/unauthenticated/getAllEvents")
    public List<Event> getAllEvents() {
        logger.info("Controller Entered: Received request to get all events.");
        logger.info("getAllEvents- Calling getAllEvents() of ServiceLayer");
        List<Event> listOfAllEvents = iEventServiceLayer.getAllEvents();
        logger.info("Exiting Controller: Returning list of events to Frontend via GET /getAllEvents");
        return listOfAllEvents;
    }

    /**
     * This method accepts the event details for creating a new event.
     * @param event It is the entity which has all the event information entered by the user.
     * @return result response about the success of creating event. true if event created successfully, else false.
     */
    @RequestMapping(method = RequestMethod.POST, value = "/president/createEvent")
    public Map<String, Boolean> createEvent(@RequestBody Event event) {
        logger.info("Controller Entered: Received request for creating a new event");
        logger.info("createEvent- Calling Service layer createEvent");
        Map<String, Boolean> resultResponse = new HashMap<>();
        boolean result = iEventServiceLayer.createEvent(event);
        resultResponse.put("status", result);
        logger.info("Exiting Controller: Returning response status to Frontend via POST /createEvent");
        return resultResponse;
    }

    /**
     * This method returns a list of events that user has registered in
     * @param userEmailId is the email id of the user using which they signed up to DalClubs
     * @return list of events to the frontend
     */
    @RequestMapping(method = RequestMethod.GET, value="/unauthenticated/getAllEvents/{userEmailId}")
    public List<Event> getEventsByUser(@PathVariable("userEmailId") String userEmailId)
    {
        logger.info("Controller Entered: Received request to get all events by userEmailID");
        logger.info("getAllEvents- Calling getEventsByUser() of ServiceLayer");
        List<Event> listOfAllEvents=iEventServiceLayer.getEventsByUser(userEmailId);
        logger.info("Exiting Controller: Returning list of events to Frontend via GET /getEventsByUser");
        return listOfAllEvents;
    }

    /**
     * This method register the events
     *
     * @param eventID this will be the id of event
     * @param emailID is the email id of the user using which they signed up to DalClubs
     * @return true if registered successfully
     */
    @RequestMapping(method = RequestMethod.POST, value = "/unauthenticated/registerEvents/{eventID}/{emailID}")
    public boolean registerEvents(@PathVariable String eventID, @PathVariable String emailID)
    {
        logger.info("Controller Entered: Received request for register events");
        logger.info("registerEvents- Calling registerEvents() of ServiceLayer");
        boolean resultStatus= iEventServiceLayer.registerEvents(eventID, emailID);
        logger.info("Exiting Controller: Returning status= "+resultStatus+" for successful event registration via POST /registerEvents");
        return resultStatus;
    }

    /**
     * This method returns the details of event
     * @param nameOfEvent is the name of event user is searching detail for
     * @return list of event details user searched for
     */
    @RequestMapping(method = RequestMethod.GET, value = "/unauthenticated/getEventDetails/{nameOfEvent}")
    public List<Event> getEventDetails(@PathVariable("nameOfEvent") String nameOfEvent)
    {
        logger.info("Controller Entered: Received request for get event details");
        logger.info("registerEvents- Calling getEventDetails() of ServiceLayer");
        List<Event> eventDetails=iEventServiceLayer.getEventDetails(nameOfEvent);
        logger.info("Exiting Controller: Returning status for get data via GET /getEventDetails");
        return eventDetails;
    }

    /**
     * This method accepts the Event details that are to be updated. It calls the service layer to perform the update operation.
     * @param event It is the entity which only has all details which are updated in an event by the user.
     * @return result response about the success of creating event. true if event details updated successfully, else false.
     */
    @RequestMapping(method = RequestMethod.POST, value="/president/updateEventDetails")
    public Map<String, Boolean> updateEventDetails(@RequestBody Event event) {
        logger.info("Controller Entered: Received request for updating a existing event");
        logger.info("createEvent- Calling Service layer updateEventDetails");
        Map<String, Boolean> resultResponse = new HashMap<>();
        boolean result = iEventServiceLayer.updateEventDetails(event);
        resultResponse.put("status", result);
        logger.info("Exiting Controller: Returning response status to Frontend via POST /updateEventDetails");
        return resultResponse;
    }

    /**
     * Deletes event and clears all registrations for event
     *
     * @param eventID ID of event to be deleted
     * @return boolean response result, which returns true if record deleted successfully, else returns false
     */
    @RequestMapping(method = RequestMethod.POST, value = "/president/deleteEvent")
    public Map<String, Boolean> deleteEvent(String eventID) {
        logger.info("Controller Entered: Received request for deleting event");
        Map<String, Boolean> resultResponse = new HashMap<>();
        boolean result = iEventServiceLayer.deleteEvent(eventID);
        resultResponse.put("status", result);
        logger.info("Exiting Controller: Returning response status to Frontend via POST /deleteEvent");
        return resultResponse;
    }

    /**
     * This method returns the event details filtered by club id
     * @param clubID is the club id on which the event details are to be filtered
     * @return list of event details filtered based on club ID
     */
    @RequestMapping(method = RequestMethod.GET, value="/unauthenticated/getEventByClub/{clubID}")
    public List<Event> getEventsByClub(@PathVariable("clubID") String clubID)
    {
        logger.info("Controller Entered: Received request to get events by clubID");
        logger.info("getEventsByClub- Calling getEventsByClub() of ServiceLayer");
        List<Event> listOfAllEvents=iEventServiceLayer.getEventsByClub(clubID);
        logger.info("Exiting Controller: Returning list of events to Frontend via GET /getEventsByClub/{clubID}");
        return listOfAllEvents;
    }
}
