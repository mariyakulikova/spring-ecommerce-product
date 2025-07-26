package ecommerce.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IllegalArgumentException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException::class)
    fun exceptionHandler(e: IllegalArgumentException): ResponseEntity<String> {
        println("IllegalArgumentException occurred: " + e.message)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.message)
    }

    @ExceptionHandler(DuplicateProductNameException::class)
    fun handleDuplicateName(e: DuplicateProductNameException): ResponseEntity<String> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.message)
    }

//    @ExceptionHandler(MethodArgumentNotValidException::class)
//    fun handleValidationException(ex: MethodArgumentNotValidException): ResponseEntity<String> {
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).build()
//    }
}
