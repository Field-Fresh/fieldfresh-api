package com.fieldfreshmarket.api.usecase.proxy

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.Grant
import com.fieldfreshmarket.api.data.proxy.CreateProxyData
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.repository.ProxyRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateProxyUsecase {
    @Autowired
    private lateinit var repository: ProxyRepository

    fun execute(grant: Grant, data: CreateProxyData): Proxy {
        return repository.save(data.toModel(grant.user))
    }

    private fun CreateProxyData.toModel(user: User): Proxy =
        Proxy(
            user = user,
            displayName = name,
            description = description,
            streetAddress = streetAddress,
            city = city,
            province = province,
            country = country,
            postalCode = postalCode,
            latitude = lat,
            longitude = long
        )
}