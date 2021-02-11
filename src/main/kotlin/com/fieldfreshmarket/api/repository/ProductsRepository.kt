package com.fieldfreshmarket.api.repository

import com.fieldfreshmarket.api.model.Product

interface ProductsRepository : BaseRepository<Product> {
    fun findById(id: String): Product?

    fun findAllByIdIn(ids: Set<String>): List<Product>
}