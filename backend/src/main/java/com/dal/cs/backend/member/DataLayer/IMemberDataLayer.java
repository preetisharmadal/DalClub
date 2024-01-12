package com.dal.cs.backend.member.DataLayer;

import com.dal.cs.backend.member.MemberObject.Member;

public interface IMemberDataLayer {

    boolean createNewMember(Member member);
    Member getMember(String emailId);
    boolean deleteMember(String emailId);
}
