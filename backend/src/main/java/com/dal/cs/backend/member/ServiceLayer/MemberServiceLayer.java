package com.dal.cs.backend.member.ServiceLayer;

import com.dal.cs.backend.member.DataLayer.IMemberDataLayer;
import com.dal.cs.backend.member.MemberObject.Member;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.dal.cs.backend.security.ApplicationSecurity.passwordEncoder;

@Service
public class MemberServiceLayer {
    private static final Logger logger = LogManager.getLogger(MemberServiceLayer.class);

    IMemberDataLayer iMemberDataLayer;

    @Autowired
    public MemberServiceLayer(IMemberDataLayer iMemberDataLayer) {
        this.iMemberDataLayer = iMemberDataLayer;
    }

    public static MemberServiceLayer getInstance(IMemberDataLayer iMemberDataLayer) {
        return new MemberServiceLayer(iMemberDataLayer);
    }

    /**
     * This method accepts the club details  for a new club request.
     * @param member is the entity to which all the member details submitted by the user are mapped.
     * @return a message to the user with the request id in case request is submitted or an error message
     * if the request is not submitted
     */

    public String createNewMemberRequest(Member member) {
        logger.info("inside createNewMemberRequest");
        String encodedPassword = member.getPassword();
        encodedPassword = passwordEncoder().encode(encodedPassword);
        member.setPassword(encodedPassword);

        boolean createNewMemberRequestStatus = iMemberDataLayer.createNewMember(member);
        if (createNewMemberRequestStatus) {
            String message = "Your request for new member register send Successfully " ;
            logger.info("new member request created successfully");
            return message;
        }
        return null;
    }
}
