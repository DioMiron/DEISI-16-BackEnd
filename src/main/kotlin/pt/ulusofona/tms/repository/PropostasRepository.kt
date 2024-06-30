package pt.ulusofona.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.Propostas
import pt.ulusofona.tms.dao.UtilizadorParticular


interface PropostasRepository : JpaRepository<Propostas, Int> {
    fun findJobById(id: Int): Propostas?
    fun findByCandidate(candidate : UtilizadorParticular?): List<Propostas>
    fun findByAreaAndDescricao(area: String, descricao: String): Propostas?
}