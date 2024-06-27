package pt.ulusofona.tms.repository

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.UtilizadorEmpresarial

interface UtilizadorEmpresarialRepository : JpaRepository<UtilizadorEmpresarial, Int> {
    fun findUtilizadorEmpresarialById(id: Int): UtilizadorEmpresarial?
    fun findUtilizadorEmpresarialsByname(name: String): UtilizadorEmpresarial?
    fun findUtilizadorEmpresarialByUser(username: String):UtilizadorEmpresarial?
    fun findUtilizadorEmpresarialsByEmail(email: String): UtilizadorEmpresarial?
}
