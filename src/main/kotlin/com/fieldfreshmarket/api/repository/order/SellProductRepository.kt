package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface SellProductRepository : BaseRepository<SellProduct> {

    @Query(
        """
            select sp.product, SUM(sp.volume) as totalVolume
            from SellProduct sp
            where sp.sellOrder.status = OrderStatus.PENDING
            group by sp.product
            order by totalVolume
        """
    )
    fun findAllPendingProducts(): List<PendingProductStatistic>

}