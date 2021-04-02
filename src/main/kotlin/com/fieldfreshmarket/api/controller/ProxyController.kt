package com.fieldfreshmarket.api.controller

import com.fieldfreshmarket.api.controller.request.proxy.CreateProxyRequest
import com.fieldfreshmarket.api.core.Controller
import com.fieldfreshmarket.api.services.ProxyService
import com.fieldfreshmarket.api.view.ProxyView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(path = ["/proxy"])
class ProxyController : Controller() {

    @Autowired
    private lateinit var proxyService: ProxyService

    @PostMapping("/new")
    fun create(@RequestBody request: CreateProxyRequest): ProxyView =
        ProxyView(proxyService.create(grant, request.toData()))

    @GetMapping
    fun get(): List<ProxyView> = proxyService.get(grant).map { ProxyView(it) }

    @GetMapping("/{id}")
    fun getById(@PathVariable(name = "id") id: String): ProxyView = ProxyView(proxyService.getById(id, grant))
}