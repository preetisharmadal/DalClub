package com.dal.cs.backend.Event.DataLayer;

import com.dal.cs.backend.Event.EventObject.Event;
import com.dal.cs.backend.Event.ObjectBuilder.EventBuilder;
import com.dal.cs.backend.baseUtils.dataLayer.BaseDataLayer;
import com.dal.cs.backend.database.IDatabaseConnection;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EventDataLayer extends BaseDataLayer implements IEventDataLayer {
    private static final Logger logger = LogManager.getLogger(EventDataLayer.class);
    private String callProcedure;
    private CallableStatement callableStatement;

    @Autowired
    public EventDataLayer(IDatabaseConnection iDatabaseConnection) {
        super(iDatabaseConnection);
    }

    public static EventDataLayer getInstance(IDatabaseConnection iDatabaseConnection) {
        return new EventDataLayer(iDatabaseConnection);
    }

    /**
     * This method fetches the records of all the events from the database table
     *
     * @return list of events fetched
     * @throws SQLException
     */
    @Override
    public List<Event> getAllEvents() throws SQLException {
        logger.info("Entered DataLayer: Entered getAllEvents()");
        callProcedure="{CALL getAllEvents()}";
        callableStatement=connection.prepareCall(callProcedure);
        boolean procedureCallStatus=callableStatement.execute();
        logger.info("Stored procedure for getAllEvents() executed with status "+procedureCallStatus);
        ResultSet resultSet=callableStatement.getResultSet();
        List<Event> listOfAllEvents=new ArrayList<>();
        if(procedureCallStatus)
        {
            setEventFromResultSet(resultSet, listOfAllEvents);
            logger.info("getAllEvents(): list of all events created successfully");
            logger.info("Exiting DataLayer: returning list of all events to Service Layer");
            return listOfAllEvents;
        } else {
            logger.error("Problem with procedure call or database connection");
            return null;
        }
    }

    /**
     * This method calls a stored procedure that inserts a record for a new event into the events table
     *
     * @param event This is event object that has all the event details
     * @return true if the event record is insert successfully, else return false
     * @throws SQLException
     */
    @Override
    public boolean createEvent(Event event) throws SQLException {
        if (connection != null) {
            logger.info("Entered DataLayer: Entered createEvent()");
            callProcedure = getProcedureCallString("createEvent", 12);
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, event.getEventID());
            callableStatement.setString(2, event.getClubID());
            callableStatement.setString(3, event.getOrganizerEmailID());
            callableStatement.setString(4, event.getEventName());
            callableStatement.setString(5, event.getDescription());
            callableStatement.setString(6, event.getVenue());
            callableStatement.setString(7, event.getImage());
            callableStatement.setString(8, event.getStartDate());
            callableStatement.setString(9, event.getEndDate());
            callableStatement.setString(10, event.getStartTime());
            callableStatement.setString(11, event.getEndTime());
            callableStatement.setString(12, event.getEventTopic());
            int result = callableStatement.executeUpdate();
            boolean resultStatus = (result == 1);
            logger.info("createEvent- Procedure execution call successful, resultStatus = " + resultStatus);
            logger.info("Exiting Data Layer: Returning boolean result status to Service Layer");
            return resultStatus;
        } else {
            logger.error("Exception: Database Connection not established.");
            return false;
        }
    }

    /**
     * retrieve the latest event id by calling a stored procedure
     *
     * @return event id of the latest event to add into table
     */
    @Override
    public String getLatestEventId() {
        try {
            logger.info("Entered DataLayer: Entered getLatestEventId)");
            callProcedure = getProcedureCallString("getLatestEventId", 0);
            callableStatement = connection.prepareCall(callProcedure);
            boolean procedureCallStatus = callableStatement.execute();
            logger.info("getLatestEventId- Procedure call to get latest event id");
            if (procedureCallStatus) {
                ResultSet resultSet = callableStatement.getResultSet();
                boolean resultStatus = resultSet.next();
                if (resultStatus) {
                    String latestEventId = resultSet.getString("eventID");
                    logger.info("Latest event id fetched is: " + latestEventId);
                    logger.info("Exiting Datalayer: returning latest event id to Service Layer");
                    return latestEventId;
                }
            }
        } catch (SQLException e) {
            logger.info("Exiting DataLayer: returning event id as null to Service Layer");
            System.out.println(e.getMessage());
            return null;
        }
        logger.info("Exiting DataLayer: returning event id as null to Service Layer");
        return null;
    }

    /**
     * This method fetches list of events that user has registered in from the database
     *
     * @param userEmailId is the email id of the user using which they signed up to DalClubs
     * @return list of events to the service layer
     * @throws SQLException
     */
    @Override
    public List<Event> getEventsByUser(String userEmailId) throws SQLException {
        logger.info("Entered DataLayer: Entered getEventsByUser()");
        callProcedure = getProcedureCallString("getEventsByUserEmailID", 1);
        callableStatement = connection.prepareCall(callProcedure);
        callableStatement.setString(1, userEmailId);
        boolean procedureCallStatus = callableStatement.execute();
        logger.info("Stored procedure for getEventsByUser() executed with status " + procedureCallStatus);
        ResultSet resultSet = callableStatement.getResultSet();
        List<Event> listOfAllEvents = new ArrayList<>();
        if (procedureCallStatus) {
            while (resultSet.next()) {
                Event event = new EventBuilder()
                        .setOrganizerEmailID(resultSet.getString(1))
                        .setEventName(resultSet.getString(2))
                        .setDescription(resultSet.getString(3))
                        .setVenue(resultSet.getString(4))
                        .setImage(resultSet.getString(5))
                        .setStartDate(resultSet.getString(6))
                        .setEndDate(resultSet.getString(7))
                        .setStartTime(resultSet.getString(8))
                        .setEndTime(resultSet.getString(9))
                        .setEventTopic(resultSet.getString(10))
                        .createEvent();
                listOfAllEvents.add(event);
            }
            logger.info("getEventsByUser(): list of all events created successfully");
            logger.info("Exiting DataLayer: returning list of all events to Service Layer");
            return listOfAllEvents;
        } else {
            logger.error("Problem with procedure call or database connection");
            return null;
        }
    }

    /**
     * This method register the events
     *
     * @param eventID is the event id of  any event host by the club
     * @param emailID is the email id of the user using which they signed up to DalClubs
     * @return true if events successfully register
     * @throws SQLException
     */

    @Override
    public boolean registerEvents(String eventID, String emailID) throws SQLException {
        logger.info("Entered DataLayer: Entered registerEvents()");
        callProcedure = getProcedureCallString("registerEvents", 2);
        callableStatement = connection.prepareCall(callProcedure);
        callableStatement.setString(1, eventID);
        callableStatement.setString(2, emailID);
        int procedureCallStatus = callableStatement.executeUpdate();
        logger.info("Stored procedure for registerEvents() executed with status " + procedureCallStatus);
        if (procedureCallStatus > 0) {
            logger.info("registerEvents(): event register successfully");
            logger.info("Exiting DataLayer: returning boolean status Service Layer");
            return true;
        } else {
            logger.error("Problem with procedure call or database connection");
            return false;
        }

    }

    /**
     * This method returns the details of event
     *
     * @param nameOfEvent it will take the name of event user searching for
     * @return all the details of the event user search for
     * @throws SQLException
     */
    @Override
    public List<Event> getEventDetails(String nameOfEvent) throws SQLException {
        logger.info("Entered DataLayer: Entered getEventDetails()");
        callProcedure = getProcedureCallString("getEventDetails", 1);
        callableStatement = connection.prepareCall(callProcedure);
        callableStatement.setString(1, nameOfEvent);
        boolean procedureCallStatus = callableStatement.execute();
        logger.info("Stored procedure for getEventDetails() executed with status " + procedureCallStatus);
        ResultSet resultSet = callableStatement.getResultSet();
        List<Event> eventDetails = new ArrayList<>();
        if (procedureCallStatus) {
            setEventFromResultSet(resultSet, eventDetails);
            logger.info("getEventDetails(): get the list of all events details successfully");
            logger.info("Exiting DataLayer: returning list of all events details to Service Layer");

            return eventDetails;
        } else {
            return null;
        }
    }

    /**
     * This function populates the Event List by reading the result set obtained from procedure call
     * @param resultSet the Result set received after procedure call
     * @param eventDetails list in which Event objects are to be added
     * @throws SQLException
     */
    private void setEventFromResultSet(ResultSet resultSet, List<Event> eventDetails) throws SQLException {
        while (resultSet.next()) {
            Event event = new EventBuilder()
                    .setEventID(resultSet.getString(1))
                    .setClubID(resultSet.getString(2))
                    .setOrganizerEmailID(resultSet.getString(3))
                    .setEventName(resultSet.getString(4))
                    .setDescription(resultSet.getString(5))
                    .setVenue(resultSet.getString(6))
                    .setImage(resultSet.getString(7))
                    .setStartDate(resultSet.getString(8))
                    .setEndDate(resultSet.getString(9))
                    .setStartTime(resultSet.getString(10))
                    .setEndTime(resultSet.getString(11))
                    .setEventTopic(resultSet.getString(12)).createEvent();
            eventDetails.add(event);
        }
    }

    /**
     * This method calls the stored procedure which updates the event details from the database table.
     * @param event This is event object having the event details that needs to be updated.
     * @return true if the event details are updated successfully, else return false
     * @throws SQLException
     */
    @Override
    public boolean updateEventDetails(Event event) throws SQLException {
        logger.info("Entered DataLayer: Entered updateEventDetails)");

        if (connection != null) {
            String callProcedure = "{CALL updateEvent(?,?,?,?,?,?,?,?,?,?,?,?)}";
            CallableStatement callableStatement = connection.prepareCall(callProcedure);

            callableStatement.setString(1, event.getEventID());
            callableStatement.setString(2, event.getClubID());
            callableStatement.setString(3, event.getOrganizerEmailID());
            callableStatement.setString(4, event.getEventName());
            callableStatement.setString(5, event.getDescription());
            callableStatement.setString(6, event.getVenue());
            callableStatement.setString(7, event.getImage());

            if (event.getStartDate() != null) {
                callableStatement.setDate(8, java.sql.Date.valueOf(event.getStartDate()));
            } else {
                callableStatement.setNull(8, java.sql.Types.DATE);
            }

            if (event.getEndDate() != null) {
                callableStatement.setDate(9, java.sql.Date.valueOf(event.getEndDate()));
            } else {
                callableStatement.setNull(9, java.sql.Types.DATE);
            }

            if (event.getStartTime() != null) {
                callableStatement.setTime(10, java.sql.Time.valueOf(event.getStartTime()));
            } else {
                callableStatement.setNull(10, java.sql.Types.TIME);
            }

            if (event.getEndTime() != null) {
                callableStatement.setTime(11, java.sql.Time.valueOf(event.getEndTime()));
            } else {
                callableStatement.setNull(11, java.sql.Types.TIME);
            }

            callableStatement.setString(12, event.getEventTopic());

            int result = callableStatement.executeUpdate();
            boolean resultStatus = (result == 1);
            logger.info("updateEventDetails- Procedure execution call successful, resultStatus = " + resultStatus);
            logger.info("Exiting Data Layer: Returning boolean result status to Service Layer");
            return resultStatus;
        }
        else {
            logger.error("Exception: Database Connection not established.");
            return false;
        }
    }

    /**
     * Deletes event and registrations table using event ID through stored SQL procedures
     *
     * @param eventID ID of event to be deleted
     * @return true if deleted successfully, false otherwise
     * @throws SQLException if event not found
     */
    @Override
    public boolean deleteEvent(String eventID) throws SQLException {
        logger.info("Entered DataLayer: Entered deleteEvent()");
        callProcedure = getProcedureCallString("deleteEvent", 1);
        callableStatement = connection.prepareCall(callProcedure);
        callableStatement.setString(1, eventID);
        int procedureCallStatus = callableStatement.executeUpdate();
        logger.info("Stored procedure for deleteEvent() executed with status " + procedureCallStatus);
        if (procedureCallStatus > 0) {
            logger.info("deleteEvent(): event deleted successfully");
            logger.info("Exiting DataLayer: returning boolean status Service Layer");
            return true;
        } else {
            logger.error("Problem with procedure call or database connection");
            return false;
        }
    }

    /**
     * This method returns the event details filtered by club id
     * @param clubID is the club id on which the event details are to be filtered
     * @return list of all event details filtered based on club ID
     * @throws SQLException
     */
    @Override
    public List<Event> getEventsByClub(String clubID) throws SQLException {
        logger.info("Entered DataLayer: Entered getEventsByClub()");
        callProcedure = getProcedureCallString("getEventsByClubID", 1);
        callableStatement = connection.prepareCall(callProcedure);
        callableStatement.setString(1, clubID);
        boolean resultStatus = callableStatement.execute();
        logger.info("getEventsByClub- Procedure execution call successful, resultStatus = " + resultStatus);
        ResultSet resultSet = callableStatement.getResultSet();
        List<Event> listOfAllEvents = new ArrayList<>();
        if (resultStatus) {
            setEventFromResultSet(resultSet, listOfAllEvents);
            logger.info("Exiting DataLayer: returning list of all events details to Service Layer");
            return listOfAllEvents;
        } else {
            return null;
        }
    }

    /**
     * This method gets the event details by its event id from the database table
     * @param eventID is the primary key for the event
     * @return the event object
     * @throws SQLException
     */
    @Override
    public Event getEventByEventId(String eventID) throws SQLException
    {
        logger.info("Entered Datalayer: inside  getEventByEventId() ");
        logger.info("Datalayer: calling stored procedure getEventByEventId() ");
        callProcedure="{CALL getEventByEventId(?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,eventID);
        boolean getEventStatus=callableStatement.execute();
        if(getEventStatus)
        {
            logger.info("Datalayer: stored procedure getEventByEventId() called successfully");
            ResultSet resultSet=callableStatement.getResultSet();
            resultSet.next();
            Event event=new EventBuilder().setOrganizerEmailID(resultSet.getString(3))
                    .setEventName(resultSet.getString(4)).setVenue(resultSet.getString(6))
                    .setStartTime(resultSet.getString(10)).setEndTime((resultSet.getString(11)))
                    .setStartDate(resultSet.getString(8)).setEndDate(resultSet.getString(9))
                    .createEvent();
            logger.info("Datalayer: returning the event object to the service layer");
            return event;
        }
        else
        {
            logger.info("Datalayer: stored procedure getEventByEventId() not called successfully");
            logger.info("Datalayer: returning the null object to the service layer");
            return null;
        }
    }
}