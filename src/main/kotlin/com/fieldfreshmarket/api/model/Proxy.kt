package com.fieldfreshmarket.api.model

import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.buy.BuyOrder
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "proxies")
class Proxy(
    @OneToOne
    var user: User,
    var displayName: String? = null,
    var description: String? = null,
    var streetAddress: String? = null,
    var postalCode: String? = null,
    var city: String? = null,
    var province: String? = null,
    var country: String? = null,
    var longitude: Double? = null,
    var latitude: Double? = null
) : BaseModel() {
    @OneToMany(mappedBy = "proxy")
    val orders: List<Order> = listOf()
}