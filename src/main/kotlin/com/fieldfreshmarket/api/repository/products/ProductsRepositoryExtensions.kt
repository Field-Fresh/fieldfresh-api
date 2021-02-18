package com.fieldfreshmarket.api.repository.products

import com.fieldfreshmarket.api.model.Product
import org.springframework.transaction.annotation.Transactional

@Transactional(readOnly = true)
interface ProductsRepositoryExtensions {
    fun search(searchText: String, limit: Int): List<Product>
}