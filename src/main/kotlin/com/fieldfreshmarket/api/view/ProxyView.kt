package com.fieldfreshmarket.api.view

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User

@JsonSerialize
class ProxyView (
   proxy: Proxy
) {
   val displayName: String = proxy.displayName
   val description: String = proxy.description
   val streetAddress: String = proxy.streetAddress
   val city: String = proxy.city
   val country: String = proxy.country
   val postalCode: String = proxy.postalCode
   val longitude: Double = proxy.longitude
   val latitude: Double = proxy.latitude
   val userId: String = proxy.user.id!!
   val id: String = proxy.id!!
   val orderCount: Int = proxy.orders.size
}