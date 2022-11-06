package com.example.demo.config;
import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.authentication.XsuaaJwtDecoderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

public class CustJwtDecoder implements JwtDecoder
{

    @Autowired
    XsuaaServiceConfiguration xsuaaServiceConfiguration;
    private JwtDecoder defaultDecoder;

    public CustJwtDecoder(XsuaaServiceConfiguration xsuaaServiceConfiguration){
        this.defaultDecoder = new XsuaaJwtDecoderBuilder(xsuaaServiceConfiguration).build();
    }

    @Override
    public Jwt decode(String token) throws JwtException {

        try {
           Jwt jwt = this.defaultDecoder.decode(token);
           return jwt;
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            NimbusJwtDecoder dec = NimbusJwtDecoder.withJwkSetUri("https://papmcloud-prod.authentication.eu20.hana.ondemand.com/token_keys").build();
            Jwt jwt = dec.decode(token);
            return jwt;
        }

    }
}
