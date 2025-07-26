package ecommerce.service

import ecommerce.auth.JwtTokenProvider
import ecommerce.dto.Member
import ecommerce.dto.TokenRequest
import ecommerce.dto.TokenResponse
import ecommerce.exception.AuthorizationException
import ecommerce.repository.JdbcMemberRepository
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val jwtTokenProvider: JwtTokenProvider,
    private val jdbcMemberRepository: JdbcMemberRepository
) {
    fun register(tokenRequest: TokenRequest): TokenResponse {
        val member = Member(email = tokenRequest.email, password = tokenRequest.password)
        jdbcMemberRepository.create(member)
        return TokenResponse(jwtTokenProvider.createToken(member.email))
    }

    fun login(tokenRequest: TokenRequest): TokenResponse {
        val member =
            jdbcMemberRepository.findByEmail(tokenRequest.email)
                ?: throw AuthorizationException("Member not found")
        if (member.password != tokenRequest.password) {
            throw AuthorizationException("Invalid password")
        }
        val accessToken = jwtTokenProvider.createToken(member.email)
        return TokenResponse(accessToken)
    }
}
