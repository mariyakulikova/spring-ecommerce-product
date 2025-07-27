package ecommerce.service

import ecommerce.dto.Product
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
