package com.fieldfreshmarket.api.config

import org.hibernate.search.jpa.FullTextEntityManager
import org.hibernate.search.jpa.Search
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component
import javax.persistence.EntityManager
import javax.persistence.PersistenceContext
import javax.transaction.Transactional


@Component
class SearchInit : ApplicationListener<ContextRefreshedEvent> {

   @PersistenceContext
   private lateinit var entityManager: EntityManager

   @Transactional
   override fun onApplicationEvent(event: ContextRefreshedEvent) {
      val fullTextEntityManager: FullTextEntityManager = Search.getFullTextEntityManager(entityManager)
      try {
         fullTextEntityManager.createIndexer().startAndWait()
      } catch (e: InterruptedException) {
         println("Error occured trying to build Hibernate Search indexes "
            + e.toString())
      }
   }
}