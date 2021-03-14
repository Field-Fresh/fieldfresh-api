package com.fieldfreshmarket.api.model.order

import com.fieldfreshmarket.api.model.BaseModel
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.Rating
import java.time.Instant
import javax.persistence.*

@MappedSuperclass
abstract class OrderProduct(
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.PENDING,
    val earliestDate: Instant?,
    val latestDate: Instant?,
    @ManyToOne(optional = false)
    @JoinColumn(name="product_id")
    val product: Product,
    var canCancel: Boolean = true,
) : BaseModel()