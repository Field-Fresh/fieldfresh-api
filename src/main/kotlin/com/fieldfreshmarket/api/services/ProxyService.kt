package com.fieldfreshmarket.api.services

import com.fieldfreshmarket.api.data.proxy.CreateProxyData
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.repository.UsersRepository
import com.fieldfreshmarket.api.usecase.proxy.CreateProxyUsecase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
@Component
class ProxyService {

   @Autowired
   private lateinit var createProxyUsecase: CreateProxyUsecase

   fun create(data: CreateProxyData): Proxy {
      return createProxyUsecase.execute(data)
   }

}
