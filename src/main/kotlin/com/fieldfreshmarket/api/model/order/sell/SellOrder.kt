package com.fieldfreshmarket.api.model.order.sell

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Order
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name="sell_orders")
class SellOrder(
    proxy: Proxy,
    @OneToMany(mappedBy = "sellOrder", cascade = [CascadeType.REMOVE])
    val sellProducts: List<SellProduct>
): Order(proxy)