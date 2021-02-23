package com.fieldfreshmarket.api.model.order.buy

import com.fieldfreshmarket.api.model.BaseModel
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.order.OrderStatus
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name="buy_products")
class BuyProduct(
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,
    val earliestDate: Instant?,
    val latestDate: Instant?,
    val maxPriceCents: Long,
    val volume: Double,
    @ManyToOne
    @JoinColumn(name="buy_order_id")
    val buyOrder: BuyOrder,
    @ManyToOne
    @JoinColumn(name="product_id")
    val product: Product
): BaseModel()