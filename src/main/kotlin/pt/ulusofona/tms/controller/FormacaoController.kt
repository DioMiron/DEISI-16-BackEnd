package pt.ulusofona.tms.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.ulusofona.tms.dao.FormacaoAcademica
import pt.ulusofona.tms.repository.FormacaoRepository
import pt.ulusofona.tms.repository.UtilizadorParticularRepository
import pt.ulusofona.tms.request.CreateFormacaoRequest


@RestController
@RequestMapping("/api/formacoes")
class FormacaoController(
    private val formacaoRepository: FormacaoRepository,
    private val utilizadorParticularRepository: UtilizadorParticularRepository
) {
    // Gives all the Formations
    @GetMapping("/list", produces = ["application/json;charset=UTF-8"])
    fun list(): List<FormacaoAcademica> = formacaoRepository.findAll()

    // Get a Formation by id
    @GetMapping("/searchId/{id}")
    fun getFormacaoById(@PathVariable id: Int): ResponseEntity<Any> {
        val formacao = formacaoRepository.findById(id)
        return if (formacao.isPresent) {
            ResponseEntity.ok(formacao.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Formacao não encontrada.")
        }
    }

    // Get a Formation by author
    @GetMapping("/searchByAuthor/{username}")
    fun getFormacaoByAuthor(@PathVariable username: String): ResponseEntity<Any> {
        val author = utilizadorParticularRepository.findUtilizadorParticularByUsername(username)
        return if (author != null) {
            val formacoes = formacaoRepository.findFormacaoByAuthor(author)
            ResponseEntity.ok(formacoes)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author não encontrado.")
        }
    }

    // Adding a new Formation
    @PostMapping("/add")
    fun createFormacao(@RequestBody formacao: CreateFormacaoRequest): ResponseEntity<Any> {
        // Check if a formation with the same attributes already exists
        val existingFormacao = formacaoRepository.findByNomeAndTipoformacaoAndInstituto(
            formacao.nome, formacao.tipoformacao, formacao.instituto
        )

        if (existingFormacao != null) {
            // Formation with the same attributes already exists, return a conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Formation with these details already exists.")
        }

        // Create FormacaoAcademica instance
        var formacaoDB = FormacaoAcademica(
            formacao.id, formacao.nome, formacao.tipoformacao, formacao.instituto, formacao.duracao,
            formacao.author
        )

        // Check if there is a formation with the same ID exists
        if (formacaoRepository.existsById(formacao.id)) {
            // Change the ID (you can implement a method to generate a new unique ID if needed)
            formacaoDB = formacaoDB.copy(id = 0) // Assuming 0 means auto-generated ID
        }

        // Save the FormacaoAcademica object
        val savedFormacao = formacaoRepository.save(formacaoDB)

        return ResponseEntity.status(HttpStatus.CREATED).body(savedFormacao)
    }

    // Delete function
    @DeleteMapping("/delete/byId/{id}")
    fun deleteFormacaoById(@PathVariable id: Int): ResponseEntity<Any> {
        return try {
            val formacao = formacaoRepository.findById(id)
            if (formacao.isPresent) {
                formacaoRepository.delete(formacao.get())
                ResponseEntity.ok("Formação apagado com sucesso")
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Formação não encontrada")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Please try again later.")
        }
    }
}