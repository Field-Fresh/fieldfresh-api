package com.fieldfreshmarket.api.services.orders

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.orders.GetOrderProductsData
import com.fieldfreshmarket.api.data.orders.GetOrdersData
import com.fieldfreshmarket.api.data.orders.matches.CreateMatchData
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.ProxyRepository
import com.fieldfreshmarket.api.repository.order.BuyProductRepository
import com.fieldfreshmarket.api.repository.order.MatchesRepository
import com.fieldfreshmarket.api.repository.order.OrdersRepository
import com.fieldfreshmarket.api.repository.order.SellProductRepository
import com.fieldfreshmarket.api.usecase.mate.BatchCreateMatchesUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@Component
class OrdersService {

    @Autowired
    private lateinit var orderRepository: OrdersRepository

    @Autowired
    private lateinit var proxyRepository: ProxyRepository

    @Autowired
    private lateinit var sellProductsRepository: SellProductRepository

    @Autowired
    private lateinit var buyProductsRepository: BuyProductRepository

    @Autowired
    private lateinit var batchCreateMatchesUsecase: BatchCreateMatchesUsecase

    @Autowired
    private lateinit var matchRepository: MatchesRepository

    fun getOrders(data: GetOrdersData): List<Order> =
        when (data.side) {
            OrderSide.SELL -> orderRepository.findAllSell(data.grant.user, data.status, data.side)
            OrderSide.BUY -> orderRepository.findAllBuy(data.grant.user, data.status, data.side)
            null -> {
                val allSell = orderRepository.findAllSell(data.grant.user, data.status, data.side)
                val allBuy = orderRepository.findAllBuy(data.grant.user, data.status, data.side)
                allSell + allBuy
            }
        }

    fun getBuyProducts(data: GetOrderProductsData): List<BuyProduct> {
        val proxyId = data.proxyId
        proxyRepository.findByIdForUser(proxyId, data.grant.user)
            ?: throw IllegalArgumentException("No Proxy with id: $data.proxyId")
        return buyProductsRepository.getAllFor(proxyId, data.status)
    }

    fun getSellProducts(data: GetOrderProductsData): List<SellProduct> {
        val proxyId = data.proxyId
        proxyRepository.findByIdForUser(proxyId, data.grant.user)
            ?: throw IllegalArgumentException("No Proxy with id: $data.proxyId")
        return sellProductsRepository.getAllFor(proxyId, data.status)
    }

    fun getMatches(
        grant: AccessGrant,
        proxyId: String,
        side: OrderSide
    ): List<Match> {
        val proxy = proxyRepository.findByIdForUser(proxyId, grant.user)
            ?: throw IllegalArgumentException("No Proxy with id: $proxyId")

        return when (side) {
            OrderSide.BUY -> matchRepository.getBuyMatchesForProxy(proxy)
            OrderSide.SELL -> matchRepository.getSellMatchesForProxy(proxy)
        }
    }

    fun processMatches(batchMatchData: List<CreateMatchData>) {
        batchCreateMatchesUsecase.execute(batchMatchData)
    }

}
