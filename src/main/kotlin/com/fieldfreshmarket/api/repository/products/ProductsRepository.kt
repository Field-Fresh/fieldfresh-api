package com.fieldfreshmarket.api.repository.products

import com.fieldfreshmarket.api.model.ClassType
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.repository.BaseRepository
import org.springframework.data.jpa.repository.Query

interface ProductsRepository : BaseRepository<Product>, ProductsRepositoryExtensions {
    fun findById(id: String): Product?

    fun findAllByIdIn(ids: Set<String>): List<Product>

    @Query(
        """
            select distinct (p.category)
            from Product p
        """
    )
    fun findAllCategories(): Set<String>

    @Query(
        """
            select distinct (p.family)
            from Product p
        """
    )
    fun findAllFamilies(): Set<String>

    @Query(
        """
            select distinct (p.type)
            from Product p
        """
    )
    fun findAllTypes(): Set<String>

    @Query(
        """
            select distinct (p.classType)
            from Product p
        """
    )
    fun findAllClasses(): Set<ClassType>
}