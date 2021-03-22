package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.orders.GetOrderProductRequest
import com.fieldfreshmarket.api.controller.request.orders.GetOrdersRequest
import com.fieldfreshmarket.api.controller.request.orders.buy.CreateBuyOrderRequest
import com.fieldfreshmarket.api.controller.request.orders.sell.CreateSellOrderRequest
import com.fieldfreshmarket.api.controller.response.orders.GetBuyOrderDetails
import com.fieldfreshmarket.api.controller.response.orders.GetMatchDetails
import com.fieldfreshmarket.api.controller.response.orders.GetOrdersResponse
import com.fieldfreshmarket.api.controller.response.orders.GetSellOrderDetails
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.data.orders.matches.GetMatchDetailsData
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.services.orders.OrdersService
import com.fieldfreshmarket.api.usecase.orders.buy.CreateBuyOrderUsecase
import com.fieldfreshmarket.api.usecase.orders.match.GetMatchDetailsUsecase
import com.fieldfreshmarket.api.usecase.orders.sell.CreateSellOrderUsecase
import com.fieldfreshmarket.api.view.order.MatchView
import com.fieldfreshmarket.api.view.order.buy.BuyOrderDetailView
import com.fieldfreshmarket.api.view.order.buy.BuyProductView
import com.fieldfreshmarket.api.view.order.sell.SellOrderDetailView
import com.fieldfreshmarket.api.view.order.sell.SellProductView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping(path = ["/orders"])
class OrderController : Controller() {

    @Autowired
    private lateinit var ordersService: OrdersService

    @Autowired
    private lateinit var createSellOrderUsecase: CreateSellOrderUsecase

    @Autowired
    private lateinit var createBuyOrderUsecase: CreateBuyOrderUsecase

    @Autowired
    private lateinit var getMatchDetailsUsecase: GetMatchDetailsUsecase

    @GetMapping("")
    fun get(request: GetOrdersRequest): GetOrdersResponse =
        GetOrdersResponse(ordersService.getOrders(request.toData(grant)))

    @GetMapping("sell/{id}")
    fun getSell(@PathVariable(name = "id") id: String): GetSellOrderDetails =
        GetSellOrderDetails(ordersService.getSellProduct(id, grant))

    @GetMapping("buy/{id}")
    fun getBuy(@PathVariable(name = "id") id: String): GetBuyOrderDetails =
        GetBuyOrderDetails(ordersService.getBuyProduct(id, grant))

    @GetMapping("/buy")
    fun getBuyProducts(request: GetOrderProductRequest): List<BuyProductView> =
        ordersService.getBuyProducts(request.toData(grant)).map { BuyProductView(it) }

    @GetMapping("/sell")
    fun getSellProducts(request: GetOrderProductRequest): List<SellProductView> =
        ordersService.getSellProducts(request.toData(grant)).map { SellProductView(it) }

    @PostMapping("/sell")
    fun createSell(@Valid @RequestBody request: CreateSellOrderRequest): SellOrderDetailView =
        SellOrderDetailView(createSellOrderUsecase.execute(request.toData(grant)))

    @PostMapping("/buy")
    fun createBuy(@Valid @RequestBody request: CreateBuyOrderRequest): BuyOrderDetailView =
        BuyOrderDetailView(createBuyOrderUsecase.execute(request.toData(grant)))

    //TODO might be able to merge this call to avoid a double call
    @GetMapping("/{proxy_id}/matches")
    fun getMatches(@PathVariable(name = "proxy_id") proxyId: String, side: OrderSide): List<MatchView> =
        ordersService.getMatches(grant, proxyId, side).map { MatchView(it) }

    @GetMapping("/{proxy_id}/match/{id}")
    fun getMatchDetails(
        @PathVariable(name = "proxy_id") proxyId: String,
        @PathVariable(name = "id") id: String
    ): GetMatchDetails =
        GetMatchDetails(getMatchDetailsUsecase.execute(
            GetMatchDetailsData(
                matchId = id,
                proxyId = proxyId,
                grant = grant
            )
        ))

    @PutMapping("/sell/{id}/cancel")
    fun cancelSell(@PathVariable(name = "id") id: String) {
        ordersService.cancelSellOrder(id, grant)
    }

    @PutMapping("/buy/{id}/cancel")
    fun cancelBuy(@PathVariable(name = "id") id: String) {
        ordersService.cancelBuyOrder(id, grant)
    }
}