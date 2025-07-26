package ecommerce.repository

import ecommerce.dto.Product
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository
import ecommerce.exception.NotFoundException

@Repository
class JdbcProductRepository(private val jdbc: JdbcTemplate) : ProductRepository {
    private val rowMapper =
        RowMapper<Product> { rs, _ ->
            Product(
                id = rs.getLong("id"),
                name = rs.getString("name"),
                price = rs.getDouble("price"),
                imageUrl = rs.getString("image_url"),
            )
        }

    override fun getAll(): List<Product> {
        return jdbc.query("SELECT * FROM products", rowMapper)
    }

    override fun create(product: Product): Long {
        jdbc.update(
            "INSERT INTO products (name, price, image_url) VALUES (?, ?, ?)",
            product.name,
            product.price,
            product.imageUrl,
        )
        return jdbc.queryForObject("SELECT MAX(id) FROM products", Long::class.java)!!
    }

    override fun update(
        id: Long,
        product: Product,
    ) {
        jdbc.update(
            "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?",
            product.name, product.price, product.imageUrl, id,
        ).takeIf { it > 0 } ?: throw NotFoundException("Product with id $id not found")
    }

    override fun delete(id: Long) {
        jdbc.update("DELETE FROM products WHERE id = ?", id)
            .takeIf { it > 0 }
            ?: throw NotFoundException("Product with id $id not found")
    }

    override fun existsByName(product: Product): Boolean {
        val rows = jdbc.query(
            "SELECT id FROM products WHERE name = ?",
            { rs, _ -> rs.getLong("id") },
            product.name,
        )
        return rows.isNotEmpty()
    }
}
