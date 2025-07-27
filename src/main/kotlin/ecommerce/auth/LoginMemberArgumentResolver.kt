package ecommerce.auth

import ecommerce.dto.Member
import ecommerce.exception.AuthorizationException
import ecommerce.repository.MemberRepository
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class LoginMemberArgumentResolver(
    private val tokenProvider: JwtTokenProvider,
    private val memberRepository: MemberRepository,
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(LoginMember::class.java)
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?,
    ): Member {
        val token =
            webRequest.getHeader("Authorization")?.removePrefix("Bearer ")
                ?: throw AuthorizationException("Authorization header is missing")
        if (!tokenProvider.validateToken(token)) throw AuthorizationException("Invalid token")
        val email = tokenProvider.getPayload(token)
        val member =
            memberRepository.findByEmail(email) ?: throw AuthorizationException(
                "No authenticated member found",
            )
        return member
    }
}
