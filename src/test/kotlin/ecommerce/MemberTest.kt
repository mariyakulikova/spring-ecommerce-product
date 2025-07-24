package ecommerce

import ecommerce.dto.Member
import ecommerce.utiles.Constants
import jakarta.validation.Validation
import jakarta.validation.Validator
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberTest {
    private lateinit var validator: Validator

    @BeforeEach
    fun setUp() {
        validator = Validation.buildDefaultValidatorFactory().validator
    }

    @Test
    fun `blank email should fail validation`() {
        val product = Member(email = "", password = "1234")
        val violations = validator.validate(product)

        assertThat(violations).anyMatch { it.message.contains(Constants.ERR_EMAIL_BLANK) }
    }

    @Test
    fun `email in worg format should fail validation`() {
        val product = Member(email = "email", password = "1234")
        val violations = validator.validate(product)

        assertThat(violations).anyMatch { it.message.contains(Constants.ERR_EMAIL_FORMAT) }
    }

    @Test
    fun `blank password should fail validation`() {
        val product = Member(email = "user@email.com", password = "")
        val violations = validator.validate(product)

        assertThat(violations).anyMatch { it.message.contains(Constants.ERR_PASSWORD) }
    }
}
