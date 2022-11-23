package com.example.demo.config;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.authentication.XsuaaJwtDecoderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.*;

public class CustJwtDecoder implements JwtDecoder {

    @Autowired
    XsuaaServiceConfiguration xsuaaServiceConfiguration;
    private final JwtDecoder defaultDecoder;

    public CustJwtDecoder(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        this.defaultDecoder = new XsuaaJwtDecoderBuilder(xsuaaServiceConfiguration).build();
    }

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
            return this.defaultDecoder.decode(token);
        } catch (Exception e) {
            throw new BadJwtException(e.getMessage());
        }

    }
}
