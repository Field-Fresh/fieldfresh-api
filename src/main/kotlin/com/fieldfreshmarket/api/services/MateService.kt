package com.fieldfreshmarket.api.services

import com.fieldfreshmarket.api.data.orders.matches.CreateMatchData
import com.fieldfreshmarket.api.messaging.types.mate.MateReadyMessage
import com.fieldfreshmarket.api.messaging.types.mate.matches.MateMatchBatchMessage
import com.fieldfreshmarket.api.messaging.types.mate.matches.toData
import com.fieldfreshmarket.api.services.orders.OrdersService
import com.fieldfreshmarket.api.usecase.mate.SendOrdersForMatchingUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MateService {

    @Autowired
    private lateinit var sendOrdersForMatchingUsecase: SendOrdersForMatchingUsecase

    @Autowired
    private lateinit var ordersService: OrdersService

    fun sendPendingOrders(mateReadyMessage: MateReadyMessage) {
        sendOrdersForMatchingUsecase.execute(mateReadyMessage)
    }

    fun batchCreateMatches(batchMessage: MateMatchBatchMessage) {
        ordersService.processMatches(
            batchMatchData = batchMessage.matches.map { it.toData() }
        )
    }


}