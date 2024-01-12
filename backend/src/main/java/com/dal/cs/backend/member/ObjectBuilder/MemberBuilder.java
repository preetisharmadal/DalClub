package com.dal.cs.backend.member.ObjectBuilder;

import com.dal.cs.backend.member.Enum.MemberType;
import com.dal.cs.backend.member.MemberObject.Member;

import java.time.LocalDate;

/**
 * This class represents the implementation of the builder pattern for the Member object.
 */
public class MemberBuilder
{
    private String emailId;
    private String firstName;
    private String lastName;
    private MemberType memberType;
    private String program;
    private int term;
    private String mobile;
    private LocalDate dob;
    private String password;

    public MemberBuilder setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public MemberBuilder setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public MemberBuilder setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public MemberBuilder setMemberType(MemberType memberType) {
        this.memberType = memberType;
        return this;
    }

    public MemberBuilder setProgram(String program) {
        this.program = program;
        return this;
    }

    public MemberBuilder setTerm(int term) {
        this.term = term;
        return this;
    }

    public MemberBuilder setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public MemberBuilder setDob(LocalDate dob) {
        this.dob = dob;
        return this;
    }

    public MemberBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public Member createMember()
    {
        return new Member(emailId,firstName,lastName,memberType, program,term,mobile,dob, password);
    }
}
