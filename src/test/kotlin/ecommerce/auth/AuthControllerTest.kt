package ecommerce.auth

import com.fasterxml.jackson.databind.ObjectMapper
import com.ninjasquad.springmockk.MockkBean
import ecommerce.dto.TokenRequest
import ecommerce.dto.TokenResponse
import ecommerce.service.AuthService
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {
    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockkBean
    private lateinit var authService: AuthService

    @Test
    fun `should return token when login is successful`() {
        val request = TokenRequest("user@email.com", "1234")
        val expectedResponse = TokenResponse("mocked-token")

        every { authService.login(request) } returns expectedResponse

        mockMvc.post("/api/members/login") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isOk() }
            content {
                json("""{ "token": "mocked-token" }""")
            }
        }
    }

    @Test
    fun `should return token when registration is successful`() {
        val request = TokenRequest("user@email.com", "1234")
        val expectedResponse = TokenResponse("registered-token")

        every { authService.register(request) } returns expectedResponse

        mockMvc.post("/api/members/register") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(request)
        }.andExpect {
            status { isEqualTo(201) }
            content {
                json("""{ "token": "registered-token" }""")
            }
        }
    }
}
