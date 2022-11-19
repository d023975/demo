package com.example.demo.config;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;


public class CustomJwtAuthenticationConverter extends TokenAuthenticationConverter {


    public CustomJwtAuthenticationConverter(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        super(xsuaaServiceConfiguration);
    }
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        try{
        return super.convert(jwt);

    }catch(Exception exception){
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx");
            System.out.println(exception.getMessage());
            System.out.println(exception.getStackTrace());
            return null;
        }

    }

}
