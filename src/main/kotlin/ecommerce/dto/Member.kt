package ecommerce.dto

import ecommerce.utiles.Constants
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class Member(
    val id: Long? = null,
    @field:Email(message = Constants.ERR_EMAIL_FORMAT)
    @field:NotBlank(message = Constants.ERR_EMAIL_BLANK)
    val email: String,
    @field:NotBlank(message = Constants.ERR_PASSWORD)
    val password: String,
    val role: String = "USER"
)
