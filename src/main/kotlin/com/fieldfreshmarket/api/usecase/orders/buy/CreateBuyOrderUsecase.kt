package com.fieldfreshmarket.api.usecase.orders.buy

import com.fieldfreshmarket.api.data.orders.buy.CreateBuyOrderData
import com.fieldfreshmarket.api.data.orders.buy.CreateBuyProductData
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.buy.BuyOrder
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.repository.ProxyRepository
import com.fieldfreshmarket.api.repository.order.BuyOrdersRepository
import com.fieldfreshmarket.api.repository.products.ProductsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import javax.persistence.EntityNotFoundException

@Component
class CreateBuyOrderUsecase {
    @Autowired
    private lateinit var buyOrderRepository: BuyOrdersRepository

    @Autowired
    private lateinit var proxyRepository: ProxyRepository

    @Autowired
    private lateinit var productRepository: ProductsRepository

    // TODO need to figure out a cleaner way to reduce this duplication
    fun execute(data: CreateBuyOrderData): BuyOrder {
        if (data.buyProducts.isEmpty()) {
            throw IllegalStateException("Can't create an order without products")
        }

        if (!data.isDateValid()) {
            throw IllegalArgumentException(
                "Latest Date should be after the Earliest Date. Earliest Date should be at least 1 day in the future"
            )
        }

        val productIdSet: Set<String> = data.buyProducts.map { it.productId }.toSet()

        if (productIdSet.size != data.buyProducts.size) {
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

        return data.toModel(proxy, products).also { buyOrderRepository.save(it) }
    }

    private fun CreateBuyOrderData.toModel(
        proxy: Proxy,
        products: Map<String, Product>
    ): BuyOrder =
        BuyOrder(proxy = proxy).also { order ->
            order.buyProducts = buyProducts.map { data ->
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
            product = product
        )

    private fun LocalDate.convertToInstant(): Instant = atStartOfDay().toInstant(ZoneOffset.UTC)

    private fun CreateBuyOrderData.isDateValid() = buyProducts.none {
        it.earliestDate.isAfter(it.latestDate) || it.earliestDate.isBefore(LocalDate.now().plusDays(1))
    }

}