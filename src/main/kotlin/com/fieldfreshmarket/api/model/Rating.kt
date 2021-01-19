package com.fieldfreshmarket.api.model

import com.fieldfreshmarket.api.model.order.Order
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name="ratings")
class Rating(
    @ManyToOne
    val ratingUser: User,
    // Rating out of 10. Valid range [1,10]
    val ratingValue: Int,
    // Required due to transaction sizes
    val comments: String,
    @ManyToOne
    val order: Order
): BaseModel() {
}