package com.fieldfreshmarket.api.repository

import com.fieldfreshmarket.api.model.Proxy
import com.fieldfreshmarket.api.model.User
import org.springframework.data.jpa.repository.Query

interface ProxyRepository: BaseRepository<Proxy> {

    @Query(
        """
            SELECT p 
            from  Proxy p
            where p.id = :id AND p.user = :user
        """
    )
    fun findByIdForUser(id: String, user: User): Proxy?
}