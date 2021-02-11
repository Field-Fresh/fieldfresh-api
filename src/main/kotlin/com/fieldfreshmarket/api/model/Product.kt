package com.fieldfreshmarket.api.model

import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.Table

/*
* Corresponds to the produce quality class
* */
enum class ClassType {
    A,
    B,
    C
}

@Entity
@Table(name="products")
class Product(
    val type: String,
    val category: String,
    val family: String,
    val unit: String,
    @Enumerated(EnumType.STRING)
    val classType: ClassType
) : BaseModel()