package com.fieldfreshmarket.api.model.order.sell

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import javax.persistence.*

@Entity
@Table(name="sell_orders")
class SellOrder(
    proxy: Proxy,
): Order(proxy, side = OrderSide.SELL) {

    @OneToMany(mappedBy = "sellOrder", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var sellProducts: List<SellProduct> = listOf()
}