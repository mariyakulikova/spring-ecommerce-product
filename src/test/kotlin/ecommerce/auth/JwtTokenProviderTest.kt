package ecommerce.auth

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(properties = ["spring.sql.init.mode=never"])
class JwtTokenProviderTest {
    @Autowired
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Test
    fun `creates and validates token successfully`() {
        val email = "test@email.com"
        val token = jwtTokenProvider.createToken(email)

        Assertions.assertThat(token).isNotEmpty()
        Assertions.assertThat(jwtTokenProvider.validateToken(token)).isTrue()
        Assertions.assertThat(jwtTokenProvider.getPayload(token)).isEqualTo(email)
    }

    @Test
    fun `validates invalid token returns false`() {
        Assertions.assertThat(jwtTokenProvider.validateToken("invalid.token.here")).isFalse()
    }
}
