package pt.ulusofona.tms.dao

import jakarta.persistence.*

@Entity
data class Permissoes (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    val id: Int = 0,

    @Column(name = "Roles", nullable = false)
    val roles: String = ""
)
