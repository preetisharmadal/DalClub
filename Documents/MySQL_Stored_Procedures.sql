-- Procedure for selecting all the CategoryID and CategoryName from category table
DELIMITER //
CREATE PROCEDURE selectAllFromCategory ()
BEGIN
	SELECT categoryID, categoryName FROM category;
END //
DELIMITER ;

-- Procedure for getting the club ID of the last row in the table
DELIMITER //
CREATE PROCEDURE getLatestClubId()
BEGIN
      SELECT CONCAT('CLB_',(SELECT CAST(SUBSTRING_INDEX(clubID , '_', -1) AS UNSIGNED) AS clubID FROM newAndUpdateClubRequest ORDER BY clubID DESC LIMIT 1)) AS clubID FROM newAndUpdateClubRequest LIMIT 1;
END //
DELIMITER ;

-- Procedure for getting the requeest ID of the last row in the table
DELIMITER //
CREATE PROCEDURE getLatestRequestId()
BEGIN
      SELECT CONCAT('REQ_',(SELECT CAST(SUBSTRING_INDEX(requestID, '_', -1) AS UNSIGNED) AS requestID FROM newAndUpdateClubRequest ORDER BY requestID DESC LIMIT 1)) AS requestID FROM newAndUpdateClubRequest LIMIT 1;
END //
DELIMITER ;

-- Procedure for getting all the clubs from Club table
DELIMITER //
CREATE PROCEDURE getAllClubs()
BEGIN
      SELECT clubID, club.categoryID, clubName, categoryName, description, presidentEmailID, facebookLink, instagramLink, location, meetingTime, clubImage, rules
      FROM club
      INNER JOIN category
      on club.categoryID = category.categoryID;
END //
DELIMITER ;

-- Procedure for searching the club by name
DELIMITER //
CREATE PROCEDURE searchClubByName(IN name VARCHAR(50) )
BEGIN
      SELECT * FROM club WHERE clubName LIKE CONCAT('%', name , '%');
END //
DELIMITER ;

-- Procedure for searching the club by category

DELIMITER //
CREATE PROCEDURE searchClubByCategory(IN category VARCHAR(50) )
BEGIN
      SELECT * FROM club WHERE categoryID LIKE CONCAT('%', category , '%');
END //
DELIMITER ;


-- Procedure for inserting new club create request details into NewAndUpdateClubRequest
DELIMITER //
CREATE PROCEDURE insertIntoNewAndUpdateClubRequest (IN requestID VARCHAR(50),IN clubID VARCHAR(50),IN requestorEmailID VARCHAR(50),IN categoryID VARCHAR(50),IN clubName VARCHAR(50), IN clubDescription VARCHAR(50),IN facebookLink VARCHAR(50),IN instagramLink VARCHAR(50), IN location VARCHAR(50), IN meetingTime VARCHAR(50),IN clubImage VARCHAR(50),IN rules VARCHAR(50),IN requestType VARCHAR(50), IN requestStatus VARCHAR(50))
BEGIN
      INSERT INTO newAndUpdateClubRequest values (requestID, clubID,requestorEmailID,categoryID,clubName,clubDescription,facebookLink,instagramLink,location,meetingTime,clubImage,rules,requestType,requestStatus);
END //
DELIMITER ;

-- Procedure for inserting data of new member request with login credentials
DELIMITER //
CREATE PROCEDURE MemberSaveNewMember (IN emailId VARCHAR(255), IN firstName VARCHAR(255), IN lastName VARCHAR(255), IN userType VARCHAR(255), IN program VARCHAR(255), IN term INT(11), IN mobileNumber VARCHAR(255), IN DOB DATE, IN password VARCHAR(255))
BEGIN
	INSERT INTO member VALUES (emailID, firstName, lastName, userType, program, term, mobileNumber, DOB);
    INSERT INTO login VALUES (emailID, password);
END //
DELIMITER ;

-- Procedure for getting member details with emailID (PK)
DELIMITER //
CREATE PROCEDURE MemberGetMemberDetails (IN emailID VARCHAR(255))
BEGIN
    SELECT firstName,
           lastName,
           userType,
           program,
           term,
           mobileNumber,
           DOB,
           password
    FROM member
             INNER JOIN login ON member.emailID = login.emailId
    WHERE member.emailID = emailID;
END //
DELIMITER ;

-- Procedure to delete a member and their login credential with emailID
DELIMITER //
CREATE PROCEDURE MemberDeleteMember(IN emailID VARCHAR(255))
BEGIN
    DELETE FROM login WHERE login.emailID=emailID;

    DELETE FROM member WHERE member.emailID=emailID;
END //
DELIMITER ;

-- Procedure to create login credential
DELIMITER //
CREATE PROCEDURE LoginCreatePassword(IN emailId VARCHAR(255), IN password VARCHAR(255))
BEGIN
    INSERT INTO login Values (emailID, password);
END //
DELIMITER ;

-- Procedure to get the club request information from newAndUpdateClubRequest table based on the reqid
DELIMITER //
CREATE PROCEDURE getClubRequestInfoByRequestId(IN requestId varchar(50))
BEGIN
  SELECT * FROM newAndUpdateClubRequest as naucr WHERE naucr.requestID=requestID;
END //
DELIMITER ;


-- Procedure to create a club by inserting record in Club table
DELIMITER //
CREATE PROCEDURE createClub(IN clubID VARCHAR(50),IN clubName VARCHAR(255),IN clubDescription VARCHAR(255),IN presidentEmailID VARCHAR(255),IN facebookLink VARCHAR(255),IN instagramLink VARCHAR(255),IN categoryID VARCHAR(50),IN location VARCHAR(255), IN meetingTime VARCHAR(255),IN clubImage VARCHAR(255),IN rules VARCHAR(255))
BEGIN
  INSERT INTO club values(clubID,clubName,clubDescription, presidentEmailID,facebookLink,instagramLink,categoryID,location,meetingTime,clubImage,rules);
END //
DELIMITER ;

-- Procedure to deleting a club record in Club table based on clubID
DELIMITER //
CREATE PROCEDURE deleteClub(IN deleteClubID VARCHAR(50))
BEGIN
  DELETE FROM eventRegistrationDetails WHERE eventID IN (
			SELECT ERD.eventID
            FROM eventRegistrationDetails ERD
            INNER JOIN events EVNT
            ON ERD.eventID = EVNT.eventID
            WHERE EVNT.clubID = deleteClubID
		);
  DELETE FROM events WHERE clubID=deleteClubID;
  DELETE FROM club WHERE clubID=deleteClubID;
END //
DELIMITER ;

-- Procedure to update club request status to approved 
DELIMITER //
CREATE PROCEDURE updateClubRequestStatusToApproved(IN requestId VARCHAR(50))
BEGIN
   UPDATE newAndUpdateClubRequest as naucr SET requestStatus="APPROVED" WHERE naucr.requestID=requestId;
END //
DELIMITER ;


-- Procedure to update club request status to rejected
DELIMITER //
CREATE PROCEDURE updateClubRequestStatusToRejected(IN requestId VARCHAR(50))
BEGIN
   UPDATE newAndUpdateClubRequest as naucr SET requestStatus="REJECTED" WHERE naucr.requestID=requestId;
END //
DELIMITER ;

-- Procedure for retrieving the latest Event Id from events table
DELIMITER //
CREATE PROCEDURE getLatestEventId()
BEGIN
	SELECT CONCAT('EVNT_',(SELECT CAST(SUBSTRING_INDEX(eventID, '_', -1) AS UNSIGNED)+1 AS eventID FROM events ORDER BY eventID DESC LIMIT 1)) AS eventID FROM events LIMIT 1;
END //
DELIMITER ;


-- Procedure to create event by inserting event record into event table
DELIMITER //
CREATE PROCEDURE createEvent(IN eventID VARCHAR(50), IN clubID VARCHAR(50), IN organizerEmailID VARCHAR(255), IN eventName VARCHAR(255), IN description VARCHAR(255), IN venue VARCHAR(255), IN image VARCHAR(255), IN startDate DATE, IN endDate DATE, IN startTime TIME, IN endTime TIME, IN eventTopic VARCHAR(255))
BEGIN
  INSERT INTO events values(eventID,clubID,organizerEmailID,eventName,description,venue,image,startDate,endDate,startTime,endTime,eventTopic);
END //
DELIMITER ;

