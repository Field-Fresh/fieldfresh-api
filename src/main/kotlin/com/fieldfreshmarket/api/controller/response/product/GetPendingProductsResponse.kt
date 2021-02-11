package com.fieldfreshmarket.api.controller.response.product

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fieldfreshmarket.api.core.cognito.CognitoJWT
import com.fieldfreshmarket.api.data.auth.SignInResponseData
import com.fieldfreshmarket.api.data.products.PendingProductData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.view.PendingProductView
import com.fieldfreshmarket.api.view.UserView

@JsonSerialize
@JsonInclude(JsonInclude.Include.NON_NULL)
class GetPendingProductsResponse (
   pendingProductsData: List<PendingProductData>
) {
    val pendingProducts: List<PendingProductView> = pendingProductsData.map { PendingProductView(it) }
}