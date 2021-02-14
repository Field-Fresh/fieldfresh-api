package com.fieldfreshmarket.api.model.order

import com.fieldfreshmarket.api.model.BaseModel
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.Rating
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name="orders")
@Inheritance(
    strategy = InheritanceType.JOINED
)
abstract class Order(
    @ManyToOne
    open val proxy: Proxy,
    @Enumerated(EnumType.STRING)
    open var status: OrderStatus = OrderStatus.PENDING,
    open var roundUpdatedTimestamp: Instant = Instant.now(),
    open var round: Int = 0,
    @Enumerated(EnumType.STRING)
    open var side: OrderSide,
) : BaseModel() {

    @OneToMany(mappedBy = "order")
    open val ratings: List<Rating> = listOf()
}