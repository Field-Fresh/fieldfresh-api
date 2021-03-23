package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface SellOrdersRepository : BaseRepository<SellOrder> {

    @Query(
        """
            Select sp
            from SellProduct sp
            where sp.product.id in :productIds and sp.status = :status
            and sp.sellOrder.proxy = :proxy
        """
    )
    fun findOverlappingOrdersForProxy(productIds: Set<String>, proxy: Proxy, status: OrderStatus): List<SellProduct>
}