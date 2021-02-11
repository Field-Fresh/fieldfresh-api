package com.fieldfreshmarket.api.usecase.orders.sell

import com.fieldfreshmarket.api.data.orders.sell.CreateSellOrderData
import com.fieldfreshmarket.api.data.orders.sell.CreateSellProductData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.ProductsRepository
import com.fieldfreshmarket.api.repository.ProxyRepository
import com.fieldfreshmarket.api.repository.order.SellOrdersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import javax.persistence.EntityNotFoundException

@Component
class CreateSellOrderUsecase {
    @Autowired
    private lateinit var sellOrderRepository: SellOrdersRepository

    @Autowired
    private lateinit var proxyRepository: ProxyRepository

    @Autowired
    private lateinit var productRepository: ProductsRepository

    fun execute(data: CreateSellOrderData): SellOrder {
        if (data.sellProducts.isEmpty()) {
            throw IllegalStateException("Can't create an order without products")
        }

        if (!data.isDateValid()) {
            throw IllegalArgumentException(
                "Latest Date should be after the Earliest Date. Earliest Date should be at least 1 day in the future"
            )
        }

        val productIdSet: Set<String> = data.sellProducts.map { it.productId }.toSet()

        if (productIdSet.size != data.sellProducts.size) {
            throw IllegalArgumentException("Cannot place multiple orders for the same product.")
        }

        val proxy = proxyRepository.findByIdForUser(data.proxyId, data.grant.user)
            ?: throw EntityNotFoundException("Proxy with id: ${data.proxyId} does not exists.")

        val products: Map<String, Product> =
            productRepository.findAllByIdIn(productIdSet)
                .map {
                    it.id!! to it
                }.toMap()

        if (products.size != productIdSet.size) {
            throw EntityNotFoundException("Some products dont exist")
        }

        return data.toModel(proxy, products).also { sellOrderRepository.save(it) }
    }

    private fun CreateSellOrderData.toModel(
        proxy: Proxy,
        products: Map<String, Product>
    ): SellOrder =
        SellOrder(proxy = proxy).also { sellOrder ->
            sellOrder.sellProducts = sellProducts.map { data ->
                data.toModel(products[data.productId]!!, sellOrder)
            }
        }

    private fun CreateSellProductData.toModel(
        product: Product,
        sellOrder: SellOrder
    ): SellProduct =
        SellProduct(
            earliestDate = earliestDate.convertToInstant(),
            latestDate = latestDate.convertToInstant(),
            minPriceCents = minPriceCents,
            volume = volume,
            sellOrder = sellOrder,
            product = product
        )

    private fun LocalDate.convertToInstant(): Instant = atStartOfDay().toInstant(ZoneOffset.UTC)

    private fun CreateSellOrderData.isDateValid() = sellProducts.none {
        it.earliestDate.isAfter(it.latestDate) || it.earliestDate.isBefore(LocalDate.now().plusDays(1))
    }

}