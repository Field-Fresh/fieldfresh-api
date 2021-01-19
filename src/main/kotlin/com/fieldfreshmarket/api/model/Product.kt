package com.fieldfreshmarket.api.model

import javax.persistence.Entity
import javax.persistence.Table

@Entity
@Table(name="products")
class Product(
    val displayName: String,
    val category: String,
    val family: String,
    val classType: String
) : BaseModel()