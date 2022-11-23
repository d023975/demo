package com.example.demo.config;


import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.util.Assert;

public class CustomJwtAuthenticationProvider2 implements AuthenticationProvider {

    private final JwtDecoder jwtDecoder;

    private Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter;

    public CustomJwtAuthenticationProvider2(JwtDecoder jwtDecoder, CustomJwtAuthenticationConverter2 converter) {
        Assert.notNull(jwtDecoder, "jwtDecoder cannot be null");
        this.jwtDecoder = jwtDecoder;
        this.jwtAuthenticationConverter = converter;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
                Jwt jwt = getJwt(bearer);
                AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);
                token.setDetails(bearer.getDetails());
                return token;
    }

    private Jwt getJwt(BearerTokenAuthenticationToken bearer) {
        return this.jwtDecoder.decode(bearer.getToken());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public void setJwtAuthenticationConverter(
            Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter) {
        Assert.notNull(jwtAuthenticationConverter, "jwtAuthenticationConverter cannot be null");
        this.jwtAuthenticationConverter = jwtAuthenticationConverter;
    }

}
