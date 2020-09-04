package com.fieldfreshmarket.api.core.cognito

import com.nimbusds.jose.JWSAlgorithm
import com.nimbusds.jose.jwk.source.JWKSource
import com.nimbusds.jose.jwk.source.RemoteJWKSet
import com.nimbusds.jose.proc.JWSVerificationKeySelector
import com.nimbusds.jose.proc.SecurityContext
import com.nimbusds.jose.util.DefaultResourceRetriever
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor
import com.nimbusds.jwt.proc.DefaultJWTProcessor
import com.fieldfreshmarket.api.core.cognito.client.CognitoProperties
import java.net.URL
import java.util.*

class CognitoJwtProcessor(
   private val properties: CognitoProperties
) {

   private val jwtProcessor: ConfigurableJWTProcessor<SecurityContext>
      get() {
         val resourceRetriever = DefaultResourceRetriever(5000, 5000)
         val jwkSetURL = URL(properties.cognitoUserKeysUrl)
         val keySource: JWKSource<SecurityContext> = RemoteJWKSet(jwkSetURL, resourceRetriever)
         val jwtProcessor: ConfigurableJWTProcessor<SecurityContext> = DefaultJWTProcessor()
         val keySelector = JWSVerificationKeySelector(JWSAlgorithm.RS256, keySource)
         jwtProcessor.jwsKeySelector = keySelector
         return jwtProcessor
      }

   fun extractAuthentication(accessToken: String): CognitoAuthToken {
      return try {
         val claims = jwtProcessor.process(accessToken, null)
         CognitoAuthToken(accessToken, properties, claims)
      } catch (e: Exception) {
         throw IllegalAccessException("${e.javaClass.simpleName} (${e.message ?: "No message"})")
      }
   }

   fun extractIdentityClaims(idToken: String): CognitoIDTokenClaims {
      return try {
         val claims = jwtProcessor.process(idToken, null)
         CognitoIDTokenClaims(
            tokenUse = claims.getStringClaim("token_use"),
            issuer = claims.getStringClaim("iss"),
            issued = claims.getClaim("iat") as Date,
            expire = claims.getClaim("exp") as Date,
            email = claims.getStringClaim("email"),
            sub = claims.getStringClaim("sub"),
            firstName = claims.getStringClaim("given_name"),
            lastName = claims.getStringClaim("family_name")
         )
      } catch (e: Exception) {
         throw IllegalAccessException("${e.javaClass.simpleName} (${e.message ?: "No message"})")
      }
   }
}