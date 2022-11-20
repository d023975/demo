package com.example.demo.config;

import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.List;


public class CustomJwtAuthenticationConverter2 implements Converter<Jwt, AbstractAuthenticationToken> {

    private JwtAuthenticationConverter jwtAuthenticationConverter;
    public CustomJwtAuthenticationConverter2( ) {

        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        //SCOPE_sap-papm-cloud!b6733.run_rfc
        //jwtGrantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_sap-papm-cloud!b6733.");
        this.jwtAuthenticationConverter = new JwtAuthenticationConverter();
        this.jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

    }
    @Override
    public AbstractAuthenticationToken convert(Jwt jwt) {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
        Collection<GrantedAuthority> authorities = jwtGrantedAuthoritiesConverter.convert(jwt);

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
        AbstractAuthenticationToken token2 = jwtAuthenticationConverter.convert(jwt);


        //String principalClaimValue = jwt.getClaimAsString(this.principalClaimName);

       for (GrantedAuthority auth : authorities) {
            auth.getAuthority();
        }



        AbstractAuthenticationToken token =  new JwtAuthenticationToken(jwt, authorities);
        //AbstractAuthenticationToken token =  this.jwtAuthenticationConverter.convert(jwt);

        return token;
    }
}



