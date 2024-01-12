package com.dal.cs.backend.member.Controller;

import com.dal.cs.backend.member.MemberObject.Member;
import com.dal.cs.backend.member.ServiceLayer.MemberServiceLayer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MemberController {
    private static final Logger logger = LogManager.getLogger(MemberController.class);

    MemberServiceLayer iMemberServiceLayer;

    @Autowired
    public MemberController(MemberServiceLayer iMemberServiceLayer) {
        this.iMemberServiceLayer = iMemberServiceLayer;
    }

    /**
     * This method accepts the member details  for a new member register request.
     * @param member is the entity to which all the member details submitted by the user are mapped.
     * @return a message to the user with the request id in case request is submitted or an error message
     * if the request is not submitted
     */
    @RequestMapping(method = RequestMethod.POST,value="/api/registerMember")
    public String createMemberRequest(@RequestBody Member member)
    {
        logger.info("New request received for Member creation");
        logger.info("Inside method createMemberRequest() in MemberController");
        String message = iMemberServiceLayer.createNewMemberRequest(member);
        logger.info("Exiting method createMemberRequest() in MemberController");
        return message;
    }
}