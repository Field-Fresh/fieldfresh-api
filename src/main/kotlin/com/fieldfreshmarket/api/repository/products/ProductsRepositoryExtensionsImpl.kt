package com.fieldfreshmarket.api.repository.products

import com.fieldfreshmarket.api.model.Product
import org.hibernate.search.jpa.FullTextEntityManager
import org.hibernate.search.jpa.FullTextQuery
import org.hibernate.search.jpa.Search
import org.hibernate.search.query.dsl.QueryBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext

@Repository
internal class ProductsRepositoryExtensionsImpl : ProductsRepositoryExtensions {

    @PersistenceContext
    lateinit var entityManager: EntityManager

    private val logger: Logger = LoggerFactory.getLogger(ProductsRepositoryExtensionsImpl::class.java)

    override fun search(searchText: String, limit: Int): List<Product> {
        val (entityManager, queryBuilder) = fulltextQueryBuilder()

        val searchQuery = queryBuilder
            .keyword()
            .fuzzy()
            .withPrefixLength(3)
            .onFields("category", "type", "family")
            .matching(searchText.toLowerCase())
            .createQuery()
        val productQuery: FullTextQuery = entityManager
            .createFullTextQuery(searchQuery, Product::class.java)

        productQuery.maxResults = limit
        return productQuery.resultList as List<Product>
    }

    private fun fulltextQueryBuilder(): Pair<FullTextEntityManager, QueryBuilder> {
        val fullTextEntityManager: FullTextEntityManager = Search.getFullTextEntityManager(entityManager)
        val queryBuilder: QueryBuilder = fullTextEntityManager.searchFactory
            .buildQueryBuilder()
            .forEntity(Product::class.java)
            .get()
        return fullTextEntityManager to queryBuilder
    }

}