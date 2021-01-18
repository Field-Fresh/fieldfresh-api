package com.fieldfreshmarket.api.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name = "users")
class User(
   var email: String,
   var cognitoSub: String?,
   var verified: Boolean = false,
   var firstName: String? = null,
   var lastName: String? = null,
   var phone: String? = null
) : BaseModel()