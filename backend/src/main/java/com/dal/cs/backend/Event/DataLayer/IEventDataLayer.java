package com.dal.cs.backend.Event.DataLayer;

import com.dal.cs.backend.Event.EventObject.Event;

import java.sql.SQLException;
import java.util.List;

public interface IEventDataLayer
{

    List<Event> getAllEvents() throws SQLException;

    boolean createEvent(Event event) throws SQLException;

    String getLatestEventId();

    List<Event> getEventsByUser(String userEmailId) throws SQLException;

    boolean registerEvents(String eventID, String emailID) throws SQLException;
    List<Event> getEventDetails(String nameOfEvent) throws  SQLException;

    boolean updateEventDetails(Event event) throws SQLException;

    boolean deleteEvent(String eventID) throws SQLException;

    List<Event> getEventsByClub(String clubID) throws SQLException;
    Event getEventByEventId(String eventID) throws  SQLException;
}
