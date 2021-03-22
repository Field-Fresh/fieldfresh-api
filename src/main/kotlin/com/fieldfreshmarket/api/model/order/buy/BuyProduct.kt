package com.fieldfreshmarket.api.model.order.buy

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
@Table(name = "buy_products")
class BuyProduct(
    val maxPriceCents: Long,
    var volume: Int,
    @ManyToOne
    @JoinColumn(name = "buy_order_id")
    @NotAudited
    val buyOrder: BuyOrder,
    status: OrderStatus = OrderStatus.PENDING,
    earliestDate: Instant?,
    latestDate: Instant?,
    product: Product,
    canCancel: Boolean = true
) : OrderProduct(
    status, earliestDate, latestDate, product, canCancel
) {
    @OneToMany(mappedBy = "buyProduct")
    @NotAudited
    val matches: List<Match> = listOf()
}
