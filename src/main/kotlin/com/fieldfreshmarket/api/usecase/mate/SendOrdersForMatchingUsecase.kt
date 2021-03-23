package com.fieldfreshmarket.api.usecase.mate

import com.fieldfreshmarket.api.messaging.MatePublisher
import com.fieldfreshmarket.api.messaging.types.mate.MateReadyMessage
import com.fieldfreshmarket.api.messaging.types.toMessage
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.repository.order.BuyProductRepository
import com.fieldfreshmarket.api.repository.order.SellProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class SendOrdersForMatchingUsecase {

    @Autowired
    private lateinit var buyProductRepository: BuyProductRepository

    @Autowired
    private lateinit var sellProductRepository: SellProductRepository

    @Autowired
    private lateinit var matePublisher: MatePublisher

    fun execute(message: MateReadyMessage) {

        val initialRequest = PageRequest.of(0, 25)
        val batchId = message.round.toString()

        // Buy Products
        scroll(
            initial = initialRequest
        ) { pageable ->
            buyProductRepository.getAll(OrderStatus.PENDING, pageable)
                .also { page ->
                    matePublisher.sendBatchMessage(
                        "buyOrder.created",
                        page.content.map { it.toMessage() },
                        page.totalElements,
                        batchId
                    )
                    page.content.forEach { buyProduct ->
                        buyProduct.apply { canCancel = false }.also { buyProductRepository.save(it) }
                    }
                }
        }

        // Sell Products
        scroll(
            initial = initialRequest
        ) { pageable ->
            sellProductRepository.getAll(OrderStatus.PENDING, pageable)
                .also { page ->
                    matePublisher.sendBatchMessage(
                        "sellOrder.created",
                        page.content.map { it.toMessage() },
                        totalCount = page.totalElements,
                        batchId = batchId
                    )
                    page.content.forEach { sellProduct ->
                        sellProduct.apply { canCancel = false }.also { sellProductRepository.save(it) }
                    }
                }
        }

    }

    fun <T> scroll(initial: Pageable, getAndDo: (pageable: Pageable) -> Page<T>) {
        var pageable = initial
        do {
            val page = getAndDo.invoke(pageable)
            pageable = page.nextPageable()
        } while (page.hasNext())
    }

}