package ecommerce

import ecommerce.auth.JwtTokenProvider
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtTokenProviderTest {

    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Test
    fun `creates and validates token successfully`() {
        val email = "test@email.com"
        val token = jwtTokenProvider.createToken(email)

        assertThat(token).isNotEmpty()
        assertThat(jwtTokenProvider.validateToken(token)).isTrue()
        assertThat(jwtTokenProvider.getPayload(token)).isEqualTo(email)
    }

    @Test
    fun `validates invalid token returns false`() {
        assertThat(jwtTokenProvider.validateToken("invalid.token.here")).isFalse()
    }
}
