package com.dal.cs.backend.Club.ObjectBuilder;


import com.dal.cs.backend.Club.ClassObject.Club;

/**
 * This class represents the implementation of the builder pattern for the Club object.
 */
public class ClubBuilder
{
    private String clubID;
    private String clubName;
    private String description;
    private String presidentEmailID;
    private String facebookLink;
    private String instagramLink;
    private String categoryID;
    private String location;
    private String meetingTime;
    private String clubImage;
    private String rules;
    private  String categoryName;

    public ClubBuilder setClubID(String clubID) {
        this.clubID = clubID;
        return this;
    }

    public ClubBuilder setClubName(String clubName) {
        this.clubName = clubName;
        return this;
    }

    public ClubBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ClubBuilder setPresidentEmailID(String presidentEmailID) {
        this.presidentEmailID = presidentEmailID;
        return this;
    }

    public ClubBuilder setFacebookLink(String facebookLink) {
        this.facebookLink = facebookLink;
        return this;
    }

    public ClubBuilder setInstagramLink(String instagramLink) {
        this.instagramLink = instagramLink;
        return this;
    }

    public ClubBuilder setCategoryID(String categoryID) {
        this.categoryID = categoryID;
        return this;
    }

    public ClubBuilder setLocation(String location) {
        this.location = location;
        return this;
    }

    public ClubBuilder setMeetingTime(String meetingTime) {
        this.meetingTime = meetingTime;
        return this;
    }

    public ClubBuilder setClubImage(String clubImage) {
        this.clubImage = clubImage;
        return this;
    }

    public ClubBuilder setRules(String rules) {
        this.rules = rules;
        return this;
    }

    public ClubBuilder setCategoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public Club createClub()
    {
        return new Club(clubID,clubName,description,presidentEmailID,facebookLink,instagramLink,categoryID,location,meetingTime,clubImage,rules, categoryName);
    }
}
