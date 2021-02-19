package com.fieldfreshmarket.api.view

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.model.ClassType
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.User

@JsonSerialize
class ProductView (
   product: Product
) {
   val productId: String = product.id!!
   val type: String = product.type
   val units: String = product.unit
   val category: String = product.category
   val family: String = product.family
   val classType: ClassType = product.classType
}