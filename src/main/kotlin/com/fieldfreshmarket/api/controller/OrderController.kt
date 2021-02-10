package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.orders.GetOrdersRequest
import com.fieldfreshmarket.api.controller.response.orders.GetOrdersResponse
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.services.orders.OrdersService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/orders"])
class OrderController : Controller() {

    @Autowired
    private lateinit var ordersService: OrdersService

    @GetMapping("")
    fun create(request: GetOrdersRequest): GetOrdersResponse =
        GetOrdersResponse(ordersService.getOrders(request.toData()))
}