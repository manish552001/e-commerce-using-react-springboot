//package com.zosh.config; 
//
//import java.util.Arrays; 
//import java.util.Collections;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.CorsConfigurationSource;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@Component
//@Configuration
//@EnableWebSecurity
//public class AppConfig {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        
//        // http.sessionManagement()
//        //     .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//        //     .and()
//        //     .authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").permitAll()
//        //          .anyRequest().authenticated())
//        //     .addFilterBefore(jwtTokenValidator(), UsernamePasswordAuthenticationFilter.class)
//        //     .csrf().disable()
//        //     .cors().configurationSource(corsConfigurationSource())
//        //     .and()
//        //     .httpBasic().disable() // Disable HTTP Basic authentication
//        //     .and()
//        //     .formLogin();
//        // return http.build();
//
//        http.csrf(csrf -> csrf.disable())
//        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
//        .authorizeHttpRequests(auth -> auth.requestMatchers("/api/**").permitAll()
//                .anyRequest().authenticated())
//        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//        .addFilterBefore(jwtTokenValidator(), UsernamePasswordAuthenticationFilter.class)
//        .httpBasic().disable()
//        .formLogin().disable();
//        return http.build();
//    }
//
//    @Bean
//    public JwtTokenValidator jwtTokenValidator() {
//        return new JwtTokenValidator();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        return new CorsConfigurationSource() {
//            @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                CorsConfiguration cfg = new CorsConfiguration();
//                cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//                cfg.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
//                cfg.setAllowCredentials(true);
//                cfg.setAllowedHeaders(Collections.singletonList("*"));
//                cfg.setExposedHeaders(Arrays.asList("Authorization"));
//                cfg.setMaxAge(3600L);
//                return cfg;
//            }
//        };
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//
//
//



package com.zosh.config;

import java.util.Arrays; 
import java.util.Collections;

import org.springframework.context.annotation.Bean;

import static org.springframework.security.config.Customizer.withDefaults;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration


public class AppConfig {

    @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                                .requestMatchers("/api/**").authenticated()
                                .anyRequest().permitAll()
                )
                .addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class)
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(new CorsConfigurationSource() {

                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                        CorsConfiguration cfg = new CorsConfiguration();
                        cfg.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
                        cfg.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
                        cfg.setAllowCredentials(true);
                        cfg.setAllowedHeaders(Collections.singletonList("*"));
                        cfg.setExposedHeaders(Arrays.asList("Authorization"));
                        cfg.setMaxAge(3600L);
                        return cfg;

                    }
                }))
                .httpBasic(withDefaults())
                .formLogin(withDefaults());
		
		return http.build();
		
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
