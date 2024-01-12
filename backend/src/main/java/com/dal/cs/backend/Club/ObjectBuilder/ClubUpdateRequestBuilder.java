package com.dal.cs.backend.Club.ObjectBuilder;

import com.dal.cs.backend.Club.ClassObject.ClubUpdateRequest;
import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.Enum.RequestType;

public class ClubUpdateRequestBuilder {
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

    public ClubUpdateRequestBuilder setRequestID(String requestID) {
        this.requestID = requestID;
        return this;
    }

    public ClubUpdateRequestBuilder setClubID(String clubID) {
        this.clubID = clubID;
        return this;
    }

    public ClubUpdateRequestBuilder setRequesterEmailID(String requesterEmailID) {
        this.requesterEmailID = requesterEmailID;
        return this;
    }

    public ClubUpdateRequestBuilder setCategoryID(String categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    public ClubUpdateRequestBuilder setClubName(String clubName) {
        this.clubName = clubName;
        return this;
    }

    public ClubUpdateRequestBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ClubUpdateRequestBuilder setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
        return this;
    }

    public ClubUpdateRequestBuilder setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
        return this;
    }

    public ClubUpdateRequestBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public ClubUpdateRequestBuilder setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
        return this;
    }

    public ClubUpdateRequestBuilder setClubImage(String clubImage) {
        this.clubImage = clubImage;
        return this;
    }

    public ClubUpdateRequestBuilder setRules(String rules) {
        this.rules = rules;
        return this;
    }

    public ClubUpdateRequestBuilder setRequestType(RequestType requestType) {
        this.requestType = requestType;
        return this;
    }

    public ClubUpdateRequestBuilder setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
        return this;
    }

    public ClubUpdateRequestBuilder setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public ClubUpdateRequest createClubUpdateRequest() {
        return new ClubUpdateRequest(requestID,clubID, requesterEmailID, categoryID, categoryName, clubName, description, facebookLink, instagramLink, location, meetingTime, clubImage, rules, requestType, requestStatus);
    }
}
