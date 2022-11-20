package com.example.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import com.sap.cloud.security.xsuaa.XsuaaServiceConfiguration;
import com.sap.cloud.security.xsuaa.token.TokenAuthenticationConverter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Collection;

import static org.springframework.security.config.Customizer.withDefaults;

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
/*        authenticationManagerBuilder.inMemoryAuthentication()
                .withUser("memuser")
                .password(passwordEncoder().encode("pass"))
                .roles("USER");*/
        return authenticationManagerBuilder.build();
    }

    /*    @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                    .and()
                    .authorizeRequests()
                    .antMatchers("/hello/**")
                    .hasAuthority("run_rfc").anyRequest().denyAll()
                    //.permitAll()
                    .and()
                    .authenticationManager(authManager);
        }*/
    @Bean
    SecurityFilterChain customJwtSecurityChain(HttpSecurity http)       throws Exception {
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
                .and().oauth2ResourceServer().jwt()
                .authenticationManager(authManager(http));
        return http.build();

    }


    private JwtDecoder jwtDecoder(XsuaaServiceConfiguration xsuaaServiceConfiguration) {
        return new CustJwtDecoder(xsuaaServiceConfiguration);
    }
    private JwtDecoder jwtDecoder2( ) {
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
        return new CustomJwtAuthenticationProvider2(jwtDecoder2() ,  getJwtAuthenticationConverter2());
    }

    // Used by spring security if CORS is enabled.
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source =
                new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }












}
