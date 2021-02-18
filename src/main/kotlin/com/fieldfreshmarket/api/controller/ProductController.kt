package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.products.GetProductFilterOptionsRequest
import com.fieldfreshmarket.api.controller.request.products.GetProductsRequest
import com.fieldfreshmarket.api.controller.response.product.GetPendingProductsResponse
import com.fieldfreshmarket.api.controller.response.product.GetProductFilterOptionsResponse
import com.fieldfreshmarket.api.controller.response.product.ProductsGetResponse
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.data.products.GetProductFilterOptionsData
import com.fieldfreshmarket.api.data.products.ProductFilterOptions
import com.fieldfreshmarket.api.data.products.SearchProductsData
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.services.ProductService
import com.fieldfreshmarket.api.usecase.products.GetProductFilterOptionsUsecase
import com.fieldfreshmarket.api.usecase.products.GetProductsUsecase
import com.fieldfreshmarket.api.usecase.products.SearchProductsUsecase
import com.fieldfreshmarket.api.view.ProductView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/products"])
class ProductController : Controller() {

    @Autowired
    private lateinit var productService: ProductService

    @Autowired
    private lateinit var getProductsUsecase: GetProductsUsecase

    @Autowired
    private lateinit var getProductFilterOptionsUsecase: GetProductFilterOptionsUsecase

    @Autowired
    private lateinit var searchProductsUsecase: SearchProductsUsecase

    @GetMapping
    fun get(request: GetProductsRequest): ProductsGetResponse =
        ProductsGetResponse(getProductsUsecase.execute(request.toData()))


    @GetMapping("/pending")
    fun getPendingProducts(@RequestParam side: OrderSide): GetPendingProductsResponse =
        GetPendingProductsResponse(productService.getPendingProducts(side))

    @GetMapping("/search")
    fun searchProduct(@RequestParam searchText: String, @RequestParam limit: Int?) =
        searchProductsUsecase.execute(SearchProductsData(searchText, limit)).map { ProductView(it) }

    @GetMapping("/filterOptions")
    fun getProductFilterOptions(request: GetProductFilterOptionsRequest): GetProductFilterOptionsResponse =
        GetProductFilterOptionsResponse(getProductFilterOptionsUsecase.execute(request.toData()))
}