package pt.ulusofona.tms.dao

import jakarta.persistence.*

@Entity
data class Competencias (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0,

    @Column(name = "Nome", nullable = false)
    val nome: String = "",

    @Column(name = "tipo", nullable = false)
    val type: String = "",

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    var author: UtilizadorParticular? = null
)
