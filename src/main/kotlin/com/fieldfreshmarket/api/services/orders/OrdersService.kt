package com.fieldfreshmarket.api.services.orders

import com.fieldfreshmarket.api.data.orders.GetOrdersData
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.repository.order.OrdersRepository
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

}
