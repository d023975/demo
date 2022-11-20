package com.example.demo.config;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;


public class CustomJwtAuthenticationConverter extends TokenAuthenticationConverter {


    public CustomJwtAuthenticationConverter(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        super(xsuaaServiceConfiguration);
    }
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {

        try{
            JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);
            AbstractAuthenticationToken token = super.convert(jwt);


        return token;

    }catch(Exception exception){
            System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx");
            System.out.println(exception.getMessage());
            System.out.println(exception.getStackTrace());
            return null;
        }

    }

}
