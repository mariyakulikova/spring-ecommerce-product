package ecommerce

import ecommerce.dto.Product
import ecommerce.repository.ProductRepository
import ecommerce.utiles.Constants
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class ProductUniqueNameTest {
    @Autowired
    private lateinit var jdbcProductStore: ProductRepository

    @Autowired
    private lateinit var validator: Validator

    @BeforeEach
    fun setUp() {
        jdbcProductStore.create(Product(name = "existing", price = 1.0, imageUrl = "http://test.com"))
    }

    @Test
    fun `should fail when name already exists`() {
        val product = Product(name = "existing", price = 2.0, imageUrl = "http://test.com")
        val violations = validator.validate(product)

        assertThat(violations).anyMatch { it.message.contains(Constants.ERR_NAME_UNIQUE) }
    }
}
