package ecommerce

import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import java.net.URI
import java.util.concurrent.atomic.AtomicLong

@Controller
class ProductController {
    private val products: MutableMap<Long, Product> = HashMap()
    private val index = AtomicLong(1)

    @PostMapping("/api/products")
    fun createProduct(
        @RequestBody product: Product,
    ): ResponseEntity<Void> {
        val id = index.getAndIncrement()
        val newProduct: Product = Product.toEntity(product, id)
        products.put(id, newProduct)
        return ResponseEntity.created(URI.create("api/products/$id")).build()
    }

    @GetMapping("/api/products")
    fun readProducts(): ResponseEntity<List<Product>> {
        return ResponseEntity.ok(products.values.toList())
    }

    @PutMapping("/api/products/{id}")
    fun updateProduct(
        @RequestBody newProduct: Product,
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        val currentProduct = products[id]

        if (currentProduct == null) {
            throw RuntimeException()
        }

        currentProduct.update(newProduct)

        return ResponseEntity.ok().build()
    }
}
