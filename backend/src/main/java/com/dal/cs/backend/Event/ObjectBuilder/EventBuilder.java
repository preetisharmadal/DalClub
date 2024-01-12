package com.dal.cs.backend.Event.ObjectBuilder;

import com.dal.cs.backend.Event.EventObject.Event;

/**
 * This class represents the implementation of the builder pattern for the Event object.
 */
public class EventBuilder
{
    private String eventID;
    private String clubID;
    private String organizerEmailID;
    private String eventName;
    private String description;
    private String venue;
    private String image;
    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;
    private String eventTopic;

    public EventBuilder setEndTime(String endTime) {
        this.endTime = endTime;
        return  this;
    }

    public EventBuilder setEventTopic(String eventTopic) {
        this.eventTopic = eventTopic;
        return this;
    }

    public EventBuilder setEventID(String eventID) {
        this.eventID = eventID;
        return  this;
    }

    public EventBuilder setClubID(String clubID) {
        this.clubID = clubID;
        return this;
    }

    public EventBuilder setOrganizerEmailID(String organizerEmailID) {
        this.organizerEmailID = organizerEmailID;
        return this;
    }

    public EventBuilder setEventName(String eventName) {
        this.eventName = eventName;
        return this;
    }

    public EventBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public EventBuilder setVenue(String venue) {
        this.venue = venue;
        return this;
    }

    public EventBuilder setImage(String image) {
        this.image = image;
        return  this;
    }

    public EventBuilder setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public EventBuilder setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public EventBuilder setStartTime(String startTime) {
        this.startTime = startTime;
        return  this;
    }
    public Event createEvent()
    {
        return new Event(eventID,clubID,organizerEmailID,eventName,description,venue,image,startDate,endDate,startTime,endTime,eventTopic);
    }
}
