package com.fieldfreshmarket.api.model

import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.model.order.buy.BuyOrder
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import javax.persistence.*

@Entity
@Table(name = "proxies")
class Proxy(
    @ManyToOne
    var user: User,
    var displayName: String,
    var description: String?,
    var streetAddress: String,
    var postalCode: String,
    var city: String,
    var province: String,
    var country: String,
    var longitude: Double,
    var latitude: Double
) : BaseModel() {
    @OneToMany(mappedBy = "proxy")
    val orders: List<Order> = listOf()
}