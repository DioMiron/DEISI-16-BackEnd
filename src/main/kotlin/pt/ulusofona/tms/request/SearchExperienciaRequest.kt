package pt.ulusofona.tms.request

import pt.ulusofona.tms.dao.*


// Search Skill
data class SearchExperienciaRequest(val id: Int, val skill: Competencias)

// Create Skill
data class CreateExperienciaRequest(
    val id: Int,
    val cidade: String,
    val profissao: String,
    val nomeempresa: String,
    val duracao: String,
    val author: UtilizadorParticular
)

// Update Skill
data class UpdateExperienciaRequest(
    val id: Int,
    val cidade: String,
    val profissao: String,
    val nomeempresa: String,
    val duracao: String
)

