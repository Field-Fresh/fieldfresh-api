package com.fieldfreshmarket.api.model.order.buy

import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.order.OrderProduct
import com.fieldfreshmarket.api.model.order.OrderStatus
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "buy_products")
class BuyProduct(
    val maxPriceCents: Long,
    var volume: Double,
    @ManyToOne
    @JoinColumn(name = "buy_order_id")
    val buyOrder: BuyOrder,
    status: OrderStatus = OrderStatus.PENDING,
    earliestDate: Instant?,
    latestDate: Instant?,
    product: Product,
    canCancel: Boolean? = true
) : OrderProduct(
    status, earliestDate, latestDate, product, canCancel
)