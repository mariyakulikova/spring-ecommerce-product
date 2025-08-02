package ecommerce.controller

import ecommerce.model.Product
import ecommerce.repository.ProductRepository
import ecommerce.service.ProductService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.net.URI
import kotlin.test.assertEquals

class ProductControllerTest {
    private val repository: ProductRepository = mock(ProductRepository::class.java)
    private val service: ProductService = mock(ProductService::class.java)
    private val controller = ProductController(repository, service)

    @Test
    fun `should create product and return 201 with Location header`() {
        val product = Product(name = "Apple", price = 1.0, imageUrl = "url")
        `when`(repository.create(product)).thenReturn(42L)

        val response: ResponseEntity<Unit> = controller.createProduct(product)

        verify(service).validateUniqueName(product)
        verify(repository).create(product)

        assertEquals(HttpStatus.CREATED, response.statusCode)
        assertEquals(URI.create("/api/products/42"), response.headers.location)
    }

    @Test
    fun `should return all products`() {
        val products =
            listOf(
                Product(id = 1, name = "Apple", price = 1.0, imageUrl = "url"),
                Product(id = 2, name = "Banana", price = 2.0, imageUrl = "url"),
            )
        `when`(repository.getAll()).thenReturn(products)

        val response = controller.readProducts()

        assertEquals(HttpStatus.OK, response.statusCode)
        assertEquals(products, response.body)
    }

    @Test
    fun `should update a product and return 200`() {
        val product = Product(name = "NewName", price = 3.0, imageUrl = "img")

        val response = controller.updateProduct(product, id = 5)

        verify(repository).update(5, product)
        assertEquals(HttpStatus.OK, response.statusCode)
    }

    @Test
    fun `should delete a product and return 204`() {
        val response = controller.deleteProduct(id = 7)

        verify(repository).delete(7)
        assertEquals(HttpStatus.NO_CONTENT, response.statusCode)
    }
}
