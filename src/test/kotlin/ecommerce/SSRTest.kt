package ecommerce

import io.restassured.RestAssured
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext

@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
class SSRTest() {
    @BeforeEach
    fun seedProducts() {
        products.clear()
        MOCK_PRODUCTS.forEachIndexed { i, p ->
            val id = i.toLong() + 1
            products[id] = Product.toEntity(p, id)
        }
    }

    @Test
    fun `HTML page contains product names`() {
        val response =
            RestAssured
                .get("/products")

        val html = response.body.asString()

        MOCK_PRODUCTS.forEach {
            assertThat(html).contains(it.name)
        }
    }

    @Test
    fun `HTML page does not contain product name`() {
        val response =
            RestAssured
                .get("/products")

        val html = response.body.asString()

        assertThat(html).doesNotContain("chocolate")
    }

    companion object {
        val MOCK_PRODUCTS = listOf(
            Product(name = "melon ice cream", price = 2.33, imageUrl = "link-1.jpg"),
            Product(name ="lemon ice cream", price = 1.50, imageUrl = "link-2.jpg"),
            Product(name = "vanilla ice cream", price = 3.09, imageUrl = "link-3.jpg")
        )
    }
}

