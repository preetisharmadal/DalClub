package com.dal.cs.backend.Club.ClassObject;

import com.dal.cs.backend.Club.Enum.RequestStatus;

public class JoinClubRequest {
    private String requestID;
    private String requesterEmailID;
    private String clubID;
    private String joiningReason;
    private RequestStatus requestStatus;

    public JoinClubRequest(String requestID, String requesterEmailID, String clubID, String joiningReason, RequestStatus requestStatus) {
        this.requestID = requestID;
        this.requesterEmailID = requesterEmailID;
        this.clubID = clubID;
        this.joiningReason = joiningReason;
        this.requestStatus = requestStatus;
    }

    public String getRequestID() {
        return requestID;
    }

    public void setRequestID(String requestID) {
        this.requestID = requestID;
    }

    public String getRequesterEmailID() {
        return requesterEmailID;
    }

    public void setRequesterEmailID(String requesterEmailID) {
        this.requesterEmailID = requesterEmailID;
    }

    public String getClubID() {
        return clubID;
    }

    public void setClubID(String clubID) {
        this.clubID = clubID;
    }

    public String getJoiningReason() {
        return joiningReason;
    }

    public void setJoiningReason(String joiningReason) {
        this.joiningReason = joiningReason;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
