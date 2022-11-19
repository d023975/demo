package com.example.demo.config;
import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.authentication.XsuaaJwtDecoderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

public class CustJwtDecoder2 implements JwtDecoder
{

    @Autowired
    XsuaaServiceConfiguration xsuaaServiceConfiguration;
    private JwtDecoder defaultDecoder;

    public CustJwtDecoder2( ){

    }

    @Override
    public Jwt decode(String token) throws JwtException {


            // System.out.println(exception.getMessage());
            NimbusJwtDecoder dec = NimbusJwtDecoder.withJwkSetUri("https://papmcloud-prod.authentication.eu20.hana.ondemand.com/token_keys").build();
            Jwt jwt = dec.decode(token);
            return jwt;




    }
}
