package ecommerce

import ecommerce.dto.Product
import io.restassured.RestAssured
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class SSRTest() {
    val products =
        listOf(
            Product(
                id = 1L,
                name = "vanilla ice cream",
                price = 1.99,
                imageUrl = "https://laurenslatest.com/wp-content/uploads/2020/08/vanilla-ice-cream-5-copy-360x361.jpg",
            ),
            Product(
                id = 2L,
                name = "pistachio ice cream",
                price = 2.49,
                imageUrl = "https://greenhealthycooking.com/wp-content/uploads/2017/06/Pistachio-Ice-Cream-Photo.jpg",
            ),
            Product(
                id = 3L,
                name = "chocolate ice cream",
                price = 1.49,
                imageUrl = "https://www.cravethegood.com/wp-content/uploads/2021/04/sous-vide-chocolate-ice-cream-15.jpg",
            ),
        )

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate

    @BeforeEach
    fun setUp() {
        jdbcTemplate.execute("DROP TABLE IF EXISTS cart_items")
        jdbcTemplate.execute("DROP TABLE products IF EXISTS")
        jdbcTemplate.execute(
            "CREATE TABLE products(" +
                "id BIGINT AUTO_INCREMENT, name VARCHAR(255) NOT NULL, price DOUBLE NOT NULL, image_url VARCHAR(512) NOT NULL)",
        )

        jdbcTemplate.batchUpdate(
            "INSERT INTO products(name, price, image_url) VALUES (?, ?, ?)",
            products,
            products.size,
        ) { ps, product ->
            ps.setString(1, product.name)
            ps.setDouble(2, product.price)
            ps.setString(3, product.imageUrl)
        }
    }

    @Test
    fun `HTML page contains product names`() {
        val response =
            RestAssured
                .get("/products")

        val html = response.body.asString()

        products.forEach {
            assertThat(html).contains(it.name)
        }
    }
}
