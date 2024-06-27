package pt.ulusofona.tms.request

import pt.ulusofona.tms.dao.UtilizadorParticular

// Search Formacao
data class SearchFormacaoRequest(val id: Int)
data class SeachFormacaoOfUser(val user: UtilizadorParticular)

// Create Formacao
data class CreateFormacaoRequest(
    val id: Int,
    val nome: String,
    val tipoformacao: String,
    val instituto: String,
    val duracao: String,
    val author: UtilizadorParticular
)

