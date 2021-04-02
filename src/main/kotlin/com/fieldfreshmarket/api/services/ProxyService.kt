package com.fieldfreshmarket.api.services

import com.fieldfreshmarket.api.data.AccessGrant
import com.fieldfreshmarket.api.data.proxy.CreateProxyData
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.repository.ProxyRepository
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.usecase.proxy.CreateProxyUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityNotFoundException

@Service
@Transactional
@Component
class ProxyService {

    @Autowired
    private lateinit var createProxyUsecase: CreateProxyUsecase

    @Autowired
    private lateinit var proxyRepository: ProxyRepository

    fun create(grant: AccessGrant, data: CreateProxyData): Proxy {
        return createProxyUsecase.execute(grant, data)
    }

    fun get(grant: AccessGrant): List<Proxy> = proxyRepository.findAllByUser(grant.user)

    fun getById(id: String, grant: AccessGrant): Proxy = proxyRepository.findByIdForUser(id, grant.user)
        ?: throw EntityNotFoundException("No Proxy with id ($id)")
}
