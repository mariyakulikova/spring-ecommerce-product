package ecommerce.service

import ecommerce.model.Product
import ecommerce.exception.DuplicateProductNameException
import ecommerce.repository.ProductRepository
import org.springframework.stereotype.Service

@Service
class ProductService(
    private val jdbc: ProductRepository,
) {
    fun validateUniqueName(product: Product) {
        when {
            jdbc.existsByName(product) -> throw DuplicateProductNameException(
                "Product name '${product.name}' already exists.",
            )
        }
    }
}
