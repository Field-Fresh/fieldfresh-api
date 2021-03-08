package com.fieldfreshmarket.api.model.order.sell

import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.order.OrderProduct
import com.fieldfreshmarket.api.model.order.OrderStatus
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "sell_products")
class SellProduct(
    val serviceRadius: Double,
    /* Per unit price (unit from Product) */
    val volume: Double,
    val minPriceCents: Long,
    // This is used to generate the S3 image URL based on the ID
    @ManyToOne(optional = false)
    @JoinColumn(name = "sell_order_id")
    val sellOrder: SellOrder,
    val pictureUrl: String? = null,
    status: OrderStatus = OrderStatus.PENDING,
    earliestDate: Instant?,
    latestDate: Instant?,
    product: Product,
    canCancel: Boolean? = true
) : OrderProduct(
    status, earliestDate, latestDate, product, canCancel
)