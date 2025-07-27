package ecommerce.dto

import ecommerce.utiles.Constants
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class ProductTest {
    private lateinit var validator: Validator

    @BeforeEach
    fun setUp() {
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    @Test
    fun `name greater 15 characters should fail validation`() {
        val product = Product(name = "valid product name here", price = 5.0, imageUrl = "http://image.jpg")
        val violations = validator.validate(product)

        Assertions.assertThat(violations).anyMatch { it.message.contains(Constants.ERR_NAME_SIZE) }
    }

    @Test
    fun `name contains not allowed characters should fail validation`() {
        val product = Product(name = "#name", price = 5.0, imageUrl = "http://image.jpg")
        val violations = validator.validate(product)

        Assertions.assertThat(violations).anyMatch { it.message.contains(Constants.ERR_NAME_REGEX) }
    }

    @Test
    fun `empty name should fail validation`() {
        val product = Product(name = "", price = 5.0, imageUrl = "http://image.jpg")
        val violations = validator.validate(product)

        Assertions.assertThat(violations).anyMatch { it.message.contains(Constants.ERR_NAME_NOT_BLANK) }
    }

    @Test
    fun `price zero should fail validation`() {
        val product = Product(name = "short name", price = 0.0, imageUrl = "http://image.jpg")
        val violations = validator.validate(product)

        Assertions.assertThat(violations).anyMatch { it.message.contains(Constants.ERR_PRICE_POSITIVE) }
    }

    @Test
    fun `link to image without http prefix should fail validation`() {
        val product = Product(name = "product name", price = 5.0, imageUrl = "image.jpg")
        val violations = validator.validate(product)

        Assertions.assertThat(violations).anyMatch { it.message.contains(Constants.ERR_URL_REGEX) }
    }
}
