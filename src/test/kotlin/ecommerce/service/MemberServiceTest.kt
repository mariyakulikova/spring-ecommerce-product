package ecommerce.service

import ecommerce.model.Member
import ecommerce.exception.DuplicateMemberEmailException
import ecommerce.repository.MemberRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MemberServiceTest {
    private lateinit var memberRepository: MemberRepository
    private lateinit var memberService: MemberService

    @BeforeEach
    fun setUp() {
        memberRepository = mockk()
        memberService = MemberService(memberRepository)
    }

    @Test
    fun `should throw exception when email already exists`() {
        val member = Member(email = "test@email.com", password = "1234")

        every { memberRepository.existsByEmail(member.email) } returns true

        assertThrows(DuplicateMemberEmailException::class.java) {
            memberService.validateUniqueName(member)
        }
    }

    @Test
    fun `should not throw when email is unique`() {
        val member = Member(email = "unique@email.com", password = "1234")

        every { memberRepository.existsByEmail(member.email) } returns false

        memberService.validateUniqueName(member)
    }
}
