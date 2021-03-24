package com.fieldfreshmarket.api.model.order.sell

import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.OrderProduct
import com.fieldfreshmarket.api.model.order.OrderStatus
import org.hibernate.envers.Audited
import org.hibernate.envers.NotAudited
import java.time.Instant
import javax.persistence.*

@Entity
@Audited
@Table(name = "sell_products")
class SellProduct(
    val serviceRadius: Double,
    /* Per unit price (unit from Product) */
    var volume: Int,
    var originalVolume: Int,
    val minPriceCents: Long,
    // This is used to generate the S3 image URL based on the ID
    @ManyToOne(optional = false)
    @JoinColumn(name = "sell_order_id")
    @NotAudited
    val sellOrder: SellOrder,
    val pictureUrl: String? = null,
    status: OrderStatus = OrderStatus.PENDING,
    earliestDate: Instant?,
    latestDate: Instant?,
    product: Product,
    canCancel: Boolean = true
) : OrderProduct(
    status, earliestDate, latestDate, product, canCancel
) {
    @OneToMany(mappedBy = "sellProduct")
    @NotAudited
    val matches: List<Match> = listOf()
}