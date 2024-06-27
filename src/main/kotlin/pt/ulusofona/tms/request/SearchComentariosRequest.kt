package pt.ulusofona.tms.request


data class SearchComentariosRequest(val id: Int, val comentario: String)

// Create
data class CreateNewComentariosRequest(val username: String, val id: Int, val comentario: String, val datetime: String)

