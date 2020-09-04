package com.fieldfreshmarket.api.view

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.User

@JsonSerialize
class UserView (
   userProfile: User?
) {
   val firstName: String? = userProfile?.firstName
   val lastName: String? = userProfile?.lastName
   val email: String? = userProfile?.email
   val profileId: String = userProfile?.id!!
}