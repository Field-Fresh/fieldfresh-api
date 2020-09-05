package com.fieldfreshmarket.api.config

import com.fieldfreshmarket.api.core.cognito.CognitoAuthFilter
import com.fieldfreshmarket.api.core.cognito.CognitoJwtProcessor
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy

@EnableWebSecurity
class AuthConfig(
   private val cognitoJwtProcessor: CognitoJwtProcessor,
   private val properties: FieldFreshProperties
) : WebSecurityConfigurerAdapter() {
   override fun configure(http: HttpSecurity) {
      http
         .csrf().disable()
         .authorizeRequests()
         .antMatchers(*properties.unauthorizedEndpoints.toTypedArray()).permitAll()
         .anyRequest().authenticated()
         .and()
         .addFilter(
            CognitoAuthFilter(
               processor = cognitoJwtProcessor,
               authenticationManager = authenticationManager()
            )
         )
         .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

   }
}