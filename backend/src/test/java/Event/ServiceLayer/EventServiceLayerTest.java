package Event.ServiceLayer;

import com.dal.cs.backend.Event.DataLayer.EventDataLayer;
import com.dal.cs.backend.Event.DataLayer.IEventDataLayer;
import com.dal.cs.backend.Event.EventObject.Event;
import com.dal.cs.backend.Event.ServiceLayer.EventServiceLayer;
import com.dal.cs.backend.Event.ServiceLayer.IEventServiceLayer;
import com.dal.cs.backend.database.DatabaseConnection;
import com.dal.cs.backend.database.IDatabaseConnection;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class EventServiceLayerTest {
    /*private IEventServiceLayer iEventServiceLayer;

    @BeforeEach
    public void beforeTestRun() {
        IDatabaseConnection iDatabaseConnection = DatabaseConnection.getInstance();
        IEventDataLayer iEventDataLayer = EventDataLayer.getInstance(iDatabaseConnection);
        iEventServiceLayer = EventServiceLayer.getInstance(iEventDataLayer) ;
    }

    @Test
    public void getAllEventsTest()
    {
        List<Event> listOfAllEvents=iEventServiceLayer.getAllEvents();
        System.out.println(listOfAllEvents);
    }

    @Test
    public void createEventTest() {
//        Event demoEvent = new Event();
//        demoEvent.setEventID("EVNT_00_2");
//        demoEvent.setClubID("CLB_2");
//        demoEvent.setOrganizerEmailID("swit@dal.ca");
//        demoEvent.setEventName("sample event name 2");
//        demoEvent.setDescription("sample event description 2");
//        demoEvent.setVenue("sample event venue 2");
//        demoEvent.setImage("sample_image2.jpg");
//        demoEvent.setStartDate("2023-01-01");
//        demoEvent.setEndDate("2023-01-03");
//        demoEvent.setStartTime("11:00:00");
//        demoEvent.setEndTime("12:00:00");
//        demoEvent.setEventTopic("sample topic");
//        boolean result = iEventServiceLayer.createEvent(demoEvent);
//        System.out.println("result = " + result);
    }

    @Test
    public void getEventsByUserTest(){
        List<Event> listOfAllEvents=iEventServiceLayer.getEventsByUser("swit@dal.ca");
        System.out.println("List of Events by user id: ");
        for (int i = 0; i < listOfAllEvents.size(); i++) {
            System.out.println(listOfAllEvents.get(i).getEventID());
            System.out.println(listOfAllEvents.get(i).getClubID());
            System.out.println(listOfAllEvents.get(i).getOrganizerEmailID());
            System.out.println(listOfAllEvents.get(i).getEventName());
            System.out.println(listOfAllEvents.get(i).getDescription());
            System.out.println(listOfAllEvents.get(i).getVenue());
            System.out.println(listOfAllEvents.get(i).getImage());
            System.out.println(listOfAllEvents.get(i).getStartDate());
            System.out.println(listOfAllEvents.get(i).getEndDate());
            System.out.println(listOfAllEvents.get(i).getStartTime());
            System.out.println(listOfAllEvents.get(i).getEndTime());
            System.out.println(listOfAllEvents.get(i).getEventTopic());
        }
    }
    @Test
    public void getEventDetailsTest()
    {
        List<Event> eventDetails=iEventServiceLayer.getEventDetails("GALA");
        System.out.println(eventDetails);
        System.out.println("Event Details ");
        int i;
        for (i = 0; i < eventDetails.size(); i++) {
            Event event = eventDetails.get(i);
            System.out.println(event.getEventName());
            System.out.println(event.getEventTopic());
            System.out.println(event.getDescription());
            System.out.println(event.getStartDate());
            System.out.println(event.getEndDate());
            System.out.println(event.getStartTime());
            System.out.println(event.getEndTime());
            System.out.println(event.getVenue());
            System.out.println(event.getOrganizerEmailID());
        }
    }

    @Test
    public void updateEventDetails() {
//        Event mockEvent = new Event();
//        mockEvent.setEventID("EVNT_3");
//        mockEvent.setClubID("CLB_3");
//        mockEvent.setEventName("Dalhousie outdoor society Spring AGM");
//        mockEvent.setEventTopic("Outdoor recreational activity");
//        boolean eventStatus = iEventServiceLayer.updateEventDetails(mockEvent);
//        System.out.println("eventStatus = " + eventStatus);
    }

    @Test
    public void getEventsByClubTest(){
        List<Event> listOfAllEvents = iEventServiceLayer.getEventsByClub("CLB_2");
        System.out.println("List of all Events by Club (CLB_2): ");
        for (int i = 0; i < listOfAllEvents.size(); i++) {
            System.out.println("Event ID: "+listOfAllEvents.get(i).getEventID());
            System.out.println("ClubID: "+listOfAllEvents.get(i).getClubID());
            System.out.println("OrganizerEmailID: "+listOfAllEvents.get(i).getOrganizerEmailID());
            System.out.println("EventName: "+listOfAllEvents.get(i).getEventName());
            System.out.println("Description: "+listOfAllEvents.get(i).getDescription());
            System.out.println("Venue: "+listOfAllEvents.get(i).getVenue());
            System.out.println("Image: "+listOfAllEvents.get(i).getImage());
            System.out.println("StartDate: "+listOfAllEvents.get(i).getStartDate());
            System.out.println("EndDate: "+listOfAllEvents.get(i).getEndDate());
            System.out.println("StartTime: "+listOfAllEvents.get(i).getStartTime());
            System.out.println("EndTime: "+listOfAllEvents.get(i).getEndTime());
            System.out.println("EventTopic: "+listOfAllEvents.get(i).getEventTopic());
        }
    }*/
}
