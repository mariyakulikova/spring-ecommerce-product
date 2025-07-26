package ecommerce.dto

data class Member(
    val id: Long? = null,
    val email: String,
    val password: String,
    val role: String = "USER"
) {
    init {
        require(email.isNotBlank()) { "Email cannot be blank" }
        require(password.isNotBlank()) { "Password cannot be blank" }
        require(isValidEmail(email)) { "Invalid email format: $email" }
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return email.matches(emailRegex)
    }
}
