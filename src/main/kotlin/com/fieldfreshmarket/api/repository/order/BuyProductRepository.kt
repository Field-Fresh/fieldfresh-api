package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface BuyProductRepository : BaseRepository<BuyProduct> {

    @Query(
        """
            select bp.product, SUM(bp.volume) as totalVolume
            from BuyProduct bp
            where bp.buyOrder.status = OrderStatus.PENDING
            group by bp.product
            order by totalVolume
        """
    )
    fun findAllPendingProducts(): List<PendingProductStatistic>
}