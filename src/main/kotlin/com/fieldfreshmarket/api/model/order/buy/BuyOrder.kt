package com.fieldfreshmarket.api.model.order.buy

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import javax.persistence.*

@Entity
@Table(name="buy_orders")
class BuyOrder(
    proxy: Proxy,
    @OneToMany(mappedBy = "buyOrder", cascade = [CascadeType.REMOVE])
    val buyProducts: List<BuyProduct>
): Order(proxy, side = OrderSide.BUY)