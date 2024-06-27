package pt.ulusofona.tms.dao

import jakarta.persistence.*
import java.util.Date

@Entity
data class ExperienciaLaboral (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Int = 0,

    @Column(name = "Cidade", nullable = false)
    val cidade: String = "",

    @Column(name = "Profissao", nullable = false)
    val profissao: String = "",

    @Column(name = "Empresa", nullable = false)
    val nomeEmpresa: String = "",

    @Column(name = "Duração da Experiência", nullable = false)
    val duracaoDaExperiencia: String = "",

    @ManyToOne
    @JoinColumn(name = "author", nullable = false)
    var author: UtilizadorParticular? = null,
)
