package com.dal.cs.backend.Club.ServiceLayer;

import com.dal.cs.backend.Club.ClassObject.Club;
import com.dal.cs.backend.Club.ClassObject.ClubUpdateRequest;
import com.dal.cs.backend.Club.ClassObject.JoinClubRequest;
import com.dal.cs.backend.Club.DataLayer.IClubDataLayer;
import com.dal.cs.backend.Club.DataLayer.IClubSecondDataLayer;
import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.Enum.RequestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ClubServiceLayer implements  IClubServiceLayer
{
    private static final Logger logger= LogManager.getLogger(ClubServiceLayer.class);

    IClubDataLayer iClubDataLayer;
    IClubSecondDataLayer iClubSecondDataLayer;

    @Autowired
    public ClubServiceLayer(IClubDataLayer iClubDataLayer, IClubSecondDataLayer iClubSecondDataLayer) {
        this.iClubDataLayer = iClubDataLayer;
        this.iClubSecondDataLayer = iClubSecondDataLayer;
    }

    public static ClubServiceLayer getInstance(IClubDataLayer iClubDataLayer, IClubSecondDataLayer iClubSecondDataLayer) {
        return new ClubServiceLayer(iClubDataLayer, iClubSecondDataLayer);
    }

    /**
     * This method generates and sets the club id of a new club. It also generates a new request id(ticket)
     * for the new club
     * @param club represents the real world club object that has all the information of the club
     * @return message that request generated successfully or not
     */
    @Override
    public String createNewClubRequest(Club club)
    {
        logger.info("Entered ServiceLayer: createNewClubRequest() entered ");
        logger.info("Calling generateRequestId()");
        String requestId=generateRequestId();
        logger.info("Calling generateClubId()");
        String clubId=generateClubId();
        club.setClubID(clubId);
        String requestType= String.valueOf(RequestType.NEW_REQUEST);
        String requestStatus=String.valueOf(RequestStatus.PENDING);
        try
        {
            logger.info("Calling createNewClubRequest() of DataLayer");
            boolean createNewClubRequestStatus = iClubDataLayer.createNewClubRequest(requestId, club, requestType, requestStatus);
            if (createNewClubRequestStatus)
            {
                String message = "Your request for new club creation has been submitted to the Admin with request id: " + requestId;
                logger.info("new club request created successfully");
                logger.info("Exiting ServiceLayer");
                return message;
            }
        }
        catch(SQLException e)
        {
            logger.error(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        String errorMessage = "There was a problem submitting your request. Please raise a new request.";
        logger.info("Exiting ServiceLayer");
        return errorMessage;
    }

    /**
     * This method generates a new incremented request id when a new request for club creation is submitted.
     * @return the incremented request id
     */
    private String generateRequestId()
    {
        logger.info("inside generateRequestId() in ClubServiceLayer ");
        try
        {
            final int one=1;
            String latestRequestId = iClubSecondDataLayer.getLatestRequestId();
            if(latestRequestId != null)
            {
                List<String> splitLatestRequestId = List.of(latestRequestId.split("_"));
                int requestNumber= Integer.parseInt(splitLatestRequestId.get(1));
                int newRequestNumber=requestNumber+one;
                return splitLatestRequestId.get(0).concat("_").concat(String.valueOf(newRequestNumber));
            }
            return "REQ_1";
        }
        catch (SQLException e)
        {
            logger.error(e.getMessage());
        }
        return "";
    }

    /**
     * This method generates a new incremented club id when a new request for club creation is submitted.
     * @return the incremented club id
     */
    private String generateClubId()
    {
        logger.info("inside generateClubId() in ClubServiceLayer ");
        try
        {
            final int one=1;
            String latestClubId = iClubSecondDataLayer.getLatestClubId();
            if(latestClubId != null)
            {
                List<String> splitLatestClubId = List.of(latestClubId.split("_"));
                int clubNUmber=Integer.parseInt(splitLatestClubId.get(1));
                int newClubNumber=clubNUmber+one;
                return splitLatestClubId.get(0).concat("_").concat(String.valueOf(newClubNumber));
            }
            return "CLB_1";
        }
        catch (SQLException e)
        {
            logger.error(e.getMessage());
        }
        return "";
    }

    /**
     * Retrieves all club categories by invoking the corresponding data layer function.
     *
     * @return A list of maps containing category names and corresponding category IDs.
     */
    @Override
    public ArrayList<HashMap<String, String>>  getAllClubCategories() {
        try {
            logger.info("Service Layer Entered: Entered getAllClubCategories- Calling Data layer getAllClubCategories");
            ArrayList<HashMap<String, String>> allClubCategories = iClubDataLayer.getAllClubCategories();
            logger.info("Exiting Service Layer: Returning category collection to Controller");
            return allClubCategories;
        } catch (SQLException e) {
            logger.error("getAllClubCategories- SQL Exception occurred while getting response from Data layer"+e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * This method fetches all the clubs
     * @return list of all clubs
     */
    @Override
    public List<Club> getAllClubs()
    {
        logger.info("Service Layer Entered: Entered getAllClubs()- Calling Data layer getAllClubs()");
        try
        {
            return iClubDataLayer.getAllClubs();
        }
        catch(SQLException e)
        {
            logger.error("getAllClubs()- SQL exception occurred while getting response from Data Layer"+e.getMessage());
        }
        logger.info("ServiceLayer: getAllClubs() returned null to Controller");
        return null;
    }

    /**
     * This method retrieves the club based on the search name keyword
     * @param name string value containing search name keyword
     * @return list of all clubs filtered on the search name keyword condition
     */
    @Override
    public List<Club> getClubsByName(String name)
    {
        logger.info("Service Layer Entered: Entered getClubsByName()- Calling Data layer getClubsByName()");
        try{
            return iClubDataLayer.getClubsByName(name);
        } catch(SQLException e)
        {
            logger.error("getClubsByName()- SQL exception occurred while getting response from Data Layer"+e.getMessage());
        }
        logger.info("ServiceLayer: getClubsByName() returned null to Controller");
        return null;
    }

    /**
     * This method retrievs the club based on the category name
     * @param category string value containing category name
     * @return list of all clubs filtered on the category condition
     */
    @Override
    public List<Club> getClubsByCategory(String category) {
        logger.info("Service Layer Entered: Entered getClubsByCategory()- Calling Data layer getClubsByCategory()");
        try{
            return iClubDataLayer.getClubsByCategory(category);
        } catch(SQLException e)
        {
            logger.error("getClubsByCategory()- SQL exception occurred while getting response from Data Layer"+e.getMessage());
        }
        logger.info("ServiceLayer: getClubsByCategory() returned null to Controller");
        return null;
    }

    /**
     * Inserts the updated club details into the request table by invoking the corresponding data layer function.
     * @param club The club object containing the new details.
     * @return The request ID if the data layer operation is successful, else an error message.
     */
    @Override
    public String updateClubDetails(Club club) {
        logger.info("Service Layer Entered: Entered updateClubDetails- Calling Data layer insertUpdatedClubDetails");
        String errorMessage;
        String requestId = generateRequestId();
        String requestType = String.valueOf(RequestType.UPDATE_REQUEST);
        String requestStatus = String.valueOf(RequestStatus.PENDING);
        try {
            boolean resultStatus = iClubDataLayer.insertUpdatedClubDetails(requestId, club, requestType, requestStatus);
            if (resultStatus) {
                logger.info("Exiting Service Layer: Returning requestId to Controller");
                return requestId;
            } else {
                errorMessage = "Unable to insert updated club detail values.";
            }
        } catch (SQLException e) {
            errorMessage = e.getMessage();
            logger.error("Exception occured in 'updateClubDetails': " + errorMessage);
        } catch (Exception e) {
            errorMessage = e.getMessage();
            logger.error("Exception occured in 'updateClubDetails': " + errorMessage);
        }
        logger.info("Exiting Service Layer: Returning error message to Controller");
        return errorMessage;
    }
     /** This method  first gets the club details from the club request table and then inserts those values
     * This method  first gets the club details from the club request table and then inserts those values
     * into the club table. Once inserted, it updates the club request status to approved.
     * @param reqId is the request id of the club update or new club request
     * @return true if the request status is updated to approved else return false
     */
    @Override
    public boolean approveClubRequest(String reqId)
    {
        logger.info("Service Layer Entered: Entered approveClubRequest()- Performing input validation for Request Id");
        if(reqId==null||reqId.equals(""))
        {
            return false;
        }
        logger.info("input validation for Request Id passed- Calling getClubDetailsFromClubRequest() of DataLayer");
        try
        {
            Club club = iClubDataLayer.getClubDetailsFromClubRequest(reqId);
            logger.info("Club details returned from getClubDetailsFromClubRequest() of DataLayer");
            logger.info("Service Layer: calling createClub() in DataLayer");
            boolean clubCreationStatus=iClubDataLayer.createClub(club);
            if(clubCreationStatus)
            {
                logger.info("ServiceLayer: club created successfully");
                logger.info("Calling updateClubRequestStatusToApproved() in DataLayer to update the club request status to approved");
                boolean updateClubRequestStatus=iClubDataLayer.updateClubRequestStatusToApproved(reqId);
                if(updateClubRequestStatus)
                {
                    logger.info("ServiceLayer: club request status updated to Approved.");
                    logger.info("Exiting Service Layer: Returning true to Controller");
                    return true;
                }
                else
                {
                    logger.info("ServiceLayer: club request status could not be updated to Approved.");
                    logger.info("Exiting Service Layer: Returning false to Controller");
                    return false;
                }
            }
            else
            {
                logger.info("ServiceLayer: club could not be created successfully");
                logger.info("Exiting ServiceLayer: Returning false to controller");
                return false;
            }

        }
        catch(SQLException e)
        {
            logger.error(" approveClubRequest()- SQL exception occurred in DataLayer"+e.getMessage());
        }
        logger.info("Exiting ServiceLayer: Returning false to controller");
        return false;
    }

    /**
     * This method updates the club request status to rejected
     * @param reqId is the request id of the club update or new club request
     * @return true if the request status is updated to rejected else return false
     */
     @Override
     public boolean rejectClubRequest(String reqId)
     {
         logger.info("Service Layer Entered: Entered rejectClubRequest()- Performing input validation for Request Id");
         if(reqId==null||reqId.equals(""))
         {
             return false;
         }
         logger.info("input validation for Request Id passed- Calling updateClubRequestStatusToRejected() of DataLayer");
         try
         {
             boolean updateClubRequestStatus=iClubDataLayer.updateClubRequestStatusToRejected(reqId);
             if(updateClubRequestStatus)
             {
                 logger.info("ServiceLayer: club request status updated to rejected successfully");
                 logger.info("Exiting Service Layer: returning true to the Controller");
                 return true;
             }
             else
             {
                 logger.info("ServiceLayer: club request status could not be updated to rejected.");
                 logger.info("Exiting Service Layer: returning false to the Controller");
                 return false;
             }
         }
         catch(SQLException e)
         {
           logger.error("ServiceLayer: SQL exception occurred in DataLayer "+ e.getMessage());
         }
         logger.info("Exiting Service Layer: returning false to the Controller");
         return false;
     }

     /** Deletes the club record from the club table by invoking the corresponding data layer function.
     *
     * @param clubID The clubID value for the club record to delete.
     * @return A boolean response result, which returns true if club record deleted successfully, else returns false
     */
    @Override
    public boolean deleteClub(String clubID) {
        logger.info("Service Layer Entered: Entered deleteClub- Calling Data layer deleteClub");
        boolean resultStatus;
        String errorMessage;
        try {
            resultStatus = iClubDataLayer.deleteClub(clubID);
            logger.info("Exiting Service Layer: Returning boolean resultStatus to Controller");
            return resultStatus;
        }
        catch (SQLException e) {
            errorMessage = e.getMessage();
            logger.error("Exception occured in 'deleteClub': "+errorMessage);
        }
        catch (Exception e)
        {
            errorMessage = e.getMessage();
            logger.error("Exception occured in 'deleteClub': "+errorMessage);
        }
        logger.info("Exiting Service Layer: Returning error message to Controller");
        return false;
    }

    /**
     * This method first call a method to generate a new request id. This request id is added to the
     * joinClubRequest object. Finally, the  joinClubRequest object is passed to the insertion method in
     * datalayer.
     * @param joinClubRequest is the real word entity that contains the join club request details
     * @return a success message if request submitted else return a failure message.
     */
    @Override
    public String submitJoinClubRequest(JoinClubRequest joinClubRequest)
    {
        logger.info("Entered ServiceLayer: submitJoinClubRequest() entered ");
        logger.info("ServiceLayer:Calling generateJoinClubRequestId()");
        String joinClubRequestId=generateJoinClubRequestId();
        joinClubRequest.setRequestID(joinClubRequestId);
        RequestStatus requestStatus=RequestStatus.PENDING;
        joinClubRequest.setRequestStatus(requestStatus);
        try
        {
            logger.info("ServiceLayer: calling insertJoinClubRequest() of the DataLayer");
            boolean insertJoinClubRequestStatus=iClubDataLayer.insertJoinClubRequest(joinClubRequest);
            if(insertJoinClubRequestStatus)
            {
                String message = "Your request for joining club has been submitted to the club president with request id: " + joinClubRequestId;
                logger.info("ServiceLayer: new join club request created successfully");
                logger.info("Exiting ServiceLayer: Returning success message to the Controller");
                return message;
            }

        }
        catch(SQLException e)
        {
            logger.error(e.getMessage());
        }
        catch (Exception e)
        {
            logger.error(e.getMessage());
        }
        String errorMessage = "There was a problem submitting your request. Please raise a new request.";
        logger.info("Exiting ServiceLayer: returning error message to the Controller");
        return errorMessage;

    }

    /**
     * This method fetches the latest join club request id  and increments it by one to generate a
     * new request id
     * @return the new generated request id
     */
    private String generateJoinClubRequestId()
    {
        logger.info("ServiceLayer: inside generateJoinClubRequestId() ");
        try
        {
            final int one=1;
            logger.info(("ServiceLayer: calling getLatestJoinClubRequestId() of Datalayer"));
            String latestRequestId = iClubSecondDataLayer.getLatestJoinClubRequestId();
            if(latestRequestId != null)
            {
                List<String> splitLatestRequestId = List.of(latestRequestId.split("_"));
                int requestNumber= Integer.parseInt(splitLatestRequestId.get(1));
                int newRequestNumber=requestNumber+one;
                return splitLatestRequestId.get(0).concat("_").concat(String.valueOf(newRequestNumber));
            }
            return "REQ_1";
        }
        catch (SQLException e)
        {
            logger.error(e.getMessage());
        }
        return "";
    }
     /**
     * Get list of all join club requests filtered using club id and president email id
     * @param clubID String
     * @param presidentEmailID String
     * @return List of join club requests for a club managed by the president
     */
    @Override
    public List<JoinClubRequest> getAllJoinClubRequests(String clubID, String presidentEmailID) {
        try {
            logger.info("Service Layer Entered: Entered getAllJoinClubRequests- Calling Data layer getAllJoinClubRequests");
            List<JoinClubRequest> joinClubRequestList = iClubDataLayer.getAllJoinClubRequests(clubID ,presidentEmailID);
            logger.info("Exiting Service Layer: Returning join club requests to Controller");
            return joinClubRequestList;
        } catch (SQLException e) {
            logger.error("getAllJoinClubRequests- SQL Exception occurred while getting response from Data layer" + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * This method calls the method in datalayer to update the request status to approved
     * @param reqId is the request id of the join club request of the member
     * @return true if the request status changed  to approved else return false
     */
    @Override
    public boolean approveJoinClubRequest(String reqId)
    {
        logger.info("Entered ServiceLayer: Entered approveJoinClubRequest() ");
        logger.info("approveJoinClubRequest(): performing input validation for request id");
        if(reqId==null||reqId.equals(""))
        {
            return false;
        }
        logger.info("approveJoinClubRequest(): input validation for request id passed");
        logger.info("approveJoinClubRequest(): calling updateJoinClubRequestStatusToApproved() of datalayer");
        try
        {
            boolean approveJoinClubRequestStatus = iClubDataLayer.updateJoinClubRequestStatusToApproved(reqId);
            if (approveJoinClubRequestStatus)
            {
                logger.info("ServiceLayer: join club request approved.");
                logger.info("Exiting ServiceLayer: returning true to the Controller.");
                return true;
            }
            else
            {
                logger.info("ServiceLayer: join club request could not be approved.");
                logger.info("Exiting ServiceLayer: returning false to the Controller.");
                return false;
            }
        }
        catch(SQLException e)
        {
            logger.error("ServiceLayer: SQL exception occurred while calling updateJoinClubRequestStatusToApproved()"+e.getMessage());
            logger.info("Exiting ServiceLayer: returning false to the Controller.");
            return false;
        }
    }

    /**
     * This method calls the method in datalayer to delete the join club request
     * @param reqId is the request id of the join club request of the member
     * @return true if the join club request deleted  else return false
     */
    public boolean rejectJoinClubRequest(String reqId)
    {
        logger.info("Entered ServiceLayer: Entered rejectJoinClubRequest()");
        logger.info("rejectJoinClubRequest(): performing input validation for request id");
        if(reqId==null||reqId.equals(""))
        {
            return false;
        }
        logger.info("rejectJoinClubRequest(): input validation for request id passed");
        logger.info("rejectJoinClubRequest(): calling deleteJoinClubRequest() of datalayer");
        try
        {
            boolean rejectJoinClubRequestStatus = iClubDataLayer.deleteJoinClubRequest(reqId);
            if (rejectJoinClubRequestStatus)
            {
                logger.info("ServiceLayer: join club request rejected.");
                logger.info("Exiting ServiceLayer: returning true to the Controller.");
                return true;
            }
            else
            {
                logger.info("ServiceLayer: join club request could not be rejected.");
                logger.info("Exiting ServiceLayer: returning false to the Controller.");
                return false;
            }
        }
        catch(SQLException e)
        {
            logger.error("ServiceLayer: SQL exception occurred while calling deleteJoinClubRequest()"+e.getMessage());
            logger.info("Exiting ServiceLayer: returning false to the Controller.");
            return false;
        }
    }

    /**
     * Get list of all new club requests based on request status
     * @param requestStatus filter requests
     * @return list of new club requests filtered with requestStatus
     */
    @Override
    public List<ClubUpdateRequest> getAllNewClubRequests(RequestStatus requestStatus) {
        try {
            logger.info("Service Layer Entered: Entered getAllNewClubRequests- Calling Data layer getAllNewClubRequests");
            List<ClubUpdateRequest> clubUpdateRequests = iClubDataLayer.getAllClubRequests(RequestType.NEW_REQUEST ,requestStatus);
            logger.info("Exiting Service Layer: Returning new club requests to Controller");
            return clubUpdateRequests;
        } catch (SQLException e) {
            logger.error("getAllNewClubRequests- SQL Exception occurred while getting response from Data layer" + e.getMessage());
            throw new RuntimeException(e);
        }
    }


    /**
     * Get list of all club update requests based on request status
     * @param requestStatus filter requests
     * @return list of club update requests filtered with requestStatus
     */
    @Override
    public List<ClubUpdateRequest> getAllUpdateClubRequests(RequestStatus requestStatus) {
        try {
            logger.info("Service Layer Entered: Entered getAllUpdateClubRequests- Calling Data layer getAllUpdateClubRequests");
            List<ClubUpdateRequest> clubUpdateRequests = iClubDataLayer.getAllClubRequests(RequestType.UPDATE_REQUEST ,requestStatus);
            logger.info("Exiting Service Layer: Returning update club requests to Controller");
            return clubUpdateRequests;
        } catch (SQLException e) {
            logger.error("getAllUpdateClubRequests- SQL Exception occurred while getting response from Data layer" + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}