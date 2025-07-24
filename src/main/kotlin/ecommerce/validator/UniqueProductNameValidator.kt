package ecommerce.validator

import ecommerce.dto.Product
import ecommerce.repository.ProductRepository
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import org.springframework.beans.factory.annotation.Autowired

class UniqueProductNameValidator(
    @Autowired
    private val jdbcProductStore: ProductRepository,
) : ConstraintValidator<UniqueProductName, String> {
    override fun isValid(
        value: String,
        context: ConstraintValidatorContext,
    ): Boolean {
        return !jdbcProductStore.existsByName(Product(name = value, price = 0.0, imageUrl = "image.png"))
    }
}
