package com.dal.cs.backend.authentication.controller;

import com.dal.cs.backend.authentication.dataTransferObject.AuthResponseDTO;
import com.dal.cs.backend.authentication.dataTransferObject.LoginDto;
import com.dal.cs.backend.member.DataLayer.IMemberDataLayer;
import com.dal.cs.backend.security.ServiceLayer.CustomUserDetailsService;
import com.dal.cs.backend.security.jwt.JWTGenerator;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/unauthenticated")
public class RefreshController {

    private JWTGenerator jwtGenerator;

    private CustomUserDetailsService userDetailsService;

    @Autowired
    public RefreshController(JWTGenerator jwtGenerator, CustomUserDetailsService userDetailsService) {
        this.jwtGenerator = jwtGenerator;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/refresh")
    public ResponseEntity handleRefresh(@CookieValue(name = "jwt") String refreshToken){
        if (!jwtGenerator.validateToken(refreshToken)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }


        String emailId = jwtGenerator.getUsernameFromJWT(refreshToken);
        String accessToken = jwtGenerator.generateToken(emailId);

        Collection<? extends GrantedAuthority> roleAut = userDetailsService.getMemberAuthorities(emailId);
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority: roleAut
        ) {
            roles.add(authority.getAuthority());
        }

        return new ResponseEntity<>(new AuthResponseDTO(accessToken, roles), HttpStatus.OK);
    }
}