-- Procedure to get those events in which a user had registered
DELIMITER //
CREATE PROCEDURE getEventsByUserEmailID(IN userEmailID VARCHAR(255))
BEGIN
    SELECT c.clubName,e.eventName,e.eventTopic, e.description, e.venue, e.startDate, e.endDate,
	e.startTime, e.startTime, e.organizerEmailID
    FROM events e
    INNER JOIN eventRegistrationDetails erd ON e.eventID = erd.eventID
    INNER JOIN club c ON c.clubID=e.clubID
    WHERE erd.emailID = userEmailID;
END//
DELIMITER ;


-- Procedure to insert a record when user registers for an event
DELIMITER //
CREATE PROCEDURE registerEvents(IN eventID VARCHAR(50), IN emailID VARCHAR(255))
BEGIN
    INSERT INTO eventRegistrationDetails VALUES (eventID, emailID);
END //
DELIMITER ;

-- Procedure to get event details by event name
DELIMITER //
CREATE PROCEDURE getEventDetails(IN nameOfEvent VARCHAR(255))
BEGIN
    SELECT eventID, clubID, organizerEmailID, eventName, description, venue, image, startDate, endDate, startTime, endTime, eventTopic
    FROM events
    WHERE eventName LIKE CONCAT('%', nameOfEvent, '%');
END //
DELIMITER ;

-- Procedure to update event details which are not null fields
DELIMITER //
CREATE PROCEDURE updateEvent(
    IN updatedEventID VARCHAR(50),
    IN updatedClubID VARCHAR(50),
    IN updatedOrganizerEmailID VARCHAR(255),
    IN updatedEventName VARCHAR(255),
    IN updatedDescription VARCHAR(255),
    IN updatedVenue VARCHAR(255),
    IN updatedImage VARCHAR(255),
    IN updatedStartDate DATE,
    IN updatedEndDate DATE,
    IN updatedStartTime TIME,
    IN updatedEndTime TIME,
    IN updatedEventTopic VARCHAR(255)
)
BEGIN
    UPDATE events
    SET
        organizerEmailID = IFNULL(updatedOrganizerEmailID, organizerEmailID),
        eventName = IFNULL(updatedEventName, eventName),
        description = IFNULL(updatedDescription, description),
        venue = IFNULL(updatedVenue, venue),
        image = IFNULL(updatedImage, image),
        startDate = IFNULL(updatedStartDate, startDate),
        endDate = IFNULL(updatedEndDate, endDate),
        startTime = IFNULL(updatedStartTime, startTime),
        endTime = IFNULL(updatedEndTime, endTime),
        eventTopic = IFNULL(updatedEventTopic, eventTopic)
    WHERE eventID = updatedEventID AND clubID = updatedClubID;
END //
DELIMITER ;

-- Procedure to get all events
DELIMITER //
CREATE PROCEDURE getAllEvents()
BEGIN
    SELECT eventID, clubID, organizerEmailID, eventName, description, venue, image, startDate, endDate, startTime, endTime, eventTopic FROM events;
END //
DELIMITER ;

-- Procedure to delete an event and its registrations based on eventID
DELIMITER //
CREATE PROCEDURE deleteEvent(IN eventID VARCHAR(50))
BEGIN
    DELETE FROM eventRegistrationDetails
    WHERE eventRegistrationDetails.eventID = eventID;

    DELETE FROM events WHERE events.eventID = eventID;
END //
DELIMITER ;

-- Procedure to get join club requests with requestID
DELIMITER //
CREATE PROCEDURE getJoinClubRequest(IN requestID VARCHAR(50))
BEGIN
    SELECT requestID, requestorEmailID, clubID, joiningReason, requestStatus
    FROM joinClubRequest
    WHERE joinClubRequest.requestID = requestID;
END //
DELIMITER ;

-- Procedure to get all join club requests with club ID
DELIMITER //
CREATE PROCEDURE getAllJoinClubRequests(IN clubID VARCHAR(50), IN presidentEmailID VARCHAR(255))
BEGIN
    SELECT requestID, requestorEmailID, clubID, joiningReason, requestStatus
    FROM joinClubRequest
    INNER JOIN club
    ON joinClubRequest.clubID = club.clubID
    WHERE joinClubRequest.clubID = clubID
    AND club.presidentEmailID = presidentEmailID;
END //
DELIMITER ;


