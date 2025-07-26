package ecommerce.service

import ecommerce.dto.Member
import ecommerce.exception.DuplicateMemberEmailException
import ecommerce.repository.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val jdbc: MemberRepository
) {
    fun validateUniqueName(member: Member) {
        when {
            jdbc.existsByEmail(member.email) -> throw DuplicateMemberEmailException(
                "Member with email '${member.email}' already exists.",
            )
        }
    }
}
