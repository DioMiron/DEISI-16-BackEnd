package pt.ulusofona.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.FormacaoAcademica
import pt.ulusofona.tms.dao.UtilizadorParticular


interface FormacaoRepository : JpaRepository<FormacaoAcademica, Int> {
    fun findFormacaoById(id: Int): FormacaoAcademica?
    fun findFormacaoByAuthor(author: UtilizadorParticular): List<FormacaoAcademica>
    fun findByNomeAndTipoformacaoAndInstituto(nome: String, tipoformacao: String, instituto: String): FormacaoAcademica?

}