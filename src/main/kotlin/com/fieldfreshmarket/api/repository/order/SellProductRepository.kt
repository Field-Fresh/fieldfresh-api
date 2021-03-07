package com.fieldfreshmarket.api.repository.order

import com.fieldfreshmarket.api.model.order.OrderStatus
import com.fieldfreshmarket.api.model.order.buy.BuyProduct
import com.fieldfreshmarket.api.model.order.sell.SellOrder
import com.fieldfreshmarket.api.model.order.sell.SellProduct
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
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
            select sp
            from SellProduct sp
            where sp.status = :status and sp.sellOrder.proxy.id = :proxyId
        """
    )
    fun getAllFor(proxyId: String, status: OrderStatus): List<SellProduct>

    @Query(
        """
            select sp 
            from SellProduct sp
            where sp.status = :status
        """
    )
    fun getAll(status: OrderStatus, pageable: Pageable): Page<SellProduct>
}