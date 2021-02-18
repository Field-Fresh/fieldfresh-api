package com.fieldfreshmarket.api.usecase.products

import com.fieldfreshmarket.api.data.products.GetProductsData
import com.fieldfreshmarket.api.model.ClassType
import com.fieldfreshmarket.api.model.Product
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

const val MAX_RESULTS = 100

@Component
@Transactional(readOnly = true)
class GetProductsUsecase {

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    // TODO this should be extracted to a custom repository. Should only call repositories from usecase layer
    fun execute(data: GetProductsData): List<Product> {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val cr: CriteriaQuery<Product> = cb.createQuery(Product::class.java)
        val root: Root<Product> = cr.from(Product::class.java)
        cr.select(root).where(*data.toPredicates(cb, root))

        return entityManager.createQuery(cr).apply {
            maxResults = data.resultLimit ?: MAX_RESULTS
        }.resultList
    }

    private fun GetProductsData.toPredicates(cb: CriteriaBuilder, root: Root<Product>): Array<Predicate> =
        listOfNotNull(
            category?.let { cb.equal(root.get<String>("category"), it) },
            type?.let { cb.equal(root.get<String>("type"), it) },
            family?.let { cb.equal(root.get<String>("family"), it) },
            classType?.let { cb.equal(root.get<ClassType>("classType"), it) },
        ).toTypedArray()
}