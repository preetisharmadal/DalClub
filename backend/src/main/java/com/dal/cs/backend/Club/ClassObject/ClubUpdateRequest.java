package com.dal.cs.backend.Club.ClassObject;

import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.Enum.RequestType;

public class ClubUpdateRequest {
    String requestID;
    String clubID;
    String requesterEmailID;
    String categoryID;
    String categoryName;
    String clubName;
    String description;
    String facebookLink;
    String instagramLink;
    String location;
    String meetingTime;
    String clubImage;
    String rules;
    RequestType requestType;
    RequestStatus requestStatus;

    public ClubUpdateRequest(String requestID, String clubID, String requesterEmailID, String categoryID, String categoryName, String clubName, String description, String facebookLink, String instagramLink, String location, String meetingTime, String clubImage, String rules, RequestType requestType, RequestStatus requestStatus) {
        this.requestID = requestID;
        this.clubID = clubID;
        this.requesterEmailID = requesterEmailID;
        this.categoryID = categoryID;
        this.clubName = clubName;
        this.description = description;
        this.facebookLink = facebookLink;
        this.instagramLink = instagramLink;
        this.location = location;
        this.meetingTime = meetingTime;
        this.clubImage = clubImage;
        this.rules = rules;
        this.requestType = requestType;
        this.requestStatus = requestStatus;
        this.categoryName = categoryName;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getRequesterEmailID() {
        return requesterEmailID;
    }

    public void setRequesterEmailID(String requesterEmailID) {
        this.requesterEmailID = requesterEmailID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFacebookLink() {
        return facebookLink;
    }

    public void setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
    }

    public String getInstagramLink() {
        return instagramLink;
    }

    public void setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getMeetingTime() {
        return meetingTime;
    }

    public void setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
    }

    public String getClubImage() {
        return clubImage;
    }

    public void setClubImage(String clubImage) {
        this.clubImage = clubImage;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public RequestType getRequestType() {
        return requestType;
    }

    public void setRequestType(RequestType requestType) {
        this.requestType = requestType;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
