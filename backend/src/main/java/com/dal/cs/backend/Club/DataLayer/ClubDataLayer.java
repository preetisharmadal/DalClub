package com.dal.cs.backend.Club.DataLayer;

import com.dal.cs.backend.Club.ClassObject.Category;
import com.dal.cs.backend.Club.ClassObject.Club;
import com.dal.cs.backend.Club.ClassObject.ClubUpdateRequest;
import com.dal.cs.backend.Club.ClassObject.JoinClubRequest;
import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.Enum.RequestType;
import com.dal.cs.backend.Club.ObjectBuilder.ClubBuilder;
import com.dal.cs.backend.Club.ObjectBuilder.ClubUpdateRequestBuilder;
import com.dal.cs.backend.Club.ObjectBuilder.JoinClubRequestBuilder;
import com.dal.cs.backend.baseUtils.dataLayer.BaseDataLayer;
import com.dal.cs.backend.database.IDatabaseConnection;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ClubDataLayer extends BaseDataLayer implements IClubDataLayer, IClubSecondDataLayer
{
    private static final Logger logger= LogManager.getLogger(ClubDataLayer.class);
    private String callProcedure;
    private  CallableStatement callableStatement;
    @Autowired
    public ClubDataLayer(IDatabaseConnection iDatabaseConnection)
    {
        super(iDatabaseConnection);
    }

    public static ClubDataLayer getInstance(IDatabaseConnection iDatabaseConnection) {
        return new ClubDataLayer(iDatabaseConnection);
    }

    /**
     * This method calls a stored procedure to get the last row of the table that stores the
     * new club requests
     * @return the request id of the last row of the table that stores the new club requests
     * @throws SQLException is thrown
     */
    public String getLatestRequestId() throws SQLException
    {
        logger.info("Entered DataLayer: Entered getLatestRequestId()");
        logger.info("Executing stored procedure to get latest request id");
        callProcedure="{CALL getLatestRequestId()}";
        callableStatement=connection.prepareCall(callProcedure);
        boolean procedureCallStatus=callableStatement.execute();
        logger.info("Procedure to get latest request id called with status "+procedureCallStatus);
        if(procedureCallStatus)
        {
            ResultSet resultSet = callableStatement.getResultSet();
            boolean resultStatus = resultSet.next();
            if (resultStatus)
            {
                String latestRequestId = resultSet.getString("requestID");
                logger.info("Latest request id fetched is: "+latestRequestId);
                logger.info("Exiting Datalayer: returning latest request id to Service Layer");
                return latestRequestId;
            }
        }
        logger.info("Exiting DataLayer: returning request id as null to Service Layer");
        return  null;
    }

    /**
     * This method calls a stored procedure to get the last row of the table that stores the
     * new club requests
     * @return club id of the last row of the table that stores the new club requests
     * @throws SQLException
     */
    public String getLatestClubId() throws SQLException
    {
        logger.info("Entered DataLayer: Entered getLatestClubId()");
        logger.info("Executing stored procedure to get latest club id");
        callProcedure="{CALL getLatestClubId()}";
        callableStatement=connection.prepareCall(callProcedure);
        boolean procedureCallStatus=callableStatement.execute();
        logger.info("Procedure to get latest club id called with status "+procedureCallStatus);
        if(procedureCallStatus)
        {
            ResultSet resultSet = callableStatement.getResultSet();
            boolean resultStatus = resultSet.next();
            if (resultStatus)
            {
                String latestClubId = resultSet.getString("ClubID");
                logger.info("Latest club id fetched is: "+latestClubId);
                logger.info("Exiting Datalayer: returning latest club id to Service Layer");
                return latestClubId;
            }

        }
        logger.info("Exiting DataLayer: returning club id as null to Service Layer");
        return null;
    }

    /**
     *
     * @param requestId passed as an argument from the service layer
     * @param club object that has all club details passed from the service layer
     * @param requestType parameter passed from the service layer. It will be NEW_REQUEST in this case.
     * @param requestStatus parameter passed from the service layer. It will be PENDING initially.
     * @return true if the request is created else an exception will be thrown.
     * @throws SQLException
     */
    public boolean createNewClubRequest(String requestId, Club club, String requestType, String requestStatus)throws SQLException
    {
        logger.info("Entered DataLayer: Entered createNewClubRequest()");
        callProcedure="{CALL insertIntoNewAndUpdateClubRequest(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,requestId);
        callableStatement.setString(2,club.getClubID());
        callableStatement.setString(3,club.getPresidentEmailID());
        callableStatement.setString(4,club.getCategoryID());
        callableStatement.setString(5,club.getClubName());
        callableStatement.setString(6,club.getDescription());
        callableStatement.setString(7,club.getFacebookLink());
        callableStatement.setString(8,club.getInstagramLink());
        callableStatement.setString(9,club.getLocation());
        callableStatement.setString(10,club.getMeetingTime());
        callableStatement.setString(11,club.getClubImage());
        callableStatement.setString(12,club.getRules());
        callableStatement.setString(13,requestType);
        callableStatement.setString(14,requestStatus);
        logger.info("Executing stored procedure to create a new record for new club request");
        int procedureCallStatus=callableStatement.executeUpdate();
        logger.info("Procedure to create a new club request called with status "+procedureCallStatus);
        if(procedureCallStatus > 0)
        {
            logger.info("Exiting DataLayer: returning true to Service Layer");
            return true;
        }
        logger.info("Exiting DataLayer: returning false to Service Layer");
        return false;

    }

    /**
     * Retrieves all club categories from the category table by calling the stored procedure.
     * @return A list of maps containing category names and corresponding category IDs.
     * @throws SQLException If an error occurs while executing the stored procedure.
     */
    @Override
    public ArrayList<HashMap<String, String>> getAllClubCategories() throws SQLException {

        if (connection != null) {

            logger.info("Data Layer Entered: Entered getAllClubCategories()");
            callProcedure = "{CALL selectAllFromCategory()}";
            callableStatement = connection.prepareCall(callProcedure);
            boolean resultStatus = callableStatement.execute();
            logger.info("getAllClubCategories- Procedure execution call successful, resultStatus = " + resultStatus);
            ArrayList<HashMap<String, String>> allClubCategories = null;
            if (resultStatus) {
                ResultSet resultSet = callableStatement.getResultSet();
                allClubCategories = new ArrayList<>();

                while (resultSet.next()) {
                    HashMap<String, String> categoryMap = new HashMap<>();
                    categoryMap.put("categoryID", resultSet.getString("categoryID"));
                    categoryMap.put("categoryName", resultSet.getString("categoryName"));
                    allClubCategories.add(categoryMap);
                }
                logger.info("getAllClubCategories- Category collection created successfully");
            }
            logger.info("Exiting Data Layer: Returning category collection to Service Layer");

            return allClubCategories;
        }
        else {
            logger.error("Exception: Database Connection not established.");
            return null;
        }
    }

    /**
     * This method fetches the records of all the clubs from the database table
     * @return list of clubs fetched
     * @throws SQLException
     */
    @Override
    public List<Club> getAllClubs() throws SQLException
    {
        logger.info("Entered DataLayer: Entered getAllClubs()");
        callProcedure="{CALL getAllClubs()}";
        callableStatement=connection.prepareCall(callProcedure);
        boolean procedureCallStatus=callableStatement.execute();
        logger.info("Stored procedure for getAllClubs() executed with status "+procedureCallStatus);
        ResultSet resultSet=callableStatement.getResultSet();
        List<Club> listOfAllClubs=new ArrayList<>();
        if(procedureCallStatus)
        {
            while(resultSet.next())
            {
                Club club=new ClubBuilder()
                        .setClubID(resultSet.getString("clubID"))
                        .setCategoryID(resultSet.getString("categoryID"))
                        .setClubName(resultSet.getString("clubName"))
                        .setCategoryName(resultSet.getString("categoryName"))
                        .setDescription(resultSet.getString("description"))
                        .setPresidentEmailID(resultSet.getString("presidentEmailID"))
                        .setFacebookLink(resultSet.getString("facebookLink"))
                        .setInstagramLink(resultSet.getString("instagramLink"))
                        .setLocation(resultSet.getString("location"))
                        .setMeetingTime(resultSet.getString("meetingTime"))
                        .setClubImage(resultSet.getString("clubImage"))
                        .setRules(resultSet.getString("rules"))
                        .createClub();
                listOfAllClubs.add(club);
            }
            logger.info("getAllClubs(): list of all clubs created successfully");
            logger.info("Exiting DataLayer: returning list of all clubs to Service Layer");
            return listOfAllClubs;
        }
        else
        {
            logger.error("Problem with procedure call or database connection");
            return null;
        }

    }

    /**
     * Inserts the updated club details into the request table.
     * @param requestId The ID of the request.
     * @param club The club object containing the new details.
     * @param requestType The type of the request.
     * @param requestStatus The status of the request.
     * @return True if the insert operation is successful, else an error message.
     * @throws SQLException If an error occurs while executing the stored procedure.
     */
    @Override
    public boolean insertUpdatedClubDetails(String requestId, Club club, String requestType, String requestStatus) throws SQLException {
        if (connection != null) {

            logger.info("Data Layer Entered: Entered insertUpdatedClubDetails()");
            callProcedure = "{CALL insertIntoNewAndUpdateClubRequest(?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, requestId);
            callableStatement.setString(2, club.getClubID());
            callableStatement.setString(3, club.getPresidentEmailID());
            callableStatement.setString(4, club.getCategoryID());
            callableStatement.setString(5, club.getClubName());
            callableStatement.setString(6, club.getDescription());
            callableStatement.setString(7, club.getFacebookLink());
            callableStatement.setString(8, club.getInstagramLink());
            callableStatement.setString(9, club.getLocation());
            callableStatement.setString(10, club.getMeetingTime());
            callableStatement.setString(11, club.getClubImage());
            callableStatement.setString(12, club.getRules());
            callableStatement.setString(13, requestType);
            callableStatement.setString(14, requestStatus);
            int result = callableStatement.executeUpdate();
            boolean resultStatus = (result == 1);
            logger.info("insertUpdatedClubDetails- Procedure execution call successful, resultStatus = " + resultStatus);
            logger.info("Exiting Data Layer: Returning boolean resultStatus to Service Layer");
            return resultStatus;
        } else {
            logger.error("Exception: Database Connection not established.");
            return false;
        }
    }
     /** This method fetches the club details from the club request information
     * @param reqId is the request id of the club update or new club request
     * @return Club object if stored procedure called successfully else return null
     * @throws SQLException
     */
    public Club getClubDetailsFromClubRequest(String reqId) throws SQLException
    {
        logger.info("Entered DataLayer: Entered getClubDetailsFromClubRequest()");
        callProcedure="{CALL getClubRequestInfoByRequestId(?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,reqId);
        boolean procedureCallStatus=callableStatement.execute();
        logger.info("Stored procedure for getClubRequestInfoByRequestId() executed with status "+procedureCallStatus);
        ResultSet resultSet=callableStatement.getResultSet();
        if(procedureCallStatus)
        {
            resultSet.next();
            Club club=new ClubBuilder()
                    .setClubID(resultSet.getString("clubID"))
                    .setPresidentEmailID(resultSet.getString("requestorEmailID"))
                    .setCategoryID(resultSet.getString("categoryID"))
                    .setClubName(resultSet.getString("clubName"))
                    .setDescription(resultSet.getString("description"))
                    .setFacebookLink(resultSet.getString("facebookLink"))
                    .setInstagramLink(resultSet.getString("instagramLink"))
                    .setLocation(resultSet.getString("location"))
                    .setMeetingTime(resultSet.getString("meetingTime"))
                    .setClubImage(resultSet.getString("clubImage"))
                    .setRules(resultSet.getString("rules"))
                    .createClub();
            logger.info("Exiting DataLayer: returning club details to Service Layer for request id "+reqId);
            return club;
        }
        else
        {
            logger.info("Exiting DataLayer: returning null to Service Layer");
            logger.error("Problem with procedure call or database connection");
            return null;
        }
    }

    /**
     * This method calls a stored procedure that inserts a record in the Club table to create a new club
     * @param club is the club object that has all the club details
     * @return true if the club record inserted else return false
     * @throws SQLException
     */
    public boolean createClub(Club club) throws SQLException
    {
        logger.info("Entered DataLayer: Entered createClub()");
        callProcedure="{CALL createClub(?,?,?,?,?,?,?,?,?,?,?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,club.getClubID());
        callableStatement.setString(2,club.getClubName());
        callableStatement.setString(3,club.getDescription());
        callableStatement.setString(4,club.getPresidentEmailID());
        callableStatement.setString(5,club.getFacebookLink());
        callableStatement.setString(6,club.getInstagramLink());
        callableStatement.setString(7,club.getCategoryID());
        callableStatement.setString(8,club.getLocation());
        callableStatement.setString(9,club.getMeetingTime());
        callableStatement.setString(10,club.getClubImage());
        callableStatement.setString(11,club.getRules());
        logger.info("Calling stored procedure createClub");
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("stored procedure called successfully");
            logger.info("Club record for club with Club ID: "+ club.getClubID()+" inserted successfully");
            logger.info("Exiting datalayer: returning true to ServiceLayer");
            return true;
        }
        else
        {
            logger.info("Club record not inserted.");
            logger.error("Problem with procedure call or database connection");
            logger.info("Exiting datalayer: returning false to ServiceLayer");
            return false;
        }
    }

    /**
     * This method calls the stored procedure that finds the clubs based on the name keyword condition
     * @param name string value containing the keyword upon which the search is to be made
     * @return A list of club details filtered based on the name condition.
     * @throws SQLException
     */
    public List<Club> getClubsByName(String name) throws SQLException {
            logger.info("Data Layer Entered: Entered getClubsByName()");
            callProcedure = "{CALL searchClubByName(?)}";
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1,name);
            boolean procedureCallStatus=callableStatement.execute();
            logger.info("getClubsByName- Procedure execution call successful, resultStatus = " + procedureCallStatus);
            ResultSet resultSet=callableStatement.getResultSet();
            List<Club> listOfAllClubs=new ArrayList<>();
            if(procedureCallStatus)
            {
                setClubFromResultSet(resultSet, listOfAllClubs);
                logger.info("Exiting DataLayer: returning search club by name to Service Layer");
                return listOfAllClubs;
            }
        else
        {
            logger.error("Exception: Database Connection not established.");
            return null;
        }
    }

    /**
     * This method calls the stored procedure that finds the clubs based on the category value
     * @param category string value containing the category name upon which the search is to be made
     * @return A list of club details filtered based on the category name
     * @throws SQLException
     */
    public List<Club> getClubsByCategory(String category) throws SQLException
    {
            logger.info("Data Layer Entered: Entered getClubsByCategory()");
            callProcedure = "{CALL searchClubByCategory(?)}";
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1,category);
            boolean procedureCallStatus=callableStatement.execute();
            ResultSet resultSet=callableStatement.getResultSet();
            logger.info("getClubsByCategory- Procedure execution call successful, resultStatus = " + procedureCallStatus);
            List<Club> listOfAllClubs=new ArrayList<>();
            if(procedureCallStatus)
            {
                listOfAllClubs = setClubFromResultSet(resultSet, listOfAllClubs);
                logger.info("Exiting DataLayer: returning list of all clubs by category to Service Layer");
                return listOfAllClubs;
            }
        else {
            logger.error("Exception: Database Connection not established.");
            return null;
        }
    }

    /**
     * This function populates the Club Arraylist by reading the result set obtained from procedure call
     * @param resultSet the Result set received after procedure call
     * @param listOfAllClubs list in which Club objects are to be added
     * @throws SQLException
     */
    private List<Club> setClubFromResultSet(ResultSet resultSet, List<Club> listOfAllClubs) throws SQLException {
        while(resultSet.next())
        {
            Club club = new ClubBuilder()
                    .setClubID(resultSet.getString("clubID"))
                    .setClubName(resultSet.getString("clubName"))
                    .setDescription(resultSet.getString("description"))
                    .setPresidentEmailID(resultSet.getString("presidentEmailID"))
                    .setFacebookLink(resultSet.getString("facebookLink"))
                    .setInstagramLink(resultSet.getString("instagramLink"))
                    .setCategoryID(resultSet.getString("categoryID"))
                    .setLocation(resultSet.getString("location"))
                    .setMeetingTime(resultSet.getString("meetingTime"))
                    .setClubImage(resultSet.getString("clubImage"))
                    .setRules(resultSet.getString("rules"))
                    .createClub();
            listOfAllClubs.add(club);
        }
        return listOfAllClubs;
    }

    /**
     * This method calls stored procedure  that updates the status of the club request to approved
     * @param requestId is the request id of the club update or new club request
     * @return true if the status is updated to approved successfully else return false
     * @throws SQLException
     */
    public boolean updateClubRequestStatusToApproved(String requestId) throws SQLException
    {
        logger.info("Entering DataLayer: Entered updateClubRequestStatusToApproved()");
        callProcedure="{CALL updateClubRequestStatusToApproved(?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,requestId);
        logger.info("Calling stored procedure updateClubRequestStatusToApproved()");
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("stored procedure called successfully");
            logger.info("record updated successfully");
            logger.info("Exiting DataLayer:Returning true to ServiceLayer");
            return true;
        }
        else
        {
            logger.info("record could not be updated successfully");
            logger.info("Exiting DataLayer:Returning false to ServiceLayer");
            return false;
        }
    }

    /**
     * This method calls stored procedure that updates the status of the club request to rejected
     * @param requestId is the request id of the club update or new club request
     * @return true if the status is updated to rejected successfully else return false
     * @throws SQLException
     */
    public boolean updateClubRequestStatusToRejected(String requestId) throws SQLException
    {
        logger.info("Entered Datalayer: Entered updateClubRequestStatusToRejected()");
        callProcedure="{CALL updateClubRequestStatusToRejected(?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,requestId);
        logger.info("Calling stored procedure updateClubRequestStatusToRejected()");
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("Stored procedure executed successfully.");
            logger.info("Record updated successfully");
            logger.info("Exiting DataLayer:Returning true to ServiceLayer");
            return  true;
        }
        else
        {
            logger.info("Stored procedure not executed successfully.");
            logger.info("record could not be updated successfully");
            logger.info("Exiting DataLayer:Returning false to ServiceLayer");
        }
        logger.info("Exiting DataLayer:Returning false to ServiceLayer");
        return false;
    }
     /** Deletes the club record from the club table based on the clubID
     *
     * @param clubID The clubID value for the club record to delete.
     * @return A boolean response result, which returns true if club record deleted successfully, else returns false
     * @throws SQLException
     */
    @Override
    public boolean deleteClub(String clubID) throws SQLException {
        boolean resultStatus = false;
        if (connection != null) {
            logger.info("Data Layer Entered: Entered deleteClub()");
            callProcedure = "{CALL deleteClub(?)}";
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, clubID);
            int result = callableStatement.executeUpdate();
            if (result == 0)
                resultStatus = false;
            else if (result > 0)
                resultStatus = true;
            logger.info("deleteClub- Procedure execution call successful, resultStatus = " + resultStatus);
            logger.info("Exiting Data Layer: Returning boolean resultStatus to Service Layer");
            return resultStatus;
        }
        else {
            logger.error("Exception: Database Connection not established.");
            return false;
        }
    }

    /**
     * This method calls a stored procedure that fetches the Latest Join Club RequestId.
     * @return the latest request id else return null
     * @throws SQLException
     */
    @Override
    public String getLatestJoinClubRequestId() throws SQLException
    {
        logger.info("Entered Datalayer: inside getLatestJoinClubRequestId() ");
        logger.info("Executing stored procedure to get latest join club request id");
        callProcedure="{CALL getLatestJoinClubRequestId()}";
        callableStatement=connection.prepareCall(callProcedure);
        boolean procedureCallStatus=callableStatement.execute();
        logger.info("Procedure to get latest join club request id called with status "+procedureCallStatus);
        if(procedureCallStatus)
        {
            ResultSet resultSet = callableStatement.getResultSet();
            boolean resultStatus = resultSet.next();
            if (resultStatus)
            {
                String latestRequestId = resultSet.getString("requestID");
                logger.info("Latest join club request id fetched is: "+latestRequestId);
                logger.info("Exiting Datalayer: returning latest join club request id to Service Layer");
                return latestRequestId;
            }
        }
        logger.info("Exiting DataLayer: returning join club request id as null to Service Layer");
        return  null;
    }

    /**
     * This method calls a stored procedure that inserts the join club request details to the database table
     * @param joinClubRequest is the real word entity that contains the join club request details
     * @return true if the record is inserted else return false
     * @throws SQLException
     */
    public boolean insertJoinClubRequest(JoinClubRequest joinClubRequest) throws SQLException
    {
        logger.info("Datalayer: Entered insertJoinClubRequest in DataLayer");
        callProcedure="{CALL insertJoinClubRequestDetails(?,?,?,?,?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,joinClubRequest.getRequestID());
        callableStatement.setString(2,joinClubRequest.getRequesterEmailID());
        callableStatement.setString(3,joinClubRequest.getClubID());
        callableStatement.setString(4,joinClubRequest.getJoiningReason());
        callableStatement.setString(5,joinClubRequest.getRequestStatus().toString());
        logger.info("Calling stored procedure to insert Join Club Request");
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("stored procedure called successfully");
            logger.info("join club request with request id: "+joinClubRequest.getRequestID()+ "inserted successfully");
            logger.info("Exiting datalayer: returning true to ServiceLayer");
            return true;
        }
        else
        {
            logger.info("join club request not inserted.");
            logger.error("Problem with procedure call or database connection");
            logger.info("Exiting datalayer: returning false to ServiceLayer");
            return false;
        }
    }

    /**
     * Gets join club request
     * @param requestID String
     * @return Join club requests for a club managed by the president
     * @throws SQLException
     */
    @Override
    public JoinClubRequest getJoinClubRequest(String requestID) throws SQLException {
        if (connection != null) {
            logger.info("Data Layer Entered: Entered getJoinClubRequest()");
            callProcedure = getProcedureCallString("getJoinClubRequest", 1);
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, requestID);
            boolean procedureCallStatus = callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            if (procedureCallStatus) {
                resultSet.next();
                    JoinClubRequestBuilder joinClubRequestBuilder = new JoinClubRequestBuilder()
                            .setRequestID(resultSet.getString("requestID"))
                            .setRequesterEmailID(resultSet.getString("requestorEmailID"))
                            .setClubID(resultSet.getString("clubID"))
                            .setJoiningReason(resultSet.getString("joiningReason"));
                    RequestStatus requestStatus = RequestStatus.valueOf(resultSet.getString("requestStatus"));
                    joinClubRequestBuilder.setRequestStatus(requestStatus);
                    logger.info("Exiting DataLayer: returning join club request to Service Layer");
                    return joinClubRequestBuilder.createJoinClubRequest();
            }
            logger.error("Problem with procedure call or database connection");
            return null;
        }
        logger.error("Exception: Database Connection not established.");
        return null;
    }

     /**
     * Gets list of club requests by joining club and join club request with club id and verifies president
     * @param clubID String
     * @param presidentEmailID String
     * @return List of join club requests for a club managed by the president
     * @throws SQLException
     */
    @Override
    public List<JoinClubRequest> getAllJoinClubRequests(String clubID, String presidentEmailID) throws SQLException {
        if (connection != null) {
            logger.info("Data Layer Entered: Entered getAllJoinClubRequests()");
            callProcedure = getProcedureCallString("getAllJoinClubRequests", 2);
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, clubID);
            callableStatement.setString(2, presidentEmailID);
            boolean procedureCallStatus = callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            List<JoinClubRequest> joinClubRequests = new ArrayList<>();
            if (procedureCallStatus) {
                while (resultSet.next()) {
                    JoinClubRequestBuilder joinClubRequestBuilder = new JoinClubRequestBuilder()
                            .setRequestID(resultSet.getString("requestID"))
                            .setRequesterEmailID(resultSet.getString("requestorEmailID"))
                            .setClubID(resultSet.getString("clubName"))
                            .setJoiningReason(resultSet.getString("joiningReason"));
                    RequestStatus requestStatus = RequestStatus.valueOf(resultSet.getString("requestStatus"));
                    joinClubRequestBuilder.setRequestStatus(requestStatus);

                    joinClubRequests.add(joinClubRequestBuilder.createJoinClubRequest());
                }
                logger.info("getAllJoinClubRequest(): list of all join club requests created successfully");
                logger.info("Exiting DataLayer: returning list of all join club request to Service Layer");
                return joinClubRequests;
            } else {
                logger.error("Problem with procedure call or database connection");
                return null;
            }
        }
        else {
            logger.error("Exception: Database Connection not established.");
            return null;
        }
    }

    /**
     * This method calls the stored procedure that updates the request status of a join club request to approved
     * @param reqId is the request id of the join club request of the member
     * @return true if the record is updated else return false
     */
    @Override
    public boolean updateJoinClubRequestStatusToApproved(String reqId) throws SQLException
    {
        logger.info("Entered Datalayer: inside updateJoinClubRequestStatusToApproved()");
        logger.info("updateJoinClubRequestStatusToApproved(): calling stored procedure");
        callProcedure="{CALL updateJoinClubRequestStatusToApproved(?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,reqId);
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("stored procedure called successfully");
            logger.info("request status of join club request with request id: "+ reqId+ "updated to approved successfully");
            logger.info("Exiting datalayer: returning true to ServiceLayer");
            return true;
        }
        else
        {
            logger.info("request status of join club request with request id: "+ reqId+ " not updated to approved successfully");
            logger.error("Problem with procedure call or database connection");
            logger.info("Exiting datalayer: returning false to ServiceLayer");
            return false;
        }
    }
    @Override
    public boolean deleteJoinClubRequest(String reqId) throws SQLException
    {
        logger.info("Entered Datalayer: deleteJoinClubRequest()");
        logger.info("deleteJoinClubRequest(): calling stored procedure");
        callProcedure="{CALL deleteJoinClubRequest(?)}";
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1,reqId);
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("stored procedure called successfully");
            logger.info("join club request with request id: "+ reqId+ " deleted");
            logger.info("Exiting datalayer: returning true to ServiceLayer");
            return true;
        }
        else
        {
            logger.info("join club request with request id: "+ reqId+ " not deleted");
            logger.error("Problem with procedure call or database connection");
            logger.info("Exiting datalayer: returning false to ServiceLayer");
            return false;
        }
    }

    /**
     * Gets all update clubs requests based on request type from database
     * @param requestType Filter request on type
     * @param requestStatus Filter
     * @return List of new club requests
     * @throws SQLException
     */
    @Override
    public List<ClubUpdateRequest>  getAllClubRequests(RequestType requestType, RequestStatus requestStatus) throws SQLException {
        if (connection != null) {
            logger.info("Entered getAllClubRequests()");
            callProcedure = getProcedureCallString("getAllClubRequests", 2);
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, requestType.name());
            callableStatement.setString(2, requestStatus.name());
            boolean procedureCallStatus = callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            List<ClubUpdateRequest> clubUpdateRequests = new ArrayList<>();
            if (procedureCallStatus) {
                while (resultSet.next()) {
                    ClubUpdateRequest clubUpdateRequest = new ClubUpdateRequestBuilder()
                            .setRequestID(resultSet.getString("requestID"))
                            .setClubID(resultSet.getString("clubID"))
                            .setRequesterEmailID(resultSet.getString("requestorEmailID"))
                            .setClubID(resultSet.getString("clubID"))
                            .setCategoryName(resultSet.getString("categoryName"))
                            .setClubName(resultSet.getString("clubName"))
                            .setDescription(resultSet.getString("description"))
                            .setFacebookLink(resultSet.getString("facebookLink"))
                            .setInstagramLink(resultSet.getString("instagramLink"))
                            .setLocation(resultSet.getString("location"))
                            .setMeetingTime(resultSet.getString("meetingTime"))
                            .setClubImage(resultSet.getString("clubImage"))
                            .setRules(resultSet.getString("rules"))
                            .setRequestType(RequestType.valueOf(resultSet.getString("requestType")))
                            .setRequestStatus(RequestStatus.valueOf(resultSet.getString("requestStatus")))
                            .createClubUpdateRequest();
                    clubUpdateRequests.add(clubUpdateRequest);
                }
                logger.info("getAllClubRequests(): list of all club requests created successfully");
                logger.info("Exiting DataLayer: returning list of all club request");
                return clubUpdateRequests;
            } else {
                logger.error("Problem with procedure call or database connection");
                return null;
            }
        }
        else {
            logger.error("Exception: Database Connection not established.");
            return null;
        }
    }

    @Override
    public ClubUpdateRequest getClubRequest(String requestID) throws SQLException {
        if (connection != null) {
            logger.info("Entered getClubRequest()");
            callProcedure = getProcedureCallString("getClubRequestInfoByRequestId", 1);
            callableStatement = connection.prepareCall(callProcedure);
            callableStatement.setString(1, requestID);
            boolean procedureCallStatus = callableStatement.execute();
            ResultSet resultSet = callableStatement.getResultSet();
            if (procedureCallStatus) {
                resultSet.next();
                ClubUpdateRequest clubUpdateRequest = new ClubUpdateRequestBuilder()
                        .setRequestID(resultSet.getString("requestID"))
                        .setClubID(resultSet.getString("clubID"))
                        .setRequesterEmailID(resultSet.getString("requestorEmailID"))
                        .setClubID(resultSet.getString("clubID"))
                        .setCategoryName(resultSet.getString("categoryID"))
                        .setClubName(resultSet.getString("clubName"))
                        .setDescription(resultSet.getString("description"))
                        .setFacebookLink(resultSet.getString("facebookLink"))
                        .setInstagramLink(resultSet.getString("instagramLink"))
                        .setLocation(resultSet.getString("location"))
                        .setMeetingTime(resultSet.getString("meetingTime"))
                        .setClubImage(resultSet.getString("clubImage"))
                        .setRules(resultSet.getString("rules"))
                        .setRequestType(RequestType.valueOf(resultSet.getString("requestType")))
                        .setRequestStatus(RequestStatus.valueOf(resultSet.getString("requestStatus")))
                        .createClubUpdateRequest();
                    logger.info("Exiting DataLayer: returning club request");
                    return clubUpdateRequest;
            }
            logger.error("Problem with procedure call or database connection");
            return null;

        }
        logger.error("Exception: Database Connection not established.");
        return null;
    }

    @Override
    public boolean createClubCategory(Category category) throws SQLException {
        logger.info("Entered Datalayer: createClubCategory()");
        logger.info("createClubCategory(): calling stored procedure");
        callProcedure=getProcedureCallString("createClubCategory", 2);
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1, category.getCategoryID());
        callableStatement.setString(2, category.getCategoryName());
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("Added category " + category.getCategoryName() + " with id "+ category.getCategoryID() );
            return true;
        }
        else
        {
            logger.error("Failed to add category " + category.getCategoryName() + " with id "+ category.getCategoryID() );
            return false;
        }
    }

    @Override
    public boolean deleteClubCategory(String categoryID) throws SQLException {
        logger.info("Entered Datalayer: deleteClubCategory()");
        logger.info("deleteClubCategory(): calling stored procedure");
        callProcedure=getProcedureCallString("deleteClubCategory", 1);
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1, categoryID);
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("Deleted category with id "+ categoryID );
            return true;
        }
        else
        {
            logger.error("Failed to delete category with id "+ categoryID );
            return false;
        }
    }

    @Override
    public boolean deleteClubRequest(String requestID) throws SQLException {
        logger.info("Entered Datalayer: deleteClubRequest()");
        logger.info("deleteClubRequest(): calling stored procedure");
        callProcedure=getProcedureCallString("deleteClubRequest", 1);
        callableStatement=connection.prepareCall(callProcedure);
        callableStatement.setString(1, requestID);
        int procedureCallStatus=callableStatement.executeUpdate();
        if(procedureCallStatus>0)
        {
            logger.info("Deleted club request with id "+ requestID );
            return true;
        }
        else
        {
            logger.error("Failed to delete request with id "+ requestID );
            return false;
        }
    }

}
