package com.fieldfreshmarket.api.model.order

import com.fieldfreshmarket.api.model.BaseModel
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import javax.persistence.*

@Entity
@Table(name = "matches")
class Match(
    val quantity: Double,
    val unitPriceCents: Long,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    val sellProduct: SellProduct,
    @ManyToOne(cascade = [CascadeType.PERSIST])
    val buyProduct: BuyProduct,
    val round: Long
): BaseModel()