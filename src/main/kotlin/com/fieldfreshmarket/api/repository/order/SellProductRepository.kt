package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface SellProductRepository : BaseRepository<SellProduct> {

    @Query(
        """
            select sp.product.id as id, SUM(sp.volume) as totalVolume
            from SellProduct sp
            where sp.status = 'PENDING' 
            group by sp.product.id
            order by totalVolume
        """
    )
    fun findAllPendingProducts(): List<PendingProductStatistic>

    @Query(
        """
            select sp.sellOrder
            from SellProduct sp
        """
    )
    fun getAllSellOrders(): List<SellOrder>
}