package com.fieldfreshmarket.api.model.order.sell

import com.fieldfreshmarket.api.model.BaseModel
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.order.OrderStatus
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name="sell_products")
class SellProduct(
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,
    val earliestDate: Instant?,
    val latestDate: Instant?,
    val serviceRadius: Double,
    /* Per unit price (unit from Product) */
    val minPriceCents: Long,
    val volume: Double,
    // This is used to generate the S3 image URL based on the ID
    @ManyToOne(optional = false)
    @JoinColumn(name = "sell_order_id")
    val sellOrder: SellOrder,
    @ManyToOne(optional = false)
    @JoinColumn(name="product_id")
    val product: Product,
    val pictureUrl: String? = null
): BaseModel()