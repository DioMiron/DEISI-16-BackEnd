package pt.ulusofona.tms.dao

import jakarta.persistence.*

@Entity
data class FormacaoAcademica (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    val id: Int = 0,

    @Column(name = "Nome", nullable = false)
    val nome: String = "",

    @Column(name = "Formação", nullable = false)
    val tipoformacao: String = "",

    @Column(name = "Instituto", nullable = true)
    val instituto: String = "",

    @Column(name = "data", nullable = false)
    val duracao: String = "",

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    var author: UtilizadorParticular? = null,
)

