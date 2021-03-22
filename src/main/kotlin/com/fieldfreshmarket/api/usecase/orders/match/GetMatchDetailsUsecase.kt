package com.fieldfreshmarket.api.usecase.orders.match

import com.fieldfreshmarket.api.data.orders.matches.GetMatchDetailsData
import com.fieldfreshmarket.api.data.orders.matches.MatchDetailsData
import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.repository.ProxyRepository
import com.fieldfreshmarket.api.repository.order.MatchesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.persistence.EntityNotFoundException

@Component
class GetMatchDetailsUsecase {

    @Autowired
    private lateinit var matchesRepository: MatchesRepository

    @Autowired
    private lateinit var proxyRepository: ProxyRepository

    fun execute(data: GetMatchDetailsData): MatchDetailsData {
        val grantedUserProxy: Proxy = proxyRepository.findByIdForUser(data.proxyId, data.grant.user)
            ?: throw EntityNotFoundException("Proxy not found.")
        val match: Match = matchesRepository.findMyIdForProxy(data.matchId, grantedUserProxy)
            ?: throw EntityNotFoundException("Match not found.")

        val matchedProxy: Proxy =
            when {
                match.sellProduct.sellOrder.proxy.id == grantedUserProxy.id -> {
                    match.buyProduct.buyOrder.proxy
                }
                match.buyProduct.buyOrder.proxy.id == grantedUserProxy.id -> {
                    match.sellProduct.sellOrder.proxy
                }
                else -> {
                    throw IllegalArgumentException("Proxy is not present.")
                }
            }

        return MatchDetailsData(
            match = match,
            matchedProxy = matchedProxy
        )
    }
}