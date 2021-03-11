package com.fieldfreshmarket.api.usecase.orders

import com.fieldfreshmarket.api.data.orders.CreateOrderData
import com.fieldfreshmarket.api.data.orders.CreateProductOrderData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Order
import com.fieldfreshmarket.api.repository.ProxyRepository
import com.fieldfreshmarket.api.repository.products.ProductsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate
import javax.persistence.EntityNotFoundException

@Component
abstract class AbstractCreateOrderUsecase<in I : CreateOrderData<*>, O: Order> {

    @Autowired
    private lateinit var proxyRepository: ProxyRepository

    @Autowired
    private lateinit var productRepository: ProductsRepository

    abstract fun validOrThrow(data: I, proxy: Proxy, products: Map<String, Product>)

    abstract fun buildAndSave(data: I, proxy: Proxy, products: Map<String, Product>): O

    fun execute(data: I): O {
        if (data.orderProducts.isEmpty()) {
            throw IllegalStateException("Can't create an order without products")
        }

        if (!data.isDateValid()) {
            throw IllegalArgumentException(
                "Latest Date should be after the Earliest Date. Earliest Date should be at least 1 day in the future"
            )
        }

        val productIdSet: Set<String> = data.orderProducts.map { it.productId }.toSet()

        if (productIdSet.size != data.orderProducts.size) {
            throw IllegalArgumentException("Cannot place multiple orders for the same product.")
        }

        val products: Map<String, Product> =
            productRepository.findAllByIdIn(productIdSet)
                .map {
                    it.id!! to it
                }.toMap()

        if (products.size != productIdSet.size) {
            throw EntityNotFoundException("Some products dont exist")
        }

        val proxy = proxyRepository.findByIdForUser(data.proxyId, data.grant.user)
            ?: throw EntityNotFoundException("Proxy with id: ${data.proxyId} does not exists.")

        validOrThrow(data, proxy, products)

        return buildAndSave(data, proxy, products)
    }

    private fun <I : CreateProductOrderData> CreateOrderData<I>.isDateValid() = orderProducts.none {
        it.earliestDate.isAfter(it.latestDate) || it.earliestDate.isBefore(LocalDate.now().plusDays(1))
    }
}