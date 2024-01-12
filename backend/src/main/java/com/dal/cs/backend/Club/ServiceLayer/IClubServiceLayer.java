package com.dal.cs.backend.Club.ServiceLayer;

import com.dal.cs.backend.Club.ClassObject.Club;
import com.dal.cs.backend.Club.ClassObject.ClubUpdateRequest;
import com.dal.cs.backend.Club.ClassObject.JoinClubRequest;
import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.Enum.RequestType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface IClubServiceLayer
{
  public String createNewClubRequest(Club club);

  public ArrayList<HashMap<String, String>> getAllClubCategories();
  public List<Club> getAllClubs();
  public List<Club> getClubsByName(String name);
  public List<Club> getClubsByCategory(String category);
  public String updateClubDetails(Club club);
  public boolean approveClubRequest(String reqId);
  public boolean rejectClubRequest(String reqId);
  public boolean deleteClub(String clubID);
  public String submitJoinClubRequest(JoinClubRequest joinClubRequest);
  public List<JoinClubRequest> getAllJoinClubRequests(String clubID, String presidentEmailID);
  public boolean approveJoinClubRequest(String reqId);
  public boolean rejectJoinClubRequest(String reqId);
  public List<ClubUpdateRequest> getAllNewClubRequests(RequestStatus requestStatus);

  public List<ClubUpdateRequest> getAllUpdateClubRequests(RequestStatus requestStatus);
}
