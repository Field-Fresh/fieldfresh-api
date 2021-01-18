package com.fieldfreshmarket.api.core

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.services.auth.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller


@Controller
class Controller {

   @Autowired
   private lateinit var authService: AuthService

   // This pulls the user from the DB everytime it is called
   val grant: AccessGrant by lazy {
      authService.getGrantForAuth()
   }
}