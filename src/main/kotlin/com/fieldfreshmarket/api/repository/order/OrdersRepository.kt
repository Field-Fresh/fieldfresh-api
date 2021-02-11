package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyOrder
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface OrdersRepository : BaseRepository<Order>{
    @Query(
        """
            select o 
            from Order o
            where (:side = o.side OR :side is null)
            AND :status = o.status 
            AND :user = o.proxy.user
        """
    )
    fun findAllBy(user: User, status: OrderStatus, side: OrderSide?): List<Order>
}