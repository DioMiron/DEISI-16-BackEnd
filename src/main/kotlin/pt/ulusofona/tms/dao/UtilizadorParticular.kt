package pt.ulusofona.tms.dao

import jakarta.persistence.*

@Entity
data class UtilizadorParticular (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0,

    @Column(name = "priv_email", nullable = false)
    var email: String = "",

    @Column(name = "priv_name", nullable = false)
    var name: String = "",

    @Column(name = "priv_username", nullable = false)
    var username: String = "",

    @Column(name = "priv_gender", nullable = false)
    var gender: String = "",

    @Column(name = "priv_job")
    var profissao: String = "",

    @Column(name = "priv_number", nullable = false)
    var numeroDeTelemovel: String = "",

    @Column(name = "priv_age", nullable = false)
    var age: Int = 0,

    @Column(name = "priv_password", nullable = false)
    var password: String = "",
)
