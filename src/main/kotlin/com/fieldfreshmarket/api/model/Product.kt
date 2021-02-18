package com.fieldfreshmarket.api.model

import org.hibernate.search.annotations.Field
import org.hibernate.search.annotations.Indexed
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
@Indexed
@Table(name="products")
class Product(
    @Field
    val type: String,
    @Field
    val category: String,
    @Field
    val family: String,
    val unit: String,
    @Enumerated(EnumType.STRING)
    val classType: ClassType
) : BaseModel()