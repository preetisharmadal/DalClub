package Event.DataLayer;

import com.dal.cs.backend.Club.ClassObject.Category;
import com.dal.cs.backend.Club.ClassObject.Club;
import com.dal.cs.backend.Event.EventObject.Event;
import com.dal.cs.backend.member.Enum.MemberType;
import com.dal.cs.backend.member.MemberObject.Member;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import testUtils.BaseTest;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventDataLayerTest extends BaseTest {

   public EventDataLayerTest() {
        super();
    }
    @AfterEach
    public void cleanUp() {
        cleanUpTest();
    }

    @Test
    public void getAllEventsTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        Member organiser = createMember(true, MemberType.member);
        Event event1 = createEvent(true, organiser.getEmailId(), club.getClubID());
        Event event2 = createEvent(true, president.getEmailId(), club.getClubID());
        try {
            List<Event> events = iEventDataLayer.getAllEvents();
            boolean match1 = false;
            boolean match2 = false;
            for (Event event : events
            ) {
                if (event.getEventID().equals(event1.getEventID()))
                    match1 = true;
                if (event.getEventID().equals(event2.getEventID()))
                    match2 = true;

                if (match1 && match2)
                    break;
            }
            Assertions.assertTrue(match1 && match2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createEventTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        Member organiser = createMember(true, MemberType.member);
        Event event = createEvent(false, organiser.getEmailId(), club.getClubID());
        //Add to clean up
        addToStack(Event.class, event.getEventID());
        try {
            Assertions.assertTrue(iEventDataLayer.createEvent(event));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

   /* @Test
    public void getLatestEventIdTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        Event event = createEvent(true, president.getEmailId(), club.getClubID());
        String latestEventId = "EVNT_" + (Integer.parseInt(iEventDataLayer.getLatestEventId().split("_")[1]) - 1);
        Assertions.assertEquals(latestEventId, event.getEventID());
    }*/

    @Test
    public void getEventsByUser() {
        try {
            List<Event> listOfAllEvents = iEventDataLayer.getEventsByUser("swit@dal.ca");
            System.out.println("List of Events: \n" + listOfAllEvents);
            int i;
            for (i = 0; i < listOfAllEvents.size(); i++) {
                Event event = listOfAllEvents.get(i);
                System.out.println(event.getOrganizerEmailID());
                System.out.println(event.getEventName());
                System.out.println(event.getDescription());
                System.out.println(event.getVenue());
                System.out.println(event.getImage());
                System.out.println(event.getStartDate());
                System.out.println(event.getEndDate());
                System.out.println(event.getStartTime());
                System.out.println(event.getEndTime());
                System.out.println(event.getEventTopic());

            }
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void getEventDetails() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        Member organiser = createMember(true, MemberType.member);
        Event event = createEvent(true, organiser.getEmailId(), club.getClubID());

        try {
            Event recievedEvent = iEventDataLayer.getEventDetails(event.getEventName()).get(0);
            Assertions.assertEquals(recievedEvent.getEventID(), event.getEventID());
            Assertions.assertEquals(recievedEvent.getEventName(), event.getEventName());
            Assertions.assertEquals(recievedEvent.getEventTopic(), event.getEventTopic());
            Assertions.assertEquals(recievedEvent.getDescription(), event.getDescription());
            Assertions.assertEquals(recievedEvent.getStartDate(), event.getStartDate());
            Assertions.assertEquals(recievedEvent.getEndDate(), event.getEndDate());
            Assertions.assertEquals(recievedEvent.getStartTime(), event.getStartTime());
            Assertions.assertEquals(recievedEvent.getEndTime(), event.getEndTime());
            Assertions.assertEquals(recievedEvent.getVenue(), event.getVenue());
            Assertions.assertEquals(recievedEvent.getOrganizerEmailID(), event.getOrganizerEmailID());
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void updateEventDetailsTest () {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        Event event = createEvent(true, president.getEmailId(), club.getClubID());
        Event eventRequest = createEventRequest( event);
        try {
            Assertions.assertTrue(iEventDataLayer.updateEventDetails(eventRequest));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }

    @Test
    public void deleteEventTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        Event event = createEvent(true, president.getEmailId(), club.getClubID());
        try {
            Assertions.assertTrue(iEventDataLayer.deleteEvent(event.getEventID()));
        } catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }

        //Remove from clean up stack
        popCleanUpStack();
    }

    @Test
    public void getEventsByClubTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        Event event1 = createEvent(true, president.getEmailId(), club.getClubID());
        Event event2 = createEvent(true, president.getEmailId(), club.getClubID());
        try {
            List<Event> eventDetails = iEventDataLayer.getEventsByClub(club.getClubID());
            boolean match1 = eventDetails.get(0).getEventID().equals(event1.getEventID()) && eventDetails.get(1).getEventID().equals(event2.getEventID());
            boolean match2 = eventDetails.get(1).getEventID().equals(event1.getEventID()) && eventDetails.get(0).getEventID().equals(event2.getEventID());

            Assertions.assertTrue(match1 || match2);
        }
        catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }
    @Test
    public void getEventByEventIdTest() {
        Member president = createMember(true, MemberType.president);
        Category category = createCategory(true);
        Club club = createClub(true, president.getEmailId(), category);
        Event event = createEvent(true, president.getEmailId(), club.getClubID());
        try {
            Event eventDetails = iEventDataLayer.getEventByEventId(event.getEventID());
            boolean match = eventDetails.getEventName().equals(event.getEventName());
            Assertions.assertTrue( match );
        }
        catch (SQLException e) {
            fail("Test failed: Exception occurred- " + e.getMessage());
        }
    }
}
