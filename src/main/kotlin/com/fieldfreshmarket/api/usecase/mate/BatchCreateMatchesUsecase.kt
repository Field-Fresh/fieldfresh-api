package com.fieldfreshmarket.api.usecase.mate

import com.fieldfreshmarket.api.data.orders.matches.CreateMatchData
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.order.BuyProductRepository
import com.fieldfreshmarket.api.repository.order.MatchesRepository
import com.fieldfreshmarket.api.repository.order.SellProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityNotFoundException

@Component
class BatchCreateMatchesUsecase {

    @Autowired
    private lateinit var sellProductRepository: SellProductRepository

    @Autowired
    private lateinit var buyProductRepository: BuyProductRepository

    @Autowired
    private lateinit var matchesRepository: MatchesRepository

    // This will lock the entire table exclusively
    fun execute(data: List<CreateMatchData>): List<Match> {
        return data.map {
            val sellProduct: SellProduct = sellProductRepository.findById(it.sellProductId)
                ?: throw EntityNotFoundException("No SellProduct with id ${it.sellProductId}")

            val buyProduct: BuyProduct = buyProductRepository.findById(it.buyProductId)
                ?: throw EntityNotFoundException("No SellProduct with id ${it.sellProductId}")

            if (buyProduct.product.id != sellProduct.product.id) {
                throw IllegalArgumentException(
                    "Products dont match for SellProduct: ${sellProduct.id} and BuyProduct: ${buyProduct.id}"
                )
            }

            if (sellProduct.volume < it.quantity) {
                throw IllegalArgumentException(
                    "Match (id: ${it.sellProductId} -> ${it.buyProductId}) quantity greater than supply"
                )
            }

            if (buyProduct.volume < it.quantity) {
                throw IllegalArgumentException(
                    "Match (id: ${it.sellProductId} -> ${it.buyProductId}) quantity greater than demanded"
                )
            }

            it.toModel(sellProduct, buyProduct)
        }.also {
            updateStatus(it)
        }.let {
            matchesRepository.saveAll(it)
        }
    }

    private fun updateStatus(matches: List<Match>) =
        matches.map {
            it.apply {
                val remainingSellQuantity = sellProduct.volume - quantity
                val remainingBuyQuantity = buyProduct.volume - quantity

                sellProduct.volume = remainingSellQuantity
                buyProduct.volume = remainingBuyQuantity

                if (remainingSellQuantity == 0.0) {
                    sellProduct.status = OrderStatus.MATCHED
                }

                if (remainingBuyQuantity == 0.0) {
                    buyProduct.status = OrderStatus.MATCHED
                }
            }
        }

    fun CreateMatchData.toModel(sellProduct: SellProduct, buyProduct: BuyProduct): Match =
        Match(
            quantity = quantity,
            unitPriceCents = unitPriceCents,
            sellProduct = sellProduct,
            buyProduct = buyProduct,
            round = round
        )
}
