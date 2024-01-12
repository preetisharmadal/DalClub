package com.dal.cs.backend.security.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:security.properties")
public class SecurityConstants {
    public static final long JWT_EXPIRATION = 10000;
    public static final String issuer = "Dal Clubs";
    //TODO: Replace static secret to @Value
    @Value("${JWT_SECRET}")
    public  String secret ;
}
