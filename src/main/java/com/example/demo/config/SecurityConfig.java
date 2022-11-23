package com.example.demo.config;

import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration

public class SecurityConfig {

    @Autowired
    XsuaaServiceConfiguration xsuaaServiceConfiguration;

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(customJwtAuthenticationProvider());
        authenticationManagerBuilder.authenticationProvider(customJwtAuthenticationProvider2());
        AuthenticationManager authManger = authenticationManagerBuilder.build();

        return authManger;
    }

    @Bean
    SecurityFilterChain customJwtSecurityChain(HttpSecurity http) throws Exception {
        http    .cors().and().csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //SCOPE_sap-papm-cloud!b6733.run_rfc
                //.antMatchers("/hello/**").hasAuthority("SCOPE_sap-papm-cloud!b6733.run_rfc")
                //.antMatchers("/hello/**").hasAuthority("sap-papm-cloud!b6733.run_rfc")
                .antMatchers("/hello/**").hasAuthority("run_rfc")
                .anyRequest().denyAll()
                .and().oauth2ResourceServer()
                .jwt()
                .authenticationManager(authManager(http));

        return http.build();

    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    private JwtDecoder jwtDecoder(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        return new CustJwtDecoder(xsuaaServiceConfiguration);
    }

    private JwtDecoder jwtDecoder2() {
        return new CustJwtDecoder2();
    }

    Converter<Jwt, AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        TokenAuthenticationConverter converter = new CustomJwtAuthenticationConverter(xsuaaServiceConfiguration);
        converter.setLocalScopeAsAuthorities(true);
        return converter;
    }

    CustomJwtAuthenticationConverter2 getJwtAuthenticationConverter2() {
        CustomJwtAuthenticationConverter2 converter = new CustomJwtAuthenticationConverter2();

        return converter;
    }


    CustomJwtAuthenticationProvider customJwtAuthenticationProvider() {
        return new CustomJwtAuthenticationProvider(jwtDecoder(xsuaaServiceConfiguration), (TokenAuthenticationConverter) getJwtAuthenticationConverter());
    }

    CustomJwtAuthenticationProvider2 customJwtAuthenticationProvider2() {
        return new CustomJwtAuthenticationProvider2(jwtDecoder2(), getJwtAuthenticationConverter2());
    }


}
