package com.fieldfreshmarket.api.model.order.buy

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name="buy_orders")
@PrimaryKeyJoinColumn(name="id")
class BuyOrder(
    proxy: Proxy
) : Order(proxy, side = OrderSide.BUY) {

    @OneToMany(mappedBy = "buyOrder", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    val buyProducts: List<BuyProduct> = listOf()
}