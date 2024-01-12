package com.dal.cs.backend.Event.ServiceLayer;
import com.dal.cs.backend.Email.ClassObject.Email;
import com.dal.cs.backend.Email.ObjectBuilder.EmailBuilder;
import com.dal.cs.backend.Email.ServiceLayer.EmailServiceLayer;
import com.dal.cs.backend.Email.ServiceLayer.IEmailServiceLayer;
import com.dal.cs.backend.Event.DataLayer.IEventDataLayer;
import com.dal.cs.backend.Event.EventObject.Event;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class EventServiceLayer implements  IEventServiceLayer{
    private static final Logger logger= LogManager.getLogger(EventServiceLayer.class);
    IEventDataLayer iEventDataLayer;

    @Autowired
    public EventServiceLayer(IEventDataLayer iEventDataLayer) {
        this.iEventDataLayer = iEventDataLayer;
    }

    public static IEventServiceLayer getInstance(IEventDataLayer iEventDataLayer) {
        return new EventServiceLayer(iEventDataLayer);
    }

    /**
     * This method fetches all the events
     * @return list of all events
     */
    @Override
    public List<Event> getAllEvents()
    {
        logger.info("Service Layer Entered: Entered getAllEvents()- Calling Data layer getAllEvents()");
        try
        {
            return iEventDataLayer.getAllEvents();
        }
        catch(SQLException e)
        {
            logger.error("getAllEvents()- SQL exception occurred while getting response from Data Layer"+e.getMessage());
        }
        logger.info("ServiceLayer: getAllEvents() returned null to Controller");
        return null;
    }

    /**
     * This method generates a new event ID, for inserting a new event record into event table.
     * @param event is the real world event object which has all the information about an event.
     * @return boolean status result if the event was created successfully by inserting the record into the table, else false.
     */
    @Override
    public boolean createEvent(Event event) {
        logger.info("Service Layer Entered: Entered createEvent- Calling Data layer createEvent");
        String errorMessage;
        logger.info("createEvent- Calling generateEventId()");
        String EventId=generateEventId();
        event.setEventID(EventId);
        logger.info("createEvent- retrieved new latest eventID = "+EventId);
        try {
            logger.info("createEvent- Calling Data layer createEvent");
            boolean createEventStatus = iEventDataLayer.createEvent(event);
            if (createEventStatus) {
                logger.info("Exiting Service Layer: Returning boolean result status=true to Controller");
                return true;
            }
            else {
                errorMessage = "Unable to create event by inserting in database table.";
                logger.warn("Exiting Service Layer: Returning boolean result status=false to Controller, Error Message: "+errorMessage);
                return false;
            }
        } catch (SQLException e) {
            errorMessage = "Unable to create event by inserting in database table. Error message: "+e.getMessage();
            logger.error("Exception occured in 'createEvent': " + errorMessage);
            logger.info("Exiting Service Layer: Returning error message to Controller");
            return false;
        }
    }

    /**
     * This method retrieves the new incremented event id when a new event creation is to be executed.
     * @return the new latest eventID String
     */
    private String generateEventId() {
        logger.info("generateEventId- Entered generateEventId- Calling Data layer getLatestEventId");
        String newEventId = iEventDataLayer.getLatestEventId();
        if(newEventId != null)
        {
            logger.info("Exiting Service Layer: Returning latest eventID.");
            return newEventId;
        }
        String firstEventId = "EVNT_1";
        logger.info("Exiting Service Layer: Returning the first eventID, as no events present.");
        return firstEventId;
    }

    /**
     * This method returns a list of events that user has registered in
     * @param userEmailId  is the email id of the user using which they signed up to DalClubs
     * @return  list of events to the controller
     */
    @Override
    public List<Event> getEventsByUser(String userEmailId)
    {
        logger.info("Service Layer Entered: Entered getEventsByUser()- Calling Data layer getEventsByUser()");
        try
        {
            return iEventDataLayer.getEventsByUser(userEmailId);
        }
        catch(SQLException e)
        {
            logger.error("getEventsByUser()- SQL exception occurred while getting response from Data Layer"+e.getMessage());
        }
        logger.info("ServiceLayer: getEventsByUser() returned null to Controller");
        return null;
    }

    /**
     * This method register the events
     *
     * @param eventID this will be the id of event
     * @param emailID is the email id of the user using which they signed up to DalClubs
     * @return true if registered successfully
     */
    @Override
    public boolean registerEvents(String eventID, String emailID)
    {
        logger.info("Service Layer Entered: Entered registerEvents()- Calling Data layer registerEvents()");
        try
        {
            boolean resultStatus= iEventDataLayer.registerEvents(eventID,emailID);
            if(resultStatus)
            {
                logger.info("ServiceLayer: event registered. Sending Confirmation mail to user.");
                //get event details by event id
                logger.info("ServiceLayer: calling getEventByEventId() of DataLayer ");
                Event event=iEventDataLayer.getEventByEventId(eventID);
                if(event!=null)
                {
                    logger.info("ServiceLayer: event details returned.");
                    logger.info("ServiceLayer: creating to, from, subject and body for the email");
                    String emailSubject="Registration successful for the event: "+ event.getEventName();
                    String emailBody = "PFB the Event Details: " + "\n" +
                            "Event ID: " + eventID + "\n" +
                            "Event Name: " + event.getEventName() + "\n" +
                            "Event Venue: " + event.getVenue() + "\n" +
                            "Event Starts: " + event.getStartDate() + " " + event.getStartTime() + "\n" +
                            "Event Ends: " + event.getEndTime() + " " + event.getEndDate();
                    logger.info("ServiceLayer: building email");
                    Email email=new EmailBuilder().setEmailBody(emailBody)
                            .setEmailRecipient(emailID)
                            .setEmailSubject(emailSubject).buildEmail();
                    logger.info("ServiceLayer: calling email service");
                    IEmailServiceLayer iEmailServiceLayer=new EmailServiceLayer();
                    boolean emailStatus=iEmailServiceLayer.sendEmail(email);
                    if(emailStatus)
                    {
                        logger.info("ServiceLayer: email sent successfully");
                        logger.info("ServiceLayer: Retuning true to the controller");
                        return true;
                    }
                    else
                    {
                        logger.info("ServiceLayer: email not sent successfully");
                        logger.info("ServiceLayer: Retuning false to the controller");
                        return false;
                    }
                }
            }
        }
        catch (SQLException e)
        {
            logger.error("getEventsByUser()- SQL exception occurred while getting response from Data Layer"+e.getMessage());
        }
        logger.info("ServiceLayer: registerEvents() returned false to Controller");
        return false;
    }

    /**
     * This method returns the details of event
     * @param nameOfEvent it will take the name of event user searching for
     * @return details of events user searched for
     */
    @Override
    public List<Event> getEventDetails(String nameOfEvent) {
        logger.info("Service Layer Entered: Entered getEventDetails()- Calling Data layer getEventDetails()");
        try
        {
            return iEventDataLayer.getEventDetails(nameOfEvent);
        }
        catch(SQLException e)
        {
            logger.error("getEventDetails()- SQL exception occurred while getting response from Data Layer"+e.getMessage());
        }
        logger.info("ServiceLayer: getEventDetails() returned false to Controller");
        return null;
    }

    /**
     * This method is responsible for calling teh data layer function to perform the event details update operation.
     * @param event It is the Event object which holds ony that event detail which needs to be updated.
     * @return boolean status result: return true if the event details were updated successfully, else false.
     */
    @Override
    public boolean updateEventDetails(Event event) {
        logger.info("Service Layer Entered: Entered updateEventDetails- Calling Data layer updateEventDetails");
        String errorMessage;
        try {
            logger.info("updateEventDetails- Calling Data layer updateEventDetails");
            boolean eventStatus = iEventDataLayer.updateEventDetails(event);
            if (eventStatus) {
                logger.info("Exiting Service Layer: Returning boolean result status=true to Controller");
                return true;
            }
            else {
                errorMessage = "Unable to update event details in database table.";
                logger.warn("Exiting Service Layer: Returning boolean result status=false to Controller. Error Message: "+errorMessage);
                return false;
            }
        } catch (SQLException e) {
            logger.error("updateEventDetails- SQL exception occurred while getting response from Data Layer"+e.getMessage());
            return false;
        }
    }

    /**
     * Deletes event and registration
     *
     * @param eventID ID of event to be deleted
     * @return true if deleted successfully, false otherwise
     */
    @Override
    public boolean deleteEvent(String eventID) {
        logger.info("Service Layer Entered: Entered deleteEvent- Calling Data layer deleteEvent");
        boolean resultStatus;
        String errorMessage;
        try {
            resultStatus = iEventDataLayer.deleteEvent(eventID);
            logger.info("Exiting Service Layer: Returning boolean resultStatus to Controller");
            return resultStatus;
        } catch (Exception e) {
            errorMessage = e.getMessage();
            logger.error("Exception occurred in 'deleteEvent': " + errorMessage);
        }
        logger.info("Exiting Service Layer: Returning error message to Controller");
        return false;
    }

    /**
     * This method returns the details of event filtered on club id
     * @param clubID the club id condition on which the event details are to be retrieved
     * @return list of event details filtered on club ID
     */
    @Override
    public List<Event> getEventsByClub(String clubID)
    {
        logger.info("Service Layer Entered: Entered getEventsByClub()- Calling Data layer getEventsByClub()");
        try
        {
            List<Event> listOfAllEvents = iEventDataLayer.getEventsByClub(clubID);
            logger.warn("Exiting Service Layer: Returning list of event details to Controller");
            return listOfAllEvents;
        }
        catch(SQLException e)
        {
            logger.error("getEventsByClub()- SQL exception occurred while getting response from Data Layer"+e.getMessage());
        }
        logger.warn("Exiting Service Layer: Returning boolean result status=false to Controller");
        return null;
    }


}
