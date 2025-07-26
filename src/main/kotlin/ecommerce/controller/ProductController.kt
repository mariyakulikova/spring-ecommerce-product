package ecommerce.controller

import ecommerce.dto.Product
import ecommerce.repository.ProductRepository
import ecommerce.service.ProductService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("api/products")
class ProductController(
    private val jdbcProductStore: ProductRepository,
    private val service: ProductService
) {
    @PostMapping
    fun createProduct(
        @Valid @RequestBody product: Product,
    ): ResponseEntity<Unit> {
        service.validateUniqueName(product)
        val id = jdbcProductStore.create(product)
        return ResponseEntity.created(URI.create("/api/products/$id")).build()
    }

    @GetMapping
    fun readProducts(): ResponseEntity<List<Product>> {
        val products = jdbcProductStore.getAll()
        return ResponseEntity.ok(products)
    }

    @PutMapping("/{id}")
    fun updateProduct(
        @Valid @RequestBody newProduct: Product,
        @PathVariable id: Long,
    ): ResponseEntity<Unit> {
        jdbcProductStore.update(id, newProduct)
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): ResponseEntity<Unit> {
        jdbcProductStore.delete(id)
        return ResponseEntity.noContent().build()
    }
}
