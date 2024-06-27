package pt.ulusofona.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.Competencias
import pt.ulusofona.tms.dao.FormacaoAcademica
import pt.ulusofona.tms.dao.UtilizadorParticular


interface CompetenciasRepository : JpaRepository<Competencias, Int> {
    fun findCompetenciasById(id: Int): Competencias?
    fun findCompetenciasByNome(nome: String): Competencias?
    fun findCompetenciasByAuthor(author: UtilizadorParticular): List<Competencias>
    fun findByNomeAndType(nome: String, type: String): Competencias?
}