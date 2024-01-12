package testUtils;

import com.dal.cs.backend.Club.ClassObject.Category;
import com.dal.cs.backend.Club.ClassObject.Club;
import com.dal.cs.backend.Club.ClassObject.ClubUpdateRequest;
import com.dal.cs.backend.Club.ClassObject.JoinClubRequest;
import com.dal.cs.backend.Club.Enum.RequestStatus;
import com.dal.cs.backend.Club.Enum.RequestType;
import com.dal.cs.backend.Event.EventObject.Event;
import com.dal.cs.backend.member.Enum.MemberType;
import com.dal.cs.backend.member.MemberObject.Member;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Random;

public class RandomGenerator {

    private static String CHARACTERS = "abcdefghijklmnopqrstuvwxyz";
    private static String DIGITS = "1234567890";
    private static final int maxStringLength = 10;
    private static final int maxStringLength2 = 8;
    private static final int lowerBoundRule = 1000;
    private static final int upperBoundRule = 10000;

    private static StringBuilder generateRandomStringFromCharacters(int length, String characterSet) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characterSet.length());
            char randomChar = characterSet.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb;
    }

    public static String generateRandomString(int length) {
        String randomCharacters = CHARACTERS + DIGITS;
        return generateRandomStringFromCharacters(length, randomCharacters).toString();
    }

    public static Integer generateRandomInteger(int upperBound) {
        Random random = new Random();
        return random.nextInt(1, upperBound);
    }

    public static Integer generateRandomInteger(int lowerBound, int upperBound) {
        Random random = new Random();
        return random.nextInt(lowerBound, upperBound);
    }

    public static String generateRandomPhoneNumber() {
        return generateRandomStringFromCharacters(maxStringLength, DIGITS).toString();
    }

    public static String generateRandomEmail() {
        // Generate a random username with 8 characters
        return generateRandomString(maxStringLength2) + "@dal.ca";
    }

    public static String generateRandomProgram(int upperBound) {
        // Generate a random username with 8 characters
        return "Program" + generateRandomInteger(upperBound);
    }

    public static LocalDate generateRandomDate() {
        final int februaryMonth = 2;
        final int marchMonth = 3;
        final int dayOne = 1;
        final int dayTwo = 2;
        int randomYear = generateRandomInteger(LocalDate.now().getYear());
        int randomMonth = generateRandomInteger(LocalDate.now().getMonthValue()== februaryMonth ? marchMonth :LocalDate.now().getMonthValue());
        randomMonth = (randomMonth == februaryMonth ? marchMonth : randomMonth);
        int randomDate = generateRandomInteger(LocalDate.now().getDayOfMonth()==dayOne ? dayTwo :LocalDate.now().getDayOfMonth());
        randomDate = (randomDate==dayOne ? dayTwo : randomDate);
        return LocalDate.of(randomYear, randomMonth, randomDate);
    }

    public static LocalTime generateRandomTime() {
        final int maxHourInDay = 23;
        final int maxMinuteInDay = 59;
        final int maxSecondInDay = 59;
        int randomHour = generateRandomInteger(maxHourInDay);
        int randomMinute = generateRandomInteger(maxMinuteInDay);
        int randomSecond = generateRandomInteger(maxSecondInDay);
        return LocalTime.of(randomHour, randomMinute, randomSecond);
    }

    public static String generateRandomClubID() {
        return "CLB_" + generateRandomInteger(lowerBoundRule, upperBoundRule);
    }

    public static String generateRandomCategoryID() {
        return "CAT_" + generateRandomInteger(lowerBoundRule, upperBoundRule);
    }

    public static Category generateRandomCategory() {
        String categoryID = RandomGenerator.generateRandomCategoryID();
        String categoryName = RandomGenerator.generateRandomString(10);
        return new Category(categoryID, categoryName);
    }

    public static String generateRandomEventID() {
        return "EVNT_" + generateRandomInteger(lowerBoundRule, upperBoundRule);
    }

    private static String generateRandomFacebookLink() {
        return "https://www.facebook.com/" + generateRandomString(maxStringLength);
    }

    private static String generateRandomInstagramLink() {
        return "https://www.instagram.com/" + generateRandomString(maxStringLength);
    }


    public static String generateRandomRequestID() {
        return "REQ_" + generateRandomInteger(lowerBoundRule, upperBoundRule);
    }

    public static Member generateRandomDalClubMember() {
        String email = RandomGenerator.generateRandomEmail();
        String firstName = generateRandomString(maxStringLength);
        String lastName = generateRandomString(maxStringLength);
        String program = generateRandomProgram(maxStringLength);
        int term = generateRandomInteger(maxStringLength);
        String mobile = generateRandomPhoneNumber();
        LocalDate date = generateRandomDate();
        String password = generateRandomString(maxStringLength);
        Member newMember = new Member(email, firstName, lastName, MemberType.member, program, term, mobile, date, password);
        return newMember;
    }

    public static Member generateRandomAdminMember() {
        Member admin = generateRandomDalClubMember();
        admin.setMemberType(MemberType.admin);
        return admin;
    }

    public static Member generateRandomPresidentMember() {
        Member president = generateRandomDalClubMember();
        president.setMemberType(MemberType.president);
        return president;
    }

    public static Club generateRandomClub(String presidentEmailID, Category category) {
        String clubID = generateRandomClubID();
        String clubName = generateRandomString(maxStringLength);
        String description = generateRandomString(maxStringLength);
        String facebookLink = generateRandomFacebookLink();
        String instagramLink = generateRandomInstagramLink();
        String location = generateRandomString(maxStringLength);
        String meetingTime = generateRandomString(maxStringLength);
        String rules = generateRandomString(maxStringLength);
        return new Club(clubID, clubName, description, presidentEmailID, facebookLink, instagramLink, category.getCategoryID(), location, meetingTime, null, rules, category.getCategoryName());
    }

    public static ClubUpdateRequest generateRandomNewClubRequest(Club clubDetails, RequestType requestType, RequestStatus requestStatus) {
        String requestID = generateRandomRequestID();
        return new ClubUpdateRequest(requestID,
                clubDetails.getClubID(),
                clubDetails.getPresidentEmailID(),
                clubDetails.getCategoryID(),
                clubDetails.getCategoryName(),
                clubDetails.getClubName(),
                clubDetails.getDescription(),
                clubDetails.getFacebookLink(),
                clubDetails.getInstagramLink(),
                clubDetails.getLocation(),
                clubDetails.getMeetingTime(),
                clubDetails.getClubImage(),
                clubDetails.getRules(),
                requestType,
                requestStatus);
    }

    public static Event generateRandomNewEventRequest(Event eventDetails) {
        return new Event(eventDetails.getEventID(),
                eventDetails.getClubID(),
                eventDetails.getOrganizerEmailID(),
                generateRandomString(maxStringLength), // Event Name
                generateRandomString(maxStringLength), // Description
                generateRandomString(maxStringLength), // Venue
                eventDetails.getImage(),
                generateRandomDate().toString(), // startDate
                generateRandomDate().toString(), // endDate
                generateRandomTime().toString(), // startTime
                generateRandomTime().toString(), // endTime
                generateRandomString(maxStringLength) // eventTopic
        );
    }

    public static JoinClubRequest generateRandomJoinClubRequest(String requesterEmailID, String clubID) {
        final int joiningReasonStringLength = 50;
        String requestID = generateRandomRequestID();
        String joiningReason = generateRandomString(joiningReasonStringLength);
        return new JoinClubRequest(requestID, requesterEmailID, clubID, joiningReason, RequestStatus.PENDING);
    }

    public static Event generateRandomEvent(String organiserEmailID, String clubID, String eventID) {
        final int maxEventTopicLength = 20;
        String eventName = generateRandomString(maxStringLength);
        String description = generateRandomString(maxStringLength);
        String venue = generateRandomString(maxStringLength);
        LocalDate startDate = generateRandomDate();
        LocalDate endDate = generateRandomDate();
        LocalTime startTime = generateRandomTime();
        LocalTime endTime = generateRandomTime();
        String eventTopic = generateRandomString(maxEventTopicLength);
        return new Event(eventID, clubID, organiserEmailID, eventName, description, venue, null, startDate.toString(), endDate.toString(), startTime.toString(), endTime.toString(), eventTopic);
    }
}


