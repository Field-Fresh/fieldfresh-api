package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.orders.GetOrdersRequest
import com.fieldfreshmarket.api.controller.request.orders.buy.CreateBuyOrderRequest
import com.fieldfreshmarket.api.controller.request.orders.GetOrderProductRequest
import com.fieldfreshmarket.api.controller.request.orders.sell.CreateSellOrderRequest
import com.fieldfreshmarket.api.controller.response.orders.GetOrdersResponse
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.services.orders.OrdersService
import com.fieldfreshmarket.api.usecase.orders.buy.CreateBuyOrderUsecase
import com.fieldfreshmarket.api.usecase.orders.sell.CreateSellOrderUsecase
import com.fieldfreshmarket.api.view.order.buy.BuyOrderDetailView
import com.fieldfreshmarket.api.view.order.buy.BuyProductView
import com.fieldfreshmarket.api.view.order.sell.SellOrderDetailView
import com.fieldfreshmarket.api.view.order.sell.SellProductView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/orders"])
class OrderController : Controller() {

    @Autowired
    private lateinit var ordersService: OrdersService

    @Autowired
    private lateinit var createSellOrderUsecase: CreateSellOrderUsecase

    @Autowired
    private lateinit var createBuyOrderUsecase: CreateBuyOrderUsecase

    @GetMapping("")
    fun get(request: GetOrdersRequest): GetOrdersResponse =
        GetOrdersResponse(ordersService.getOrders(request.toData(grant)))

    @GetMapping("/buy")
    fun getBuyProducts(request: GetOrderProductRequest): List<BuyProductView> =
        ordersService.getBuyProducts(request.toData(grant)).map { BuyProductView(it) }

    @GetMapping("/sell")
    fun getSellProducts(request: GetOrderProductRequest): List<SellProductView> =
        ordersService.getSellProducts(request.toData(grant)).map { SellProductView(it) }

    @PostMapping("/sell")
    fun createSell(@RequestBody request: CreateSellOrderRequest): SellOrderDetailView =
        SellOrderDetailView(createSellOrderUsecase.execute(request.toData(grant)))

    @PostMapping("/buy")
    fun createBuy(@RequestBody request: CreateBuyOrderRequest): BuyOrderDetailView =
        BuyOrderDetailView(createBuyOrderUsecase.execute(request.toData(grant)))

}