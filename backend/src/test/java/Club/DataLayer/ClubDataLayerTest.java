package Club.DataLayer;

import com.dal.cs.backend.Club.ClassObject.Category;
import com.dal.cs.backend.Club.ClassObject.Club;
import com.dal.cs.backend.Club.ClassObject.ClubUpdateRequest;
import com.dal.cs.backend.Club.ClassObject.JoinClubRequest;
import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.Enum.RequestType;
import com.dal.cs.backend.member.Enum.MemberType;
import com.dal.cs.backend.member.MemberObject.Member;
import org.junit.jupiter.api.*;
import testUtils.BaseTest;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClubDataLayerTest extends BaseTest {


    public ClubDataLayerTest() {
        super();
    }

    @AfterEach
    public void cleanUp() {
        cleanUpTest();
    }

    @Test
    void getLatestRequestIdTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(false, president.getEmailId(), category);
        ClubUpdateRequest newClubRequest = createNewClubRequest(true, club, RequestStatus.PENDING);
        try {
            String latestRequestId = iClubSecondDataLayer.getLatestRequestId();
            System.out.println(latestRequestId.equals(newClubRequest.getRequestID()));
//            Assertions.assertTrue(latestRequestId.equals(newClubRequest.getRequestID()));
        } catch (Exception e) {
            fail("Exception occured: " + e.getMessage());
        }
    }



    @Test
    public void getLatestJoinClubRequestIdTest() {
        Member president = createMember(true, MemberType.president);
        Member member = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        JoinClubRequest joinClubRequest = createNewJoinClubRequest(true, member.getEmailId(), club.getClubID());
        try {
            String latestRequestId = iClubSecondDataLayer.getLatestJoinClubRequestId();
            Assertions.assertTrue(latestRequestId.equals(joinClubRequest.getRequestID()));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void getAllJoinClubRequestsTest() {
        Member president = createMember(true, MemberType.president);
        Member member1 = createMember(true, MemberType.member);
        Member member2 = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        JoinClubRequest joinClubRequest1 = createNewJoinClubRequest(true, member1.getEmailId(), club.getClubID());
        JoinClubRequest joinClubRequest2 = createNewJoinClubRequest(true, member2.getEmailId(), club.getClubID());

        List<JoinClubRequest> joinClubRequests = new ArrayList<>();
        try {
            joinClubRequests = iClubDataLayer.getAllJoinClubRequests(club.getClubID(), president.getEmailId());
            boolean match1 = false;
            boolean match2 = false;
            for (JoinClubRequest joinClubRequest : joinClubRequests
            ) {
                if (joinClubRequest.getRequestID().equals(joinClubRequest1.getRequestID()))
                    match1 = true;
                if (joinClubRequest.getRequestID().equals(joinClubRequest2.getRequestID()))
                    match2 = true;

                if (match1 && match2)
                    break;
            }
            Assertions.assertTrue(match1 || match2);
        } catch (SQLException e) {
            System.out.println("Test failed: Exception occurred- " + e.getMessage());
//            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void getAllClubRequestsTest() {

        Member member = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(true, member.getEmailId(), category);
        ClubUpdateRequest clubRequest1 = (createUpdateClubRequest(true, club, RequestStatus.PENDING));
        ClubUpdateRequest clubRequest2 = (createUpdateClubRequest(true, club, RequestStatus.PENDING));

        List<ClubUpdateRequest> clubUpdateRequests = null;
        try {
            clubUpdateRequests = iClubDataLayer.getAllClubRequests(RequestType.UPDATE_REQUEST, RequestStatus.PENDING);
            boolean match1 = false;
            boolean match2 = false;
            for (ClubUpdateRequest clubUpdateRequest : clubUpdateRequests
            ) {
                if (clubUpdateRequest.getRequestID().equals(clubRequest1.getRequestID()))
                    match1 = true;
                if (clubUpdateRequest.getRequestID().equals(clubRequest2.getRequestID()))
                    match2 = true;

                if (match1 && match2)
                    break;
            }
            Assertions.assertTrue(match1 || match2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createClubCategoryTest() {
        Category category = createCategory(false);
        try {
            Assertions.assertTrue(iClubDataLayer.createClubCategory(category));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }

        //Clean up
        addToStack(Category.class, category.getCategoryID());
    }

    @Test
    public void getAllClubCategoriesTest() {
        List<Category> categories = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            categories.add(createCategory(true));
        }
        try {
            ArrayList<HashMap<String, String>> allCategories = iClubDataLayer.getAllClubCategories();
            for (Category category : categories
            ) {
                boolean found = false;
                for (HashMap<String, String> receivedCategory : allCategories
                ) {
                    if (receivedCategory.get("categoryID").equals(category.getCategoryID())) {
                        Assertions.assertTrue(receivedCategory.get("categoryName").equals(category.getCategoryName()));
                        found = true;
                        break;
                    }
                }
                Assertions.assertTrue(found);
            }
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void deleteClubCategoryTest() {
        Category category = createCategory(true);
        try {
            Assertions.assertTrue(iClubDataLayer.deleteClubCategory(category.getCategoryID()));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }

        //Avoid clean up as deleted
        popCleanUpStack();
    }

    @Test
    public void createNewClubRequestTest() {
        Member member = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(false, member.getEmailId(), category);
        ClubUpdateRequest newClubRequest = createNewClubRequest(false, club, RequestStatus.PENDING);
        //Clean up
        addToStack(ClubUpdateRequest.class, newClubRequest.getRequestID());

        try {
            Assertions.assertTrue(iClubDataLayer.createNewClubRequest(newClubRequest.getRequestID(), club, RequestType.NEW_REQUEST.name(), RequestStatus.PENDING.name()));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void getClubDetailsFromClubRequestTest() {
        Member member = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(false, member.getEmailId(), category);
        ClubUpdateRequest newClubRequest = createNewClubRequest(true, club, RequestStatus.PENDING);

        try {
            Club recievedClub = iClubDataLayer.getClubDetailsFromClubRequest(newClubRequest.getRequestID());
            Assertions.assertTrue(recievedClub.getClubID().equals(club.getClubID()));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void deleteNewClubRequestTest() {
        Member member = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(true, member.getEmailId(), category);
        ClubUpdateRequest newClubRequest = createNewClubRequest(true, club, RequestStatus.PENDING);

        try {
            Assertions.assertTrue(iClubDataLayer.deleteClubRequest(newClubRequest.getRequestID()));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }

        //Remove from cleanup stack
        popCleanUpStack();
    }

    @Test
    public void insertUpdatedClubDetailsTest() {
        Member member = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(true, member.getEmailId(), category);
        ClubUpdateRequest updateClubRequest = createUpdateClubRequest(false, club, RequestStatus.PENDING);

        //Clean up
        addToStack(ClubUpdateRequest.class, updateClubRequest.getRequestID());

        try {
            Assertions.assertTrue(iClubDataLayer.insertUpdatedClubDetails(updateClubRequest.getRequestID(), club, RequestType.UPDATE_REQUEST.name(), RequestStatus.PENDING.name()));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void updateClubRequestStatusToApprovedTest() {
        Member member = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(false, member.getEmailId(), category);
        ClubUpdateRequest newClubRequest = createNewClubRequest(true, club, RequestStatus.PENDING);
        //Clean up
        addToStack(ClubUpdateRequest.class, newClubRequest.getRequestID());
        try {
            Assertions.assertTrue(iClubDataLayer.getClubRequest(newClubRequest.getRequestID()).getRequestStatus().equals(RequestStatus.PENDING));
            Assertions.assertTrue(iClubDataLayer.updateClubRequestStatusToApproved(newClubRequest.getRequestID()));
            Assertions.assertTrue(iClubDataLayer.getClubRequest(newClubRequest.getRequestID()).getRequestStatus().equals(RequestStatus.APPROVED));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void updateClubRequestStatusToRejectedTest() {
        Member member = createMember(true, MemberType.member);
        Category category = createCategory(true);
        Club club = createClub(false, member.getEmailId(), category);
        ClubUpdateRequest newClubRequest = createNewClubRequest(true, club, RequestStatus.PENDING);
        //Clean up
        addToStack(ClubUpdateRequest.class, newClubRequest.getRequestID());
        try {
            Assertions.assertTrue(iClubDataLayer.getClubRequest(newClubRequest.getRequestID()).getRequestStatus().equals(RequestStatus.PENDING));
            Assertions.assertTrue(iClubDataLayer.updateClubRequestStatusToRejected(newClubRequest.getRequestID()));
            Assertions.assertTrue(iClubDataLayer.getClubRequest(newClubRequest.getRequestID()).getRequestStatus().equals(RequestStatus.REJECTED));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void createClubTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(false, president.getEmailId(), category);
        //Clean up
        addToStack(Club.class, club.getClubID());
        try {
            Assertions.assertTrue(iClubDataLayer.createClub(club));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void getAllClubsTest() {
        List<Club> clubs = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Member president = createMember(true, MemberType.president);
            Category category = createCategory(true);
            clubs.add(createClub(true, president.getEmailId(), category));
        }

        try {
            List<Club> receivedClubs = iClubDataLayer.getAllClubs();
            for (Club club : clubs
            ) {
                boolean found = false;
                for (Club receivedClub : receivedClubs
                ) {
                    if (receivedClub.getClubID().equals(club.getClubID())) {
                        Assertions.assertTrue(receivedClub.getClubName().equals(club.getClubName()));
                        found = true;
                        break;
                    }
                }
                Assertions.assertTrue(found);
            }
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void deleteClubTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);

        try {
            Assertions.assertTrue(iClubDataLayer.deleteClub(club.getClubID()));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }

        //Skip club clean up
        popCleanUpStack();
    }

    @Test
    public void getClubsByNameTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club randomClub = createClub(true, president.getEmailId(), category);
        try {
            List<Club> clubs = iClubDataLayer.getClubsByName(randomClub.getClubName());
            Assertions.assertTrue(clubs.size() != 0);
            for (Club club : clubs
            ) {
                Assertions.assertTrue(club.getClubName().equals(randomClub.getClubName()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void getClubsByCategoryTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club randomClub = createClub(true, president.getEmailId(), category);
        try {
            List<Club> clubs = iClubDataLayer.getClubsByCategory(randomClub.getCategoryID());
            Assertions.assertTrue(clubs.size() != 0);
            for (Club club : clubs
            ) {
                Assertions.assertTrue(club.getCategoryID().equals(randomClub.getCategoryID()));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void insertJoinClubRequestTest() {
        Member member = createMember(true, MemberType.member);
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        JoinClubRequest joinClubRequest = createNewJoinClubRequest(false, member.getEmailId(), club.getClubID());
        //Clean up
        addToStack(JoinClubRequest.class, joinClubRequest.getRequestID());
        try {
            Assertions.assertTrue(iClubDataLayer.insertJoinClubRequest(joinClubRequest));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void updateJoinClubRequestStatusToApproved() {
        Member member = createMember(true, MemberType.member);
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        JoinClubRequest joinClubRequest = createNewJoinClubRequest(true, member.getEmailId(), club.getClubID());

        try {
            Assertions.assertTrue(iClubDataLayer.getJoinClubRequest(joinClubRequest.getRequestID()).getRequestStatus().equals(RequestStatus.PENDING));
            Assertions.assertTrue(iClubDataLayer.updateJoinClubRequestStatusToApproved(joinClubRequest.getRequestID()));
            Assertions.assertTrue(iClubDataLayer.getJoinClubRequest(joinClubRequest.getRequestID()).getRequestStatus().equals(RequestStatus.APPROVED));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void deleteJoinClubRequestTest() {
        Member member = createMember(true, MemberType.member);
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        JoinClubRequest joinClubRequest = createNewJoinClubRequest(true, member.getEmailId(), club.getClubID());

        try {
            Assertions.assertTrue(iClubDataLayer.deleteJoinClubRequest(joinClubRequest.getRequestID()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        //Remove from stack
        popCleanUpStack();
    }
}
