package pt.ulusofona.tms.dao

import jakarta.persistence.*
import java.util.Date

@Entity
data class Comentarios (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0,

    @Column(name = "author", nullable = false)
    var author: String = "",

    @Column(name = "data", nullable = false)
    var data: String = "",

    @Column(name = "Comentário", nullable = false)
    var comentario: String = "",
)
