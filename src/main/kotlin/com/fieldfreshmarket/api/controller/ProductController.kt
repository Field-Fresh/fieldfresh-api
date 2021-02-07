package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.response.product.ProductsGetResponse
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.services.ProductService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import javax.persistence.Enumerated

@RestController
@RequestMapping(path = ["/products"])
class ProductController : Controller() {

    @Autowired
    private lateinit var productService: ProductService

    @GetMapping
    fun get() = ProductsGetResponse(productService.get())

    @GetMapping("/pending")
    fun getPendingProducts(@RequestParam side: OrderSide) = productService.getPendingProducts(side)

}