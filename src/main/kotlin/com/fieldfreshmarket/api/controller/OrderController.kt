package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.orders.GetOrdersRequest
import com.fieldfreshmarket.api.controller.request.orders.sell.CreateSellOrderRequest
import com.fieldfreshmarket.api.controller.response.orders.GetOrdersResponse
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.services.orders.OrdersService
import com.fieldfreshmarket.api.usecase.orders.sell.CreateSellOrderUsecase
import com.fieldfreshmarket.api.view.order.sell.SellOrderDetailView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/orders"])
class OrderController : Controller() {

    @Autowired
    private lateinit var ordersService: OrdersService

    @Autowired
    private lateinit var createSellOrderUsecase: CreateSellOrderUsecase

    @GetMapping("")
    fun get(request: GetOrdersRequest): GetOrdersResponse =
        GetOrdersResponse(ordersService.getOrders(request.toData(grant)))

    @PostMapping("/sell")
    fun createSell(@RequestBody request: CreateSellOrderRequest): SellOrderDetailView =
        SellOrderDetailView(createSellOrderUsecase.execute(request.toData(grant)))

}