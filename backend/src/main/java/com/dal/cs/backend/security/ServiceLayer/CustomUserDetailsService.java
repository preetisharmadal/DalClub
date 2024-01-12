package com.dal.cs.backend.security.ServiceLayer;

import com.dal.cs.backend.member.DataLayer.IMemberDataLayer;
import com.dal.cs.backend.member.Enum.MemberType;
import com.dal.cs.backend.member.MemberObject.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private IMemberDataLayer iMemberDataLayer;

    @Autowired
    public CustomUserDetailsService(IMemberDataLayer iMemberDataLayer) {
        this.iMemberDataLayer = iMemberDataLayer;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException {
        Member member = iMemberDataLayer.getMember(emailId);
        return new User(member.getUsername(), member.getPassword(), mapRoleToAuthority(member.getMemberType()));
    }

    private Collection<GrantedAuthority> mapRoleToAuthority(MemberType memberType) {
        List<MemberType> roles = new LinkedList<>();
        roles.add(MemberType.member);
        switch (memberType) {
            case president -> roles.add(MemberType.president);
            case admin -> roles.add(MemberType.admin);
        }
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.name())).collect(Collectors.toList());
    }

    public Collection<GrantedAuthority> getMemberAuthorities(String emailId) {
        Member member = iMemberDataLayer.getMember(emailId);
        return mapRoleToAuthority(member.getMemberType());
    }
}
