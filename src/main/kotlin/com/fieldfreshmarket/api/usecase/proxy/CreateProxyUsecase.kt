package com.fieldfreshmarket.api.usecase.proxy

import com.fieldfreshmarket.api.data.proxy.CreateProxyData
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import com.fieldfreshmarket.api.repository.ProxyRepository
import com.fieldfreshmarket.api.repository.UsersRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class CreateProxyUsecase {
    @Autowired
    private lateinit var repository: ProxyRepository

    @Autowired
    private lateinit var usersRepository: UsersRepository


    fun execute(data: CreateProxyData): Proxy {
        val user = usersRepository.findById(data.userId)
            ?: throw IllegalArgumentException("User with id: ${data.userId} does not exists.")

        return repository.save(data.toModel(user))
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