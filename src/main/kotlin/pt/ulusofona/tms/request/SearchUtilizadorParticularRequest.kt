package pt.ulusofona.tms.request

import pt.ulusofona.tms.dao.Competencias
import pt.ulusofona.tms.dao.ExperienciaLaboral
import pt.ulusofona.tms.dao.FormacaoAcademica

// Create
data class CreateUtilizadorParticularRequest(
    val id: Int,
    val email: String,
    val name: String,
    val username: String,
    val gender: String,
    val profissao: String,
    val contacto: String,
    val idade: Int,
    val password: String
)

data class AddUtilizadorParticularAcademicSkills(val id: Int, val academic: FormacaoAcademica)
data class AddUtilizadorParticularSkills(val id: Int, val skill: Competencias)
data class AddUtilizadorParticularExperiencias(val id: Int, val experiencia: ExperienciaLaboral)


// Edit classes
data class EditUtilizadorParticular(
    val id: Int,
    val email: String,
    val name: String,
    val username: String,
    val gender: String,
    val profissao: String,
    val contacto: String,
    val idade: Int,
    val password: String
)

