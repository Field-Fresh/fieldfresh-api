package com.fieldfreshmarket.api.controller.response.product

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.data.auth.SignInResponseData
import com.fieldfreshmarket.api.data.products.PendingProductData
import com.fieldfreshmarket.api.data.products.ProductFilterOptions
import com.fieldfreshmarket.api.model.ClassType
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.view.PendingProductView
import com.fieldfreshmarket.api.view.UserView

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class GetProductFilterOptionsResponse (
   productFilterOptions: ProductFilterOptions
) {
    val categories: List<String>? = productFilterOptions.categories.takeIf { it.isNotEmpty() }
    val families: List<String>? = productFilterOptions.families.takeIf { it.isNotEmpty() }
    val types: List<String>? = productFilterOptions.types.takeIf { it.isNotEmpty() }
    val classTypes: List<ClassType>? = productFilterOptions.classTypes.takeIf { it.isNotEmpty() }
}