package com.fieldfreshmarket.api.model.order.buy

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Order
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name="buy_orders")
class BuyOrder(
    proxy: Proxy,
    @OneToMany(mappedBy = "buyOrder", cascade = [CascadeType.REMOVE])
    val buyProducts: List<BuyProduct>
): Order(proxy)