package pt.ulusofona.tms.repository;
import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.UtilizadorParticular

interface UtilizadorParticularRepository : JpaRepository<UtilizadorParticular, Int> {
    fun findUtilizadorParticularById(id: Int):UtilizadorParticular?
    fun findUtilizadorParticularByName(name: String):UtilizadorParticular?
    fun findUtilizadorParticularByUsername(username: String):UtilizadorParticular?
    fun findUtilizadorParticularByEmail(email: String):UtilizadorParticular?
}