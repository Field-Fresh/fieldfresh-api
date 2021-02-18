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
            select sp.sellOrder
            from SellProduct sp
            where (:side = sp.sellOrder.side OR :side is null)
            AND :status = sp.status
            AND :user = sp.sellOrder.proxy.user
        """
    )
    fun findAllSell(user: User, status: OrderStatus, side: OrderSide?): List<Order>

    @Query(
        """
            select bp.buyOrder
            from BuyProduct bp
            where (:side = bp.buyOrder.side OR :side is null)
            AND :status = bp.status
            AND :user = bp.buyOrder.proxy.user
        """
    )
    fun findAllBuy(user: User, status: OrderStatus, side: OrderSide?): List<Order>
}