package com.fieldfreshmarket.api.services.orders

import com.fieldfreshmarket.api.data.orders.GetOrderProductsData
import com.fieldfreshmarket.api.data.orders.GetOrdersData
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.order.BuyProductRepository
import com.fieldfreshmarket.api.repository.order.OrdersRepository
import com.fieldfreshmarket.api.repository.order.SellProductRepository
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
    private lateinit var sellProductsRepository: SellProductRepository

    @Autowired
    private lateinit var buyProductsRepository: BuyProductRepository

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
        if (!data.grant.user.ownsProxy(proxyId)) {
            throw IllegalArgumentException("No Proxy with id: $data.proxyId")
        }
        return buyProductsRepository.getAllFor(proxyId, data.status)
    }

    fun getSellProducts(data: GetOrderProductsData): List<SellProduct> {
        val proxyId = data.proxyId
        if (!data.grant.user.ownsProxy(proxyId)) {
            throw IllegalArgumentException("No Proxy with id: $data.proxyId")
        }
        return sellProductsRepository.getAllFor(proxyId, data.status)
    }

}
