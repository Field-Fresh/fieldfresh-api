package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.response.HealthCheckResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/sysinfo"])
class HealthCheckController {

    @GetMapping
    fun sysInfo(): HealthCheckResponse {
        return HealthCheckResponse()
    }

}