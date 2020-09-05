package com.fieldfreshmarket.api.core.cognito

import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class CognitoAuthFilter(
   private val processor: CognitoJwtProcessor,
   authenticationManager: AuthenticationManager
) : BasicAuthenticationFilter(authenticationManager) {

   override fun doFilterInternal(
      req: HttpServletRequest,
      res: HttpServletResponse,
      chain: FilterChain
   ) {
      try {
         val token = extractToken(req.getHeader("Authorization"))
         val authentication = token?.let {
            processor.extractAuthentication(it)
         }
         SecurityContextHolder.getContext().authentication = authentication
         chain.doFilter(req, res)
      } catch (e: AccessDeniedException) {
         logger.error("Access denied: ${e.message ?: "No message"}")
         res.status = 401
         res.writer.write("Access denied")
      }
   }

   /**
    * Extract token from header
    */
   private fun extractToken(header: String?): String? {
      val headers = header?.split("Bearer ")
      return if (headers == null || headers.size < 2) {
         null
      } else {
         headers[1]
      }
   }
}
