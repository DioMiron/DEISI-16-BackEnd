package pt.ulusofona.tms.dao

import jakarta.persistence.*

@Entity
data class Propostas(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    var id: Int = 0,

    @ManyToOne
    @JoinColumn(name = "Comentário", nullable = true)
    var comment: Comentarios? = null,

    @Column(name = "Área", nullable = false)
    var area: String = "",

    @Column(name = "Description", nullable = false)
    var descricao: String = "",

    @Column(name = "skill", nullable = false)
    var skillsRequired: String = "",

    @ManyToOne
    @JoinColumn(name = "Author", nullable = true)
    var author: UtilizadorEmpresarial? = null,

    @ManyToOne
    @JoinColumn(name = "Candidato", nullable = true)
    var candidate: UtilizadorParticular? = null,
)

