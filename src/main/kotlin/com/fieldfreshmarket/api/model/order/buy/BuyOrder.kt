package com.fieldfreshmarket.api.model.order.buy

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.model.order.OrderStatus
import javax.persistence.*

@Entity
@Table(name="buy_orders")
@PrimaryKeyJoinColumn(name="id")
class BuyOrder(
    proxy: Proxy
) : Order(proxy, side = OrderSide.BUY) {

    @OneToMany(mappedBy = "buyOrder", cascade = [CascadeType.REMOVE, CascadeType.PERSIST])
    var buyProducts: List<BuyProduct> = listOf()

    override val isActive: Boolean
        get() = !buyProducts.none { it.status == OrderStatus.PENDING }
}