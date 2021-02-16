package com.fieldfreshmarket.api.usecase.orders.sell

import com.fieldfreshmarket.api.data.orders.sell.CreateSellOrderData
import com.fieldfreshmarket.api.data.orders.sell.CreateSellProductData
import com.fieldfreshmarket.api.repository.products.ProductsRepository
import com.fieldfreshmarket.api.repository.ProxyRepository
import com.fieldfreshmarket.api.repository.order.SellOrdersRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.Assert.assertEquals
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import stubs.*
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.temporal.ChronoUnit
import javax.persistence.EntityNotFoundException

@RunWith(MockitoJUnitRunner::class)
class CreateSellOrderUsecaseTest {

    @Mock
    private lateinit var sellOrderRepository: SellOrdersRepository

    @Mock
    private lateinit var proxyRepository: ProxyRepository

    @Mock
    private lateinit var productRepository: ProductsRepository

    @InjectMocks
    private lateinit var usecase: CreateSellOrderUsecase

    @Test(expected = IllegalStateException::class)
    fun testFailIfNoProducts() {
        val data = CreateSellOrderData(grant(), TEST_PROXY_ID, sellProducts = listOf())

        usecase.execute(data)
    }

    /* Dont allow an order with different params for the same product */
    @Test(expected = IllegalArgumentException::class)
    fun testFailIfDuplicateProducts() {
        val sellProducts = listOf(
            createSellProductData(productId = TEST_PRODUCT_ID),
            createSellProductData(productId = TEST_PRODUCT_ID_2),
            createSellProductData(productId = TEST_PRODUCT_ID),
        )
        val data = CreateSellOrderData(grant(), TEST_PROXY_ID, sellProducts = sellProducts)
        usecase.execute(data)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test that the earliest date is at least 1 day in the future`() {
        val sellProducts = listOf(
            createSellProductData(productId = TEST_PRODUCT_ID, earliestDate = LocalDate.now()),
        )
        val data = CreateSellOrderData(grant(), TEST_PROXY_ID, sellProducts = sellProducts)
        usecase.execute(data)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test latest date is after earliest`() {
        val earliestDate = LocalDate.now().plusDays(1)
        val latestDate = earliestDate.minusDays(1)
        val sellProducts = listOf(
            createSellProductData(productId = TEST_PRODUCT_ID, earliestDate = earliestDate, latestDate = latestDate),
        )
        val data = CreateSellOrderData(grant(), TEST_PROXY_ID, sellProducts = sellProducts)
        usecase.execute(data)
    }

    @Test(expected = EntityNotFoundException::class)
    fun `test that all products exist`() {
        val sellProducts = listOf(
            createSellProductData(productId = TEST_PRODUCT_ID),
            createSellProductData(productId = TEST_PRODUCT_ID_2),
        )
        val data = CreateSellOrderData(grant(), TEST_PROXY_ID, sellProducts = sellProducts)
        whenever(proxyRepository.findByIdForUser(any(), any())).thenReturn(proxy())
        usecase.execute(data)
    }

    @Test(expected = EntityNotFoundException::class)
    fun `test proxy exists`() {
        val sellProducts = listOf(
            createSellProductData(productId = TEST_PRODUCT_ID),
        )
        val data = CreateSellOrderData(grant(), TEST_PROXY_ID, sellProducts = sellProducts)
        usecase.execute(data)
    }

    @Test
    fun `test sell order creation succeeds with single products`() {
        val earlyInstant = Instant.ofEpochSecond(1613081761 + 86400)
        val latestInstant = Instant.ofEpochSecond(1613081761 + 2 * 86400)
        val earliestDate = LocalDate.ofInstant(earlyInstant, ZoneOffset.UTC)
        val latestDate = LocalDate.ofInstant(latestInstant, ZoneOffset.UTC)
        val sellProductData = createSellProductData(
            productId = TEST_PRODUCT_ID,
            earliestDate = earliestDate,
            latestDate = earliestDate.plusDays(1)
        )
        val sellProducts = listOf(
            sellProductData,
        )
        val data = CreateSellOrderData(grant(), TEST_PROXY_ID, sellProducts = sellProducts)
        val proxy = proxy()
        whenever(proxyRepository.findByIdForUser(any(), any())).thenReturn(proxy)
        val product = product(id = TEST_PRODUCT_ID)
        whenever(productRepository.findAllByIdIn(any())).thenReturn(listOf(product))

        val result = usecase.execute(data)

        verify(sellOrderRepository).save(any())
        assertEquals(result.round, 0)
        assertEquals(result.sellProducts.first().earliestDate, earlyInstant.truncatedTo(ChronoUnit.DAYS))
        assertEquals(result.sellProducts.first().latestDate, latestInstant.truncatedTo(ChronoUnit.DAYS))
        assertEquals(result.sellProducts.first().volume, sellProductData.volume)
        assertEquals(result.sellProducts.first().minPriceCents, sellProductData.minPriceCents)
        assertThat(result.sellProducts.first().product).isEqualToComparingFieldByFieldRecursively(product)
    }


    private fun createSellProductData(
        productId: String,
        earliestDate: LocalDate = LocalDate.now().plusDays(1),
        latestDate: LocalDate = LocalDate.now().plusDays(2)
    ) = CreateSellProductData(
        earliestDate = earliestDate,
        latestDate = latestDate,
        minPriceCents = 10,
        volume = 25.5,
        productId = productId
    )
}
