package Club.ServiceLayer;

import com.dal.cs.backend.Club.DataLayer.ClubDataLayer;
import com.dal.cs.backend.Club.DataLayer.IClubDataLayer;
import com.dal.cs.backend.Club.DataLayer.IClubSecondDataLayer;
import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.ServiceLayer.ClubServiceLayer;
import com.dal.cs.backend.Club.ServiceLayer.IClubServiceLayer;
import com.dal.cs.backend.Club.ClassObject.Club;
import com.dal.cs.backend.database.DatabaseConnection;
import com.dal.cs.backend.database.IDatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ClubServiceLayerTest
{
    private IClubServiceLayer iclubServiceLayer;

    @BeforeEach
    public void beforeTestRun() {
        IDatabaseConnection iDatabaseConnection = DatabaseConnection.getInstance();
        IClubDataLayer iClubDataLayer =  ClubDataLayer.getInstance(iDatabaseConnection);
        IClubSecondDataLayer iClubSecondDataLayer = ClubDataLayer.getInstance(iDatabaseConnection);
        iclubServiceLayer = ClubServiceLayer.getInstance(iClubDataLayer, iClubSecondDataLayer) ;
    }

    @Test
    public void getAllClubCategoriesTest() {
        try {
            ArrayList<HashMap<String, String>> result = iclubServiceLayer.getAllClubCategories();
            System.out.println("result: \n" + result);
        }
        catch (Exception e) {
            fail("Test failed: Exception occured- "+e.getMessage());
        }
    }
    @Test
    public void getAllClubsTest()
    {
        List<Club> listOfAllClubs=iclubServiceLayer.getAllClubs();
        System.out.println(listOfAllClubs);
    }
    @Test
    public void updateClubDetailsTest() {
//        try {
//            Club club = new Club();
//            club.setClubID("CLB_1");
//            club.setClubName("Dal & Kings Bike Society");
//            club.setDescription("Enthusiastic club organising biking trips.");
//            String result = iclubServiceLayer.updateClubDetails(club);
//            System.out.println("result: " + result);
//        } catch (Exception e) {
//            fail("Test failed: Exception occured- " + e.getMessage());
//        }
    }
    @Test
    public void approveClubRequestWhenReqIdIsNull()
    {
        String reqId=null;
        assertFalse(iclubServiceLayer.approveClubRequest(reqId));
    }
    @Test
    public void approveClubRequestWhenReqIdIsEmpty()
    {
        String reqId="";
        assertFalse(iclubServiceLayer.approveClubRequest(reqId));
    }
    @Test
    public void rejectClubRequestWhenReqIdIsNull()
    {
        String reqId=null;
        assertFalse(iclubServiceLayer.approveClubRequest(reqId));
    }
    @Test
    public void rejectClubRequestWhenReqIdIsEmpty()
    {
        String reqId="";
        assertFalse(iclubServiceLayer.approveClubRequest(reqId));
    }

    @Test
    public void deleteClubTest() {
//        boolean result = iclubServiceLayer.deleteClub("CLB_1");
//        System.out.println("result = " + result);
    }

    @Test
    public void getAllJoinClubRequestsTest() {
//        assertNotEquals(iclubServiceLayer.getAllJoinClubRequests("CLB_2", "user@dal.ca").size(), 0);
    }

    @Test
    public void approveJoinClubRequestWhenReqIdIsNullTest()
    {
        String reqId=null;
        assertFalse(iclubServiceLayer.approveClubRequest(reqId));
    }
    @Test
    public void approveJoinClubRequestWhenReqIdIsEmptyTest()
    {
        String reqId="";
        assertFalse(iclubServiceLayer.approveClubRequest(reqId));
    }
    @Test
    public void rejectJoinClubRequestWhenReqIdIsNullTest()
    {
        String reqId=null;
        assertFalse(iclubServiceLayer.rejectClubRequest(reqId));
    }
    @Test
    public void rejectJoinClubRequestWhenReqIdIsEmptyTest()
    {
        String reqId="";
        assertFalse(iclubServiceLayer.rejectClubRequest(reqId));
    }

    @Test
    public void getAllNewClubRequestsTests() {
//        assertTrue(iclubServiceLayer.getAllNewClubRequests(RequestStatus.APPROVED).size()>0);
    }
    @Test
    public void getAllClubUpdateRequestsTests() {
//        assertTrue(iclubServiceLayer.getAllUpdateClubRequests(RequestStatus.APPROVED).size()>0);
    }

}
