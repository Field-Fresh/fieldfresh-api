package com.fieldfreshmarket.api.model

import javax.persistence.Entity
import javax.persistence.FetchType
import javax.persistence.OneToMany
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
) : BaseModel() {
    @OneToMany(mappedBy = "ratingUser")
    val ratings: List<Rating> = listOf()

    @OneToMany(mappedBy = "user")
    val proxies: List<Proxy> = listOf()

    fun ownsProxy(proxyId: String): Boolean = proxies.map { it.id }.contains(proxyId)
}