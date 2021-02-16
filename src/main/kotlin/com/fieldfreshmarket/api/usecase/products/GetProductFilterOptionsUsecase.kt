package com.fieldfreshmarket.api.usecase.products

import com.fieldfreshmarket.api.data.products.GetProductFilterOptionsData
import com.fieldfreshmarket.api.data.products.ProductFilterOptions
import com.fieldfreshmarket.api.model.ClassType
import com.fieldfreshmarket.api.model.Product
import com.fieldfreshmarket.api.repository.products.ProductsRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.persistence.criteria.CriteriaBuilder
import javax.persistence.criteria.CriteriaQuery
import javax.persistence.criteria.Predicate
import javax.persistence.criteria.Root

@Component
@Transactional(readOnly = true)
class GetProductFilterOptionsUsecase {

    @Autowired
    private lateinit var productsRepository: ProductsRepository

    @PersistenceContext
    private lateinit var entityManager: EntityManager

    // TODO this should be extracted to a custom repository. Should only call repositories from usecase layer
    fun execute(data: GetProductFilterOptionsData): ProductFilterOptions {
        val cb: CriteriaBuilder = entityManager.criteriaBuilder
        val cr: CriteriaQuery<String> = cb.createQuery(String::class.java)
        val root: Root<Product> = cr.from(Product::class.java)

        val options = ProductFilterOptions()
        cr.where(*data.toPredicates(cb, root)).distinct(true).also { _cr ->
            options.categories = entityManager.createQuery(_cr.select(root.get("category"))).resultList
            options.types = entityManager.createQuery(_cr.select(root.get("type"))).resultList
            options.families = entityManager.createQuery(_cr.select(root.get("family"))).resultList
        }

        return options.copy(classTypes = ClassType.values().toList())
    }

    private fun GetProductFilterOptionsData.toPredicates(cb: CriteriaBuilder, root: Root<Product>): Array<Predicate> =
        listOfNotNull(
            category?.let { cb.equal(root.get<String>("category"), it) },
            type?.let { cb.equal(root.get<String>("type"), it) },
            family?.let { cb.equal(root.get<String>("family"), it) },
            classType?.let { cb.equal(root.get<ClassType>("classType"), it) },
        ).toTypedArray()
}
