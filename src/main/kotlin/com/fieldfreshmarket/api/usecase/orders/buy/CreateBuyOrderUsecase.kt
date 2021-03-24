package com.fieldfreshmarket.api.usecase.orders.buy

import com.fieldfreshmarket.api.data.orders.buy.CreateBuyOrderData
import com.fieldfreshmarket.api.data.orders.buy.CreateBuyProductData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyOrder
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.repository.order.BuyOrdersRepository
import com.fieldfreshmarket.api.usecase.orders.AbstractCreateOrderUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset

@Component
class CreateBuyOrderUsecase : AbstractCreateOrderUsecase<CreateBuyOrderData, BuyOrder>() {
    @Autowired
    private lateinit var buyOrderRepository: BuyOrdersRepository

    private fun CreateBuyOrderData.toModel(
        proxy: Proxy,
        products: Map<String, Product>
    ): BuyOrder =
        BuyOrder(proxy = proxy).also { order ->
            order.buyProducts = orderProducts.map { data ->
                data.toModel(products[data.productId]!!, order)
            }
        }

    private fun CreateBuyProductData.toModel(
        product: Product,
        buyOrder: BuyOrder
    ): BuyProduct =
        BuyProduct(
            earliestDate = earliestDate.convertToInstant(),
            latestDate = latestDate.convertToInstant(),
            maxPriceCents = maxPriceCents,
            volume = volume,
            buyOrder = buyOrder,
            product = product,
            originalVolume = volume
        )

    private fun LocalDate.convertToInstant(): Instant = atStartOfDay().toInstant(ZoneOffset.UTC)

    override fun validOrThrow(data: CreateBuyOrderData, proxy: Proxy, products: Map<String, Product>) {
        val overlappingOrders: List<BuyProduct> =
            buyOrderRepository.findOverlappingOrdersForProxy(products.keys, proxy, OrderStatus.PENDING)
        if (overlappingOrders.isNotEmpty()) {
            throw IllegalArgumentException("Cannot place multiple orders for the same product.")
        }
    }

    override fun buildAndSave(data: CreateBuyOrderData, proxy: Proxy, products: Map<String, Product>): BuyOrder {
        return data.toModel(proxy, products).also { buyOrderRepository.save(it) }
    }

}