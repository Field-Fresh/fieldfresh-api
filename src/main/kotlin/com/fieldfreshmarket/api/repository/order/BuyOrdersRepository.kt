package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.buy.BuyOrder
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface BuyOrdersRepository : BaseRepository<BuyOrder> {

    @Query(
        """
            Select bp
            from BuyProduct bp
            where bp.product.id in :productIds
            and bp.buyOrder.proxy = :proxy
        """
    )
    fun findOverlappingOrdersForProxy(productIds: Set<String>, proxy: Proxy): List<BuyProduct>
}