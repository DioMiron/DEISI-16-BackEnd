package pt.ulusofona.tms.repository;

import org.springframework.data.jpa.repository.JpaRepository
import pt.ulusofona.tms.dao.Comentarios
import pt.ulusofona.tms.dao.UtilizadorParticular


interface ComentariosRepository : JpaRepository<Comentarios, Int> {
    fun findCommentById(id: Int): Comentarios?
    fun findCommentByAuthor(author: String): List<Comentarios>
    fun findByAuthorAndComentario(author: String, comment: String) : Comentarios?
}