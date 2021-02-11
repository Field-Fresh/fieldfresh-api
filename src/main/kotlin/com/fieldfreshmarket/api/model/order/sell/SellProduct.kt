package com.fieldfreshmarket.api.model.order.sell

import com.fieldfreshmarket.api.model.BaseModel
import com.fieldfreshmarket.api.model.Product
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="sell_products")
class SellProduct(
    val earliestDate: Instant?,
    val latestDate: Instant?,
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