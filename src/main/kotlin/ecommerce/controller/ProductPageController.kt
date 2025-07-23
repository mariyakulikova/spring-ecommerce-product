package ecommerce.controller

import ecommerce.repository.ProductStore
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class ProductPageController(private val jdbcProductStore: ProductStore) {
    @GetMapping("/products")
    fun getProducts(model: Model): String {
        model.addAttribute("products", jdbcProductStore.getAll())
        return "products"
    }
}
