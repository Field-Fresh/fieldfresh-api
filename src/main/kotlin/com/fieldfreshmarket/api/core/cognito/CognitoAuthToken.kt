package com.fieldfreshmarket.api.core.cognito

import com.fieldfreshmarket.api.core.cognito.CognitoAccessTokenClaims
import com.nimbusds.jwt.JWTClaimsSet
import com.fieldfreshmarket.api.core.cognito.client.CognitoProperties
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import java.time.Instant
import java.util.*

class CognitoAuthToken(
   val token: String,
   private val properties: CognitoProperties,
   details: JWTClaimsSet,
   authorities: List<GrantedAuthority> = listOf()
) : AbstractAuthenticationToken(authorities) {

   private val cognitoAccessClaim: CognitoAccessTokenClaims

   init {
      setDetails(details)
      cognitoAccessClaim = CognitoAccessTokenClaims(
         tokenUse = details.getStringClaim("token_use"),
         issuer = details.getStringClaim("iss"),
         issued = details.getClaim("iat") as Date,
         expire = details.getClaim("exp") as Date,
         email = details.getStringClaim("username"),
         sub = details.getStringClaim("sub"),
         token = token
      )
      isAuthenticated = verifyClaims(cognitoAccessClaim)
   }

   override fun getCredentials(): Any {
      return token
   }

   override fun getPrincipal(): CognitoAccessTokenClaims {
      return cognitoAccessClaim
   }

   private fun verifyClaims(cognitoAccessTokenClaims: CognitoAccessTokenClaims): Boolean {
      return cognitoAccessTokenClaims.expire.after(Date.from(Instant.now()))
         && cognitoAccessTokenClaims.issuer == "https://cognito-idp.${properties.awsRegion}.amazonaws.com/${properties.cognitoUserPoolId}"
         && cognitoAccessTokenClaims.tokenUse == "access"
   }

}