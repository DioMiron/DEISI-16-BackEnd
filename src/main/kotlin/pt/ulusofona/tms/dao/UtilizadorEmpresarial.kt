package pt.ulusofona.tms.dao

import jakarta.persistence.*
import java.util.Date

@Entity
data class UtilizadorEmpresarial (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto incrementing ID
    @Column(name = "id", nullable = false)
    val id: Int = 0,

    @Column(name = "Email", nullable = false)
    var email: String = "",

    @Column(name = "Nome", nullable = false)
    var name: String = "",

    @Column(name = "Utilizador", nullable = false)
    var user: String = "",

    @Column(name = "gender", nullable = false)
    var gender: String = "",

    @Column(name = "profissao", nullable = false)
    var profissao: String = "",

    @Column(name = "Contacto Telefónico", nullable = false)
    var contacto: String = "",

    @Column(name = "age", nullable = false)
    var age: Int? = 0,

    @Column(name = "Password", nullable = false)
    var password: String = "",

    @Column(name = "Empresa", nullable = true)
    var empresa: String = "",
)
