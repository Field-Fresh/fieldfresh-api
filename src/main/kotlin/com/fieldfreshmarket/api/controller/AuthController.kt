package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.auth.*
import com.fieldfreshmarket.api.controller.response.auth.SignInResponse
import com.fieldfreshmarket.api.controller.response.auth.VerificationResponse
import com.fieldfreshmarket.api.services.auth.AuthService
import com.fieldfreshmarket.api.view.CognitoJWTView
import com.fieldfreshmarket.api.view.UserView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/auth"])
class AuthController {

   @Autowired
   private lateinit var authService: AuthService

   @PostMapping("/signout")
   fun signout(@Valid @RequestBody signOutRequest: SignOutRequest) =
      authService.signout(signOutRequest.toData())

   @PostMapping("/signin")
   fun signin(@Valid @RequestBody signInRequest: SignInRequest): SignInResponse =
      SignInResponse(authService.signin(signInRequest.toData()))

   @PostMapping("/verify")
   fun verify(
      @RequestBody request: SignUpConfirmationRequest
   ): VerificationResponse = VerificationResponse(authService.verify(request.toData()))

   @PutMapping("/verify/resend")
   fun verify(
      @RequestBody request: ResendVerificationRequest
   ) {
      authService.resendVerification(request.toData())
   }

   /**
    * Post SignUpRequest to create a new user in aws cognito
    */
   @PostMapping("/signup")
   fun signup(@Valid @RequestBody signUpRequest: SignUpRequest): UserView =
      UserView(authService.signup(signUpRequest.toData()))


   @PostMapping("/forgotpassword")
   fun forgotPassword(@Valid @RequestBody forgotPasswordRequest: ForgotPasswordRequest): UserView =
       UserView(authService.forgotPassword(forgotPasswordRequest.toData()))

   @PostMapping("/verifypassword")
   fun confirmForgotPassword(@Valid @RequestBody confirmForgotPasswordRequest: ConfirmForgotPasswordRequest): UserView =
       UserView(authService.confirmForgotPassword(confirmForgotPasswordRequest.toData()))

   /**
    * Get aws tokens with authorization code
    */
   @GetMapping("/token")
   fun token(@RequestParam("code") code: String): CognitoJWTView? =
      CognitoJWTView(authService.getToken(code))
}