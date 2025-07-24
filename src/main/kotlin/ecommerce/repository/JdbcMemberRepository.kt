package ecommerce.repository

import ecommerce.dto.Member
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper

class JdbcMemberRepository(private val jdbcTemplate: JdbcTemplate) : MemberRepository {
    private val rowMapper = RowMapper<Member> { rs, _ ->
        Member(
            id = rs.getLong("id"),
            email = rs.getString("email"),
            password = rs.getString("password"),
            role = rs.getString("role")
        )
    }

    override fun create(member: Member): Long? {
        jdbcTemplate.update(
            "INSERT INTO members (email, password, role) VALUES (?, ?, ?)",
            member.email, member.password, member.role
        )
        return jdbcTemplate.queryForObject("SELECT MAX(id) FROM members", Long::class.java)
    }

    override fun findByEmail(email: String): Member? {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM members WHERE email = ?",
                arrayOf(email),
                rowMapper
            )
    }

    override fun existsByEmail(email: String): Boolean {
        val count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM members WHERE email = ?",
            arrayOf(email),
            Long::class.java
        ) ?: 0
        return count > 0
    }
}
