package com.fieldfreshmarket.api.services

import com.fieldfreshmarket.api.data.products.PendingProductData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.repository.products.ProductsRepository
import com.fieldfreshmarket.api.usecase.products.GetPendingProductsUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@Component
class ProductService {

    @Autowired
    private lateinit var repository: ProductsRepository

    @Autowired
    private lateinit var getPendingProductsUsecase: GetPendingProductsUsecase

    fun get(): List<Product> = repository.findAll()

    fun getPendingProducts(orderSide: OrderSide): List<PendingProductData> = getPendingProductsUsecase.execute(orderSide)

}
