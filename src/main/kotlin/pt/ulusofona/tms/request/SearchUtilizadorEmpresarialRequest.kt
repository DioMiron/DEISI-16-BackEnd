package pt.ulusofona.tms.request

// Create
data class CreateUtilizadorEmpresarialRequest(
    val id: Int,
    val email: String,
    val name: String,
    val username: String,
    val gender: String,
    val profissao: String,
    val contacto: String,
    val age: Int,
    val password: String,
    val empresa: String
)

// Edit
data class EditUtilizadorEmpresarial(
    val id: Int,
    val email: String,
    val name: String,
    val username: String,
    val gender: String,
    val profissao: String,
    val contacto: String,
    val age: Int,
    val password: String,
    val empresa: String
)

