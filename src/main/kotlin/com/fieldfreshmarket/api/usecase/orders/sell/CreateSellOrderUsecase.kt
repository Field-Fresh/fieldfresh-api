package com.fieldfreshmarket.api.usecase.orders.sell

import com.fieldfreshmarket.api.data.orders.sell.CreateSellOrderData
import com.fieldfreshmarket.api.data.orders.sell.CreateSellProductData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.order.SellOrdersRepository
import com.fieldfreshmarket.api.usecase.orders.AbstractCreateOrderUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@Component
class CreateSellOrderUsecase : AbstractCreateOrderUsecase<CreateSellOrderData, SellOrder>() {
    @Autowired
    private lateinit var sellOrderRepository: SellOrdersRepository

    private fun CreateSellOrderData.toModel(
        proxy: Proxy,
        products: Map<String, Product>
    ): SellOrder =
        SellOrder(proxy = proxy).also { sellOrder ->
            sellOrder.sellProducts = orderProducts.map { data ->
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
            product = product,
            serviceRadius = serviceRadius
        )

    private fun LocalDate.convertToInstant(): Instant = atStartOfDay().toInstant(ZoneOffset.UTC)
    override fun validOrThrow(data: CreateSellOrderData, proxy: Proxy, products: Map<String, Product>) {
        val overlappingOrders: List<SellProduct> =
            sellOrderRepository.findOverlappingOrdersForProxy(products.keys, proxy, OrderStatus.PENDING)
        if (overlappingOrders.isNotEmpty()) {
            throw IllegalArgumentException("Cannot place multiple orders for the same product.")
        }
    }

    override fun buildAndSave(data: CreateSellOrderData, proxy: Proxy, products: Map<String, Product>): SellOrder {
        return data.toModel(proxy, products).also { sellOrderRepository.save(it) }
    }

}