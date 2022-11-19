package com.example.demo.config;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;


public class CustomJwtAuthenticationConverter2 extends TokenAuthenticationConverter {


    public CustomJwtAuthenticationConverter2(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        super(xsuaaServiceConfiguration);
    }
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        return super.convert(jwt);

    }

}



