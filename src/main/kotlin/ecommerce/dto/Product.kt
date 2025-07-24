package ecommerce.dto

import ecommerce.utiles.Constants
import ecommerce.validator.UniqueProductName
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Positive
import jakarta.validation.constraints.Size

class Product(
    var id: Long? = null,
    @field:NotBlank(message = Constants.ERR_NAME_NOT_BLANK)
    @field:Pattern(
        regexp = """^[\w\s()\[\]+\-&/_]*$""",
        message = Constants.ERR_NAME_REGEX,
    )
    @field:Size(max = 15, message = Constants.ERR_NAME_SIZE)
    @field:UniqueProductName(message = Constants.ERR_NAME_UNIQUE)
    var name: String,
    @field:Positive(message = Constants.ERR_PRICE_POSITIVE)
    var price: Double,
    @field:Pattern(regexp = """^(http://|https://).+""", message = Constants.ERR_URL_REGEX)
    var imageUrl: String,
)
