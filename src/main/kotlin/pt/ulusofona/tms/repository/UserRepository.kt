package pt.ulusofona.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.User

interface UserRepository : JpaRepository<User, Int> {

    fun findByEmail(email: String): User?
    fun existsByEmail(email: String): Boolean
    fun existsByEmailToken(emailToken: String): Boolean
    fun findByEmailToken(emailToken: String): User?
    fun findByResetPasswordToken(resetPasswordToken: String): User?
}