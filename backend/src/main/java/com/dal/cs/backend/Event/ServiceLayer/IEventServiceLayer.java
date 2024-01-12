package com.dal.cs.backend.Event.ServiceLayer;

import com.dal.cs.backend.Event.EventObject.Event;

import java.util.List;

public interface IEventServiceLayer {
    List<Event> getAllEvents();

    List<Event> getEventsByUser(String userEmailId);

    boolean createEvent(Event event);

    boolean registerEvents(String eventID, String emailID);

    List<Event> getEventDetails(String nameOfEvent);

    boolean updateEventDetails(Event event);

    boolean deleteEvent(String eventID);

    List<Event> getEventsByClub(String clubId);
}
