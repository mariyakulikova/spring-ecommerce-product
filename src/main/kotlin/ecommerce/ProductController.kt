package ecommerce

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("api/products")
class ProductController(private val jdbcProductStore: ProductStore) {
    @PostMapping
    fun createProduct(
        @RequestBody product: Product,
    ): ResponseEntity<Unit> {
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
        @RequestBody newProduct: Product,
        @PathVariable id: Long,
    ): ResponseEntity<Unit> {
        val updated = jdbcProductStore.update(id, newProduct)
        if (!updated) {
            throw RuntimeException("Product not found")
        }
        return ResponseEntity.ok().build()
    }

    @DeleteMapping("/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): ResponseEntity<Unit> {
        val deleted = jdbcProductStore.delete(id)
        if (!deleted) {
            throw RuntimeException()
        }
        return ResponseEntity.noContent().build()
    }

    @ExceptionHandler(RuntimeException::class)
    fun handle(e: RuntimeException): ResponseEntity<Unit> {
        return ResponseEntity.notFound().build()
    }
}

@Controller
class ProductPageController(private val jdbcProductStore: JdbcProductStore) {
    @GetMapping("/products")
    fun getProducts(model: Model): String {
        model.addAttribute("products", jdbcProductStore.getAll())
        return "products"
    }
}
