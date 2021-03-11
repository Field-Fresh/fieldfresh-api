package com.fieldfreshmarket.api.messaging

import com.fieldfreshmarket.api.core.messaging.Consumer
import com.fieldfreshmarket.api.core.messaging.MessageRouter
import com.fieldfreshmarket.api.messaging.types.mate.MateReadyMessage
import com.fieldfreshmarket.api.messaging.types.mate.matches.MateMatchBatchMessage
import com.fieldfreshmarket.api.services.MateService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class SQSMessageRouter: MessageRouter {

    @Autowired
    private lateinit var mateService: MateService

    @Consumer(name = "mate.ready", type = MateReadyMessage::class)
    fun onMateReady(message: MateReadyMessage) {
        mateService.sendPendingOrders(message)
    }

    @Consumer(name = "mate.match.batch", type = MateMatchBatchMessage::class)
    fun onMateMatchBatch(message: MateMatchBatchMessage) {
        mateService.batchCreateMatches(message)
    }

}