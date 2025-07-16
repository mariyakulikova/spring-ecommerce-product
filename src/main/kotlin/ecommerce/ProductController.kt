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
import java.net.URI
import java.util.concurrent.atomic.AtomicLong

// TODO: remove mock data for product list
//val MOCK_PRODUCTS = listOf(
//    Product(name = "melon ice cream", price = 2.33, imageUrl = "link-1.jpg"),
//    Product(name = "lemon ice cream", price = 1.50, imageUrl = "link-2.jpg"),
//    Product(name = "vanilla ice cream", price = 3.09, imageUrl = "link-3.jpg")
//)
//
//val products: MutableMap<Long, Product> = MOCK_PRODUCTS
//    .map { product ->
//        val id = index.getAndIncrement()
//        id to Product.toEntity(product, id)
//    }
//    .toMap()
//    .toMutableMap()

val products: MutableMap<Long, Product> = HashMap()
val index = AtomicLong(1)

@Controller
class ProductController {

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

    @DeleteMapping("/api/products/{id}")
    fun deleteProduct(
        @PathVariable id: Long,
    ): ResponseEntity<Void> {
        val deletedProduct = products.remove(id)

        if (deletedProduct == null) throw RuntimeException()

        return ResponseEntity.noContent().build()
    }

    // Intercepts any thrown RuntimeException within ProductController
    @ExceptionHandler(RuntimeException::class)
    fun handle(e: RuntimeException): ResponseEntity<Unit> {
        return ResponseEntity.notFound().build()
    }
}

@Controller
class ProductPageController {
    @GetMapping("/products")
    fun getProducts(model: Model): String {
        model.addAttribute("products", products.values.toList())
        return "products"
    }
}
