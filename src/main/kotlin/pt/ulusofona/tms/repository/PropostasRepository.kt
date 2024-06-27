package pt.ulusofona.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.Propostas


interface PropostasRepository : JpaRepository<Propostas, Int> {
    fun findJobById(id: Int): Propostas?
    fun findByAreaAndDescricao(area: String, descricao: String): Propostas?
}