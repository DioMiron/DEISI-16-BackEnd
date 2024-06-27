package pt.ulusofona.tms.request

import pt.ulusofona.tms.dao.Propostas
import pt.ulusofona.tms.dao.UtilizadorEmpresarial


// Search Job
data class SearchPropostasRequest(val id: Int, val proposta: Propostas)

// Create Job
data class CreatePropostaRequest(
    val id: Int,
    val area: String,
    val descricao: String,
    val skills: String,
    val author: UtilizadorEmpresarial
)

// Update Job
data class UpdatePropostaRequest(val id: Int, val area: String, val descricao: String, val skills: String)

