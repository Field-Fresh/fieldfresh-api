package com.fieldfreshmarket.api.usecase.products

import com.fieldfreshmarket.api.data.products.SearchProductsData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.repository.products.ProductsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

const val MAX_PRODUCT_SEARCH_RESULTS = 100

@Component
@Transactional(readOnly = true)
class SearchProductsUsecase {

    @Autowired
    private lateinit var productsRepository: ProductsRepository

    fun execute(data: SearchProductsData): List<Product> {
        if (data.limit != null && data.limit < 0) {
            throw IllegalArgumentException("Limit must be greater than 0.")
        }
        return productsRepository.search(
            data.searchText,
            data.limit ?: MAX_PRODUCT_SEARCH_RESULTS
        )
    }
}