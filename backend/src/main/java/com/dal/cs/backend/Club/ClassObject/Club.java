package com.dal.cs.backend.Club.ClassObject;

public class Club
{
    private String clubID;
    private String clubName;
    private String categoryName;
    private String description;
    private String presidentEmailID;
    private String facebookLink;
    private String instagramLink;
    private String categoryID;
    private String location;
    private String meetingTime;
    private String clubImage;
    private String rules;

    public Club(String clubID, String clubName, String description, String presidentEmailID, String facebookLink, String instagramLink, String categoryID, String location, String meetingTime, String clubImage, String rules, String categoryName)
    {
        this.clubID = clubID;
        this.clubName = clubName;
        this.description = description;
        this.presidentEmailID = presidentEmailID;
        this.facebookLink = facebookLink;
        this.instagramLink = instagramLink;
        this.categoryID = categoryID;
        this.location = location;
        this.meetingTime = meetingTime;
        this.clubImage = clubImage;
        this.rules = rules;
        this.categoryName = categoryName;
    }
    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getClubName() {
        return clubName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPresidentEmailID() {
        return presidentEmailID;
    }

    public void setPresidentEmailID(String presidentEmailID) {
        this.presidentEmailID = presidentEmailID;
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

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
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
}
