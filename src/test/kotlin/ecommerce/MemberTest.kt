package ecommerce

import ecommerce.dto.Member
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.assertj.core.api.Assertions.assertThat

class MemberTest {

    @Test
    fun `should create member with valid email and password`() {
        val member = Member(
            email = "user@example.com",
            password = "securePass123"
        )

        assertThat(member.email).isEqualTo("user@example.com")
        assertThat(member.password).isEqualTo("securePass123")
    }

    @Test
    fun `should throw exception when email is blank`() {
        val exception = assertThrows<IllegalArgumentException> {
            Member(
                email = "   ",
                password = "1234"
            )
        }
        assertThat(exception.message).isEqualTo("Email cannot be blank")
    }

    @Test
    fun `should throw exception when password is blank`() {
        val exception = assertThrows<IllegalArgumentException> {
            Member(
                email = "user@example.com",
                password = "  "
            )
        }
        assertThat(exception.message).isEqualTo("Password cannot be blank")
    }

    @Test
    fun `should throw exception when email format is invalid`() {
        val exception = assertThrows<IllegalArgumentException> {
            Member(
                email = "user_at_example.com",
                password = "1234"
            )
        }
        assertThat(exception.message).isEqualTo("Invalid email format: user_at_example.com")
    }
}
