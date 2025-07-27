package ecommerce.auth

import jakarta.servlet.http.HttpServletRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import java.util.*

class BearerAuthorizationExtractorTest {

    private val extractor = BearerAuthorizationExtractor()

    @Test
    fun `should extract token from Bearer Authorization header`() {
        val request = mock(HttpServletRequest::class.java)
        val headers = Collections.enumeration(listOf("Bearer my.jwt.token"))
        `when`(request.getHeaders("Authorization")).thenReturn(headers)

        val token = extractor.extract(request)

        assertEquals("my.jwt.token", token)
    }

    @Test
    fun `should return empty string when Authorization header is missing`() {
        val request = mock(HttpServletRequest::class.java)
        val headers = Collections.emptyEnumeration<String>()
        `when`(request.getHeaders("Authorization")).thenReturn(headers)

        val token = extractor.extract(request)

        assertEquals("", token)
    }

    @Test
    fun `should return empty string when Authorization is not Bearer`() {
        val request = mock(HttpServletRequest::class.java)
        val headers = Collections.enumeration(listOf("Basic abcdefg"))
        `when`(request.getHeaders("Authorization")).thenReturn(headers)

        val token = extractor.extract(request)

        assertEquals("", token)
    }

    @Test
    fun `should extract token before comma if present`() {
        val request = mock(HttpServletRequest::class.java)
        val headers = Collections.enumeration(listOf("Bearer token1,token2"))
        `when`(request.getHeaders("Authorization")).thenReturn(headers)

        val token = extractor.extract(request)

        assertEquals("token1", token)
    }
}

