package com.dal.cs.backend.Event.EventObject;

public class Event {
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

    public Event(String eventID, String clubID, String organizerEmailID, String eventName, String description, String venue, String image, String startDate, String endDate, String startTime, String endTime, String eventTopic)
    {
        this.eventID = eventID;
        this.clubID = clubID;
        this.organizerEmailID = organizerEmailID;
        this.eventName = eventName;
        this.description = description;
        this.venue = venue;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.eventTopic = eventTopic;
    }

    public String getEventID() {
        return eventID;
    }

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getOrganizerEmailID() {
        return organizerEmailID;
    }

    public void setOrganizerEmailID(String organizerEmailID) {
        this.organizerEmailID = organizerEmailID;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEventTopic() {
        return eventTopic;
    }

    public void setEventTopic(String eventTopic) {
        this.eventTopic = eventTopic;
    }


}
