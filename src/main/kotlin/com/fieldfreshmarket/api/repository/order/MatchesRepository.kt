package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.Match
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query

interface MatchesRepository : BaseRepository<Match> {

    @Query(
        """
            select m from Match m
            where m.buyProduct.buyOrder.proxy = :proxy
        """
    )
    fun getBuyMatchesForProxy(proxy: Proxy): List<Match>

    @Query(
        """
            select m from Match m
            where m.sellProduct.sellOrder.proxy = :proxy
        """
    )
    fun getSellMatchesForProxy(proxy: Proxy): List<Match>
}