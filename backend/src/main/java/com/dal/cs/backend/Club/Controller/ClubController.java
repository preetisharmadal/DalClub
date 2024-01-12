package com.dal.cs.backend.Club.Controller;

import com.dal.cs.backend.Club.ClassObject.Club;
import com.dal.cs.backend.Club.ClassObject.ClubUpdateRequest;
import com.dal.cs.backend.Club.ClassObject.JoinClubRequest;
import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.ServiceLayer.IClubServiceLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders="*")
@RestController
public class ClubController
{
    private static final Logger logger= LogManager.getLogger(ClubController.class);

    IClubServiceLayer iClubServiceLayer;

    @Autowired
    public ClubController(IClubServiceLayer iClubServiceLayer) {
        this.iClubServiceLayer = iClubServiceLayer;
    }

    /**
     * This method accepts the club details  for a new club request.
     * @param club is the entity to which all the club details submitted by the user are mapped.
     * @return a message to the user with the request id in case request is submitted or an error message
     * if the request is not submitted
     */
    @RequestMapping(method = RequestMethod.POST,value="/member/registerClub")
    public String createClubRequest(@RequestBody Club club)
    {
        logger.info("Controller Entered: Received a new request for club creation");
        logger.info(" createClubRequest- calling createClubRequest() of ServiceLayer");
        String message=iClubServiceLayer.createNewClubRequest(club);
        logger.info("Exiting Controller: returning message if create club request generated or not");
        return message;
    }

    /**
     * Retrieves all club categories.
     *
     * @return A list of maps containing category names and corresponding category IDs.
     */
    @RequestMapping(method = RequestMethod.GET, value="/unauthenticated/getAllClubCategory")
    public ArrayList<HashMap<String, String>> getAllClubCategories() {
        logger.info("Controller Entered: Received request for getting all club categories.");
        logger.info("getAllClubCategories- Calling Service layer getAllClubCategories");
        ArrayList<HashMap<String, String>> allClubCategories = iClubServiceLayer.getAllClubCategories();
        logger.info("Exiting Controller: Returning categories collection to Frontend via GET /getAllClubCategory");
        return allClubCategories;
    }

    /**
     * This receives request to retrieve a list of all clubs in DalClubs
     * @return list of clubs
     */
    @RequestMapping(method = RequestMethod.GET, value="/unauthenticated/getAllClubs")
    public List<Club> getAllClubs()
    {
        logger.info("Controller Entered: Received request to get all clubs.");
        logger.info("getAllClubs- Calling getAllClubs() of ServiceLayer");
        List<Club> listOfAllClubs=iClubServiceLayer.getAllClubs();
        logger.info("Exiting Controller: Returning list of clubs to Frontend via GET /getAllClubs");
        return listOfAllClubs;
    }

    /**
     * This method retrieves the clubs filtered by the search keywords
     * @param name string value containing the search keyword value
     * @return list of clubs filtered by name
     */
    @RequestMapping(method = RequestMethod.GET, value="/unauthenticated/getClubByName/{name}")
    public List<Club> getClubsByName(@PathVariable("name") String name)
    {
        logger.info("Controller Entered: Received request to get clubs by name.");
        logger.info("getClubsByName- Calling getClubsByName() of ServiceLayer");
        List<Club> listOfAllClubs=iClubServiceLayer.getClubsByName(name);
        logger.info("Exiting Controller: Returning list of clubs to Frontend via GET /getClubByName/{name}");
        return listOfAllClubs;
    }

    /**
     * This method retrieves the clubs filtered by category name
     * @param category string value containing the category name value
     * @return list of clubs filtered by category name
     */
    @RequestMapping(method = RequestMethod.GET, value="/unauthenticated/getClubByCategory/{category}")
    public List<Club> getClubsByCategory(@PathVariable("category") String category)
    {
        logger.info("Controller Entered: Received request to get clubs by category.");
        logger.info("getClubsByCategory- Calling getClubsByCategory() of ServiceLayer");
        List<Club> listOfAllClubs=iClubServiceLayer.getClubsByCategory(category);
        logger.info("Exiting Controller: Returning list of clubs to Frontend via GET /getClubByCategory/{category}");
        return listOfAllClubs;
    }

    /**
     * Updates the details of a existing club.
     * @param club The club object containing the new details.
     * @return A string response result, upon success returns newly generated request ID and upon failure returns error message.
     */
    @RequestMapping(method = RequestMethod.POST, value="/president/updateClubDetails")
    public String updateClubDetails(@RequestBody Club club) {
        logger.info("Controller Entered: Received request for updating club details.");
        logger.info("updateClubDetails- Calling Service layer updateClubDetails");
        String responseResult = iClubServiceLayer.updateClubDetails(club);
        logger.info("Exiting Controller: Returning service layer response result to Frontend via POST /updateClubDetails");
        return responseResult;
    }


    /** * This method receives the request to approve the club update or new club request
     * @param reqId is the request id of the club update or new club request
     * @return a message
     */
        @RequestMapping(method = RequestMethod.PUT,value="/unauthenticated/approveClubRequest/{reqId}")
     public String approveClubRequest(@PathVariable("reqId") String reqId )
    {
        logger.info("Controller Entered: Received request to approve the new club or update club request.");
        logger.info("approveClubRequest()- Calling approveClubRequest() of ServiceLayer");
        boolean approveRequestStatus=iClubServiceLayer.approveClubRequest(reqId);
        String message;
        if(approveRequestStatus)
        {
            message = " Club request with Request ID: " + reqId + " has been successfully approved";
        }
        else
        {
            message = "Your request with Request ID: " + reqId + " could not be approved";
        }
        logger.info("Exiting Controller: returning club request approval message");
        return  message;
    }
    
    /** * This method receives the request to reject the club update or new club request
     * @param reqId is the request id of the club update or new club request
     * @return a message
     */
    @RequestMapping(method = RequestMethod.PUT,value="/unauthenticated/rejectClubRequest/{reqId}")
    public String rejectClubRequest(@PathVariable("reqId") String reqId )
    {
        logger.info("Controller Entered: Received request to reject the new club or update club request.");
        logger.info("rejectClubRequest()- Calling rejectClubRequest() of ServiceLayer");
        boolean rejectRequestStatus = iClubServiceLayer.rejectClubRequest(reqId);
        String message;
        if (rejectRequestStatus)
        {
            message = "Club request with Request ID: " + reqId + " has been successfully rejected";
        }
        else
        {
            message = "Club request with Request ID: " + reqId + " could not be rejected";
        }
        logger.info("Exiting Controller: returning club request rejection message");
        return message;
    }


    /**
     * Deletes the club details from the Club table. It also deletes all the Event details corresponding to this club to delete.
     * @param clubID The clubID value for the club record to delete
     * @return A boolean response result, which returns true if record deleted successfully, else returns false
     */
    @RequestMapping(method = RequestMethod.POST, value="/admin/deleteClub")
    public boolean deleteClub(String clubID) {
        logger.info("Controller Entered: Received request for deleting the club based on its ID column.");
        logger.info("deleteClub- Calling Service layer deleteClub");
        boolean responseResult = iClubServiceLayer.deleteClub(clubID);
        logger.info("Exiting Controller: Returning service layer response result to Frontend via POST /deleteClub");
        return responseResult;
    }

    /**
     * This method receives the api call to submit a request for joining a club.
     * @param joinClubRequest is the entity to which the details of a join club request are mapped
     * @return a message with the request id if the request is submitted or an error message if
     * request could not be submitted.
     */
    @RequestMapping(method = RequestMethod.POST,value="/unauthenticated/joinClubRequest")
    public String submitJoinClubRequest(@RequestBody JoinClubRequest joinClubRequest)
    {
        logger.info("Controller Entered: Received request for joining a club");
        logger.info("submitJoinClubRequest(): calling submitJoinClubRequest() of ServiceLayer");
        String message=iClubServiceLayer.submitJoinClubRequest(joinClubRequest);
        logger.info("Exiting Controller: returning message if create join club request submitted or not");
        return message;
    }
     /**
     * Get all join club requests using club ID and president email ID
     * @param clubID String
     * @param presidentEmailID String
     * @return List of all join club requests of club managed by president
     */
    @RequestMapping(method = RequestMethod.GET, value = "/president/getAllJoinClubRequests")
    public List<JoinClubRequest> getAllJoinClubRequests(String clubID, String presidentEmailID) {
        logger.info("Controller Entered: Received request for getting all join club requests based on club ID.");
        List<JoinClubRequest> joinClubRequestList = iClubServiceLayer.getAllJoinClubRequests(clubID, presidentEmailID);
        logger.info("Exiting Controller: Returning service layer response result to Frontend via POST /deleteClub");
        return joinClubRequestList;
    }

    /**
     *  This method receives the api call to approve a member's join club request by the president of the club
     * @param reqId is the request id of the join club request of the member
     * @return a message to the frontend
     */
    @RequestMapping(method =RequestMethod.PUT,value="/president/approveJoinClubRequest/{reqId}")
    public String approveJoinClubRequest(@PathVariable("reqId") String reqId)
    {
        String message;
        logger.info(" Entered Controller: received request to approve a join club request");
        logger.info("Controller: Inside approveJoinClubRequest() ");
        logger.info("approveJoinClubRequest(): Calling approveJoinClubRequest() in the service layer");
        boolean approveJoinClubRequestStatus=iClubServiceLayer.approveJoinClubRequest(reqId);
        if(approveJoinClubRequestStatus)
        {
            message="Join Club Request with request id: "+reqId+" was approved";
        }
        else
        {
            message="Join Club Request with request id: "+reqId+"could not be approved";
        }
        logger.info("Controller: Exiting Controller. Returning approval message");
        return message;
    }

    /**
     *  This method receives the api call to reject a member's join club request by the president of the club
     * @param reqId is the request id of the join club request of the member
     * @return a message to the frontend
     */
    @RequestMapping(method =RequestMethod.PUT,value="/unauthenticated/rejectJoinClubRequest/{reqId}")
    public String rejectJoinClubRequest(@PathVariable("reqId") String reqId)
    {
        String message;
        logger.info(" Entered Controller: received request to reject a join club request");
        logger.info("Controller: Inside rejectJoinClubRequest() ");
        logger.info("rejectJoinClubRequest(): Calling rejectJoinClubRequest() in the service layer");
        boolean rejectJoinClubRequestStatus=iClubServiceLayer.rejectJoinClubRequest(reqId);
        if(rejectJoinClubRequestStatus)
        {
            message="Join Club Request with request id: "+reqId+" was rejected successfully";
        }
        else
        {
            message="Join Club Request with request id: "+reqId+"could not be rejected";
        }
        logger.info("Controller: Exiting Controller. Returning rejection message");
        return message;
    }


    /**
     * Controller endpoint for admin to get all new club requests
     * @param requestStatus filter results based on status
     * @return list of new club requests
     */
    @RequestMapping(method = RequestMethod.GET, value = "/admin/getAllNewClubRequests")
    public List<ClubUpdateRequest> getAllNewClubRequests(String requestStatus) {
        logger.info("Controller Entered: Received request for getting all new club requests based on club ID.");
        List<ClubUpdateRequest> ClubUpdateRequest = iClubServiceLayer.getAllNewClubRequests(RequestStatus.valueOf(requestStatus));
        logger.info("Exiting Controller: Returning service layer response result to endpoint");
        return ClubUpdateRequest;
    }


    /**
     * Controller endpoint for admin to get all club update requests
     * @param requestStatus filter results based on status
     * @return list of club update requests
     */
    @RequestMapping(method = RequestMethod.GET, value = "/admin/getAllUpdateClubRequests")
        public List<ClubUpdateRequest> getAllUpdateClubRequests(String requestStatus) {
        logger.info("Controller Entered: Received request for getting all new club requests based on club ID.");
        List<ClubUpdateRequest> ClubUpdateRequest = iClubServiceLayer.getAllUpdateClubRequests(RequestStatus.valueOf(requestStatus));
        logger.info("Exiting Controller: Returning service layer response result to endpoint");
        return ClubUpdateRequest;
    }

}