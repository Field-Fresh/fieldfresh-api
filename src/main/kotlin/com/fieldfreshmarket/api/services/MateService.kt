package com.fieldfreshmarket.api.services

import com.fieldfreshmarket.api.messaging.types.mate.MateReadyMessage
import com.fieldfreshmarket.api.usecase.mate.SendOrdersForMatchingUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class MateService {

    @Autowired
    private lateinit var sendOrdersForMatchingUsecase: SendOrdersForMatchingUsecase

    fun sendPendingOrders(mateReadyMessage: MateReadyMessage) {
        sendOrdersForMatchingUsecase.execute(mateReadyMessage)
    }
}