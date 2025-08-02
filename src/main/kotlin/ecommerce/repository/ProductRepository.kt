package ecommerce.repository

import ecommerce.model.Product

interface ProductRepository {
    fun getAll(): List<Product>

    fun create(product: Product): Long?

    fun update(
        id: Long,
        product: Product,
    )

    fun delete(id: Long)

    fun existsByName(product: Product): Boolean
}
