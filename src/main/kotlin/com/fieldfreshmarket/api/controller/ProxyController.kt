package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.proxy.CreateProxyRequest
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.services.ProxyService
import com.fieldfreshmarket.api.view.ProxyView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping(path = ["/proxy"])
class ProxyController : Controller() {

    @Autowired
    private lateinit var proxyService: ProxyService

    @PatchMapping("/new")
    fun create(@RequestBody request: CreateProxyRequest): ProxyView =
        ProxyView(proxyService.create(request.toData()))
}