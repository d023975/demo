package com.example.demo.config;

import com.nimbusds.jwt.JWT;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class StaticJwtAuthenticationProvider implements AuthenticationProvider {

    private final StaticJwtAuthenticationConverter converter;

    public StaticJwtAuthenticationProvider(StaticJwtAuthenticationConverter converter) {
        this.converter = converter;
    }

    public JwtDecoder jwtDecoder() {
 /*       SecretKeySpec key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
        return NimbusJwtDecoder.withSecretKey(key).build();*/
        return null;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
        try {

            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");


        } catch (JwtException failed) {

        }

        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
//        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
        return authentication.equals(BearerTokenAuthenticationToken.class);
    }

}
