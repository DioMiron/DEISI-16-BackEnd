package pt.ulusofona.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.Competencias
import pt.ulusofona.tms.dao.ExperienciaLaboral
import pt.ulusofona.tms.dao.FormacaoAcademica
import pt.ulusofona.tms.dao.UtilizadorParticular


interface ExperienciaRepository : JpaRepository<ExperienciaLaboral, Int> {
    fun findExperienciaById(id: Int): ExperienciaLaboral?
    fun findExperienciaByProfissao(profissao: String): ExperienciaLaboral?
    fun findExperienciaByNomeEmpresa(nomeempresa: String): List<ExperienciaLaboral>?
    fun findExperienciaByAuthor(author: UtilizadorParticular): List<ExperienciaLaboral>
    fun findByCidadeAndProfissaoAndNomeEmpresa(cidade: String, profissao: String, nomeempresa: String): ExperienciaLaboral?
}