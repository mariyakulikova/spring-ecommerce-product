package ecommerce.service

import ecommerce.model.Product
import ecommerce.exception.DuplicateProductNameException
import ecommerce.repository.ProductRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductServiceTest {
    private lateinit var repository: ProductRepository
    private lateinit var service: ProductService

    @BeforeEach
    fun setUp() {
        repository = mockk()
        service = ProductService(repository)
    }

    @Test
    fun `should throw DuplicateProductNameException when product name exists`() {
        val product = Product(name = "Vanilla", price = 10.0, imageUrl = "http://image.jpg")

        every { repository.existsByName(product) } returns true

        assertThrows<DuplicateProductNameException> {
            service.validateUniqueName(product)
        }
    }

    @Test
    fun `should pass silently when product name is unique`() {
        val product = Product(name = "UniqueName", price = 10.0, imageUrl = "http://image.jpg")

        every { repository.existsByName(product) } returns false

        service.validateUniqueName(product) // no exception expected
    }
}
