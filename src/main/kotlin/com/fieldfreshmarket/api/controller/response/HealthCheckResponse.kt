package com.fieldfreshmarket.api.controller.response

import java.time.LocalDateTime

@Suppress("unused")
class HealthCheckResponse {
    val time: String = LocalDateTime.now().toString()

    val heapSize: String = "${Runtime.getRuntime().totalMemory()/1000000} MB"

    val heapMaxSize: String = "${Runtime.getRuntime().maxMemory()/1000000} MB"

    val heapFreeSize: String = "${Runtime.getRuntime().freeMemory()/1000000} MB"

    val cpu: Int = Runtime.getRuntime().availableProcessors()
}