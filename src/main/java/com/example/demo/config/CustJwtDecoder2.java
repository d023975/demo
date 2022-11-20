package com.example.demo.config;
import org.springframework.security.oauth2.jwt.*;


public class CustJwtDecoder2 implements JwtDecoder
{


    private JwtDecoder defaultDecoder;

    public CustJwtDecoder2( ){

    }
    public CustJwtDecoder2(String issuerUri ){
        // Calling JwtDecoders#fromIssuerLocation is what invokes the Provider Configuration or Authorization
        // Server Metadata endpoint in order to derive the JWK Set Uri.
        this.defaultDecoder = JwtDecoders.fromIssuerLocation(issuerUri);

    }


    @Override
    public Jwt decode(String token) throws JwtException {


            // https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-decoder-builder
            NimbusJwtDecoder dec = NimbusJwtDecoder
                    //.withPublicKey()
                    //.withSecretKey()
                    .withJwkSetUri("https://papmcloud-prod.authentication.eu20.hana.ondemand.com/token_keys")
                    //.jwsAlgorithm(RS512).jwsAlgorithm(ES512)

                    .build();
               // NimbusJwtDecoder.withPublicKey()
             //https://docs.spring.io/spring-security/reference/servlet/oauth2/resource-server/jwt.html#oauth2resourceserver-jwt-validation-custom
            //dec.setJwtValidator();
            Jwt jwt = dec.decode(token);
            return jwt;




    }
}
