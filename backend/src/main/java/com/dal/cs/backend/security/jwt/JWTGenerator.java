package com.dal.cs.backend.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.dal.cs.backend.security.jwt.SecurityConstants.issuer;

@Component
public class JWTGenerator {
    //private static final KeyPair = Keys.keyPairFor(SignatureAlgorithm.RS256);


    private SecurityConstants securityConstants;

    @Autowired
    public JWTGenerator(SecurityConstants securityConstants) {
        this.securityConstants = securityConstants;
    }

    public String generateToken(String username) {
        return generateToken(username, SecurityConstants.JWT_EXPIRATION);
    }

    public String generateToken(String username, long expiry) {
        Date currentDate = new Date();
        Date expireDate = new Date(currentDate.getTime() + expiry);

        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .setIssuer(issuer)
                .signWith(SignatureAlgorithm.HS512, securityConstants.secret)
                .compact();
        System.out.println("New token :");
        System.out.println(token);
        printStructure(token);
        return token;
    }

    public String getUsernameFromJWT(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(securityConstants.secret)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(securityConstants.secret)
                    .requireIssuer(issuer)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (Exception ex) {
            throw new AuthenticationCredentialsNotFoundException("JWT was expired or incorrect", ex.fillInStackTrace());
        }
    }

    public void printStructure(String token) {
        Jws parseClaimsJws = Jwts.parser().setSigningKey(securityConstants.secret)
                .parseClaimsJws(token);
        System.out.println("Header     : " + parseClaimsJws.getHeader());
        System.out.println("Body       : " + parseClaimsJws.getBody());
        System.out.println("Signature  : " + parseClaimsJws.getSignature());
    }
}