-- Procedure to get the latest join club request id
DELIMITER //
CREATE PROCEDURE getLatestJoinClubRequestId()
BEGIN
      SELECT CONCAT('REQ_',(SELECT CAST(SUBSTRING_INDEX(requestID, '_', -1) AS UNSIGNED) AS requestID FROM joinClubRequest ORDER BY requestID DESC LIMIT 1)) AS requestID FROM joinClubRequest LIMIT 1;
END //
DELIMITER ;

-- Procedure to insert joib club request details
DELIMITER //
CREATE PROCEDURE insertJoinClubRequestDetails(IN requestID VARCHAR(50),IN requestorEmailID VARCHAR(255),IN clubID VARCHAR(50),IN joiningReason VARCHAR (255),IN requestStatus VARCHAR(255) )
BEGIN
     INSERT INTO joinClubRequest VALUES (requestID,requestorEmailID,clubID,joiningReason,requestStatus);
END //
DELIMITER ;

-- Procedure to update join club request status to approved
DELIMITER //
CREATE PROCEDURE updateJoinClubRequestStatusToApproved(IN requestId VARCHAR(50))
BEGIN
     UPDATE joinClubRequest as jcr
     SET requestStatus="APPROVED"
     WHERE jcr.requestID=requestId;
END //
DELIMITER ;

-- Procedure to delete join club request on rejection
DELIMITER //
CREATE PROCEDURE deleteJoinClubRequest(IN requestId VARCHAR(50))
BEGIN
   DELETE FROM joinClubRequest
   WHERE joinClubRequest.requestID=requestId;
END //
DELIMITER ;

-- Procedure to retrieve clubs by their club id
DELIMITER //
CREATE PROCEDURE getEventsByClubID(IN club_Id VARCHAR(50))
BEGIN
SELECT eventID, clubID, organizerEmailID, eventName, description, venue, image, startDate, endDate, startTime, endTime, eventTopic FROM events WHERE clubID = club_Id;
END //
DELIMITER ;

-- Procedure to get event by even id
DELIMITER //
CREATE PROCEDURE getEventByEventId(IN eventId VARCHAR(50))
BEGIN
   select * from events where events.eventID=eventId;
END //
DELIMITER ;


-- Procedure to get all new and update club requests
DELIMITER //
CREATE PROCEDURE getAllClubRequests(IN requestType VARCHAR(255), IN requestStatus VARCHAR(255))
BEGIN
    SELECT requestID, newAndUpdateClubRequest.clubID, requestorEmailID, newAndUpdateClubRequest.categoryID, categoryName, newAndUpdateClubRequest.clubName, newAndUpdateClubRequest.description, newAndUpdateClubRequest.facebookLink, newAndUpdateClubRequest.instagramLink, newAndUpdateClubRequest.location, newAndUpdateClubRequest.meetingTime, newAndUpdateClubRequest.clubImage, newAndUpdateClubRequest.rules, newAndUpdateClubRequest.requestType, newAndUpdateClubRequest.requestStatus
    FROM club
    INNER JOIN newAndUpdateClubRequest
        ON club.clubID = newAndUpdateClubRequest.clubID
    INNER JOIN category
        ON newAndUpdateClubRequest.categoryID = category.categoryID
    WHERE newAndUpdateClubRequest.requestType = requestType
    AND newAndUpdateClubRequest.requestStatus = requestStatus;
END //
DELIMITER ;

    -- Procedure to create club category
DELIMITER //
CREATE PROCEDURE createClubCategory(IN categoryID VARCHAR(255), IN categoryName VARCHAR(255))
BEGIN
INSERT INTO category (category.categoryID, category.categoryName) VALUES (categoryID, categoryName);
END //
DELIMITER ;

-- Procedure to delete club category
DELIMITER //
CREATE PROCEDURE deleteClubCategory(IN categoryID VARCHAR(255))
BEGIN
DELETE FROM category WHERE category.categoryID = categoryID;
END //
DELIMITER ;

-- Procedure to delete club request
DELIMITER //
CREATE PROCEDURE deleteClubRequest(IN requestID VARCHAR(255))
BEGIN
    DELETE FROM newAndUpdateClubRequest WHERE newAndUpdateClubRequest.requestID = requestID;
END //
DELIMITER ;