package com.fieldfreshmarket.api.model.order.buy

import com.fieldfreshmarket.api.model.BaseModel
import com.fieldfreshmarket.api.model.Product
import java.time.Instant
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="buy_products")
class BuyProduct(
    val earliestDate: Instant?,
    val latestDate: Instant?,
    val maxPriceCents: Long,
    val quantity: Long,
    @ManyToOne
    @JoinColumn(name="buy_order_id")
    val buyOrder: BuyOrder,
    @ManyToOne
    @JoinColumn(name="product_id")
    val product: Product
): BaseModel()