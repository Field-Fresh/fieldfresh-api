package com.fieldfreshmarket.api.usecase.products

import com.fieldfreshmarket.api.data.products.PendingProductData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.order.OrderSide
import com.fieldfreshmarket.api.repository.ProductsRepository
import com.fieldfreshmarket.api.repository.order.BuyProductRepository
import com.fieldfreshmarket.api.repository.order.SellProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component
@Transactional
class GetPendingProductsUsecase {

    @Autowired
    private lateinit var productRepository: ProductsRepository

    @Autowired
    private lateinit var sellProductsRepository: SellProductRepository

    @Autowired
    private lateinit var buyProductsRepository: BuyProductRepository

    fun execute(orderSide: OrderSide): List<PendingProductData> {
        return when (orderSide) {
            OrderSide.SELL -> sellProductsRepository.findAllPendingProducts()
            OrderSide.BUY -> buyProductsRepository.findAllPendingProducts()
        }.map {
            val product: Product = productRepository.findById(it.id) ?: throw IllegalStateException()
            PendingProductData(product, it.totalVolume, product.unit)
        }
    }
}