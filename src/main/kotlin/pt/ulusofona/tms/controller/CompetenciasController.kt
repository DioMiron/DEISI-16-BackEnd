package pt.ulusofona.tms.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.ulusofona.tms.dao.Competencias
import pt.ulusofona.tms.dao.FormacaoAcademica
import pt.ulusofona.tms.repository.CompetenciasRepository
import pt.ulusofona.tms.repository.FormacaoRepository
import pt.ulusofona.tms.repository.UtilizadorParticularRepository
import pt.ulusofona.tms.request.CreateFormacaoRequest
import pt.ulusofona.tms.request.CreateSkillRequest


@RestController
@RequestMapping("/api/competencias")
class CompetenciasController(
    private val competenciasRepository: CompetenciasRepository,
    private val utilizadorParticularRepository: UtilizadorParticularRepository
) {
    // Gives all the Competencias
    @GetMapping("/list", produces = ["application/json;charset=UTF-8"])
    fun list(): List<Competencias> = competenciasRepository.findAll()

    // Get a Competencia by id
    @GetMapping("/searchId/{id}")
    fun getCompetenciaById(@PathVariable id: Int): ResponseEntity<Any> {
        val competencia = competenciasRepository.findById(id)
        return if (competencia.isPresent) {
            ResponseEntity.ok(competencia.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competencia não encontrada.")
        }
    }

    // Get a Formation by author
    @GetMapping("/searchByAuthor/{username}")
    fun getCompetenciaByAuthor(@PathVariable username: String): ResponseEntity<Any> {
        val author = utilizadorParticularRepository.findUtilizadorParticularByUsername(username)
        return if (author != null) {
            val competencias = competenciasRepository.findCompetenciasByAuthor(author)
            ResponseEntity.ok(competencias)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author não encontrado.")
        }
    }

    // Adding a new Formation
    // Adding a new Formation
    @PostMapping("/add")
    fun createCompetencia(@RequestBody competencia: CreateSkillRequest): ResponseEntity<Any> {
        // Check if a skill with the same attributes already exists
        val existingCompetencia = competenciasRepository.findByNomeAndType(
            competencia.nome, competencia.type
        )

        if (existingCompetencia != null) {
            // Skill with the same attributes already exists, return a conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Skill with these details already exists.")
        }

        // Create Competencias instance
        var competenciaDB = Competencias(
            competencia.id, competencia.nome, competencia.type, competencia.author
        )

        // Check if there is a skill with the same ID exists
        if (competenciasRepository.existsById(competencia.id)) {
            // Change the ID (you can implement a method to generate a new unique ID if needed)
            competenciaDB = competenciaDB.copy(id = 0) // Assuming 0 means auto-generated ID
        }

        // Save the Competencias object
        val savedCompetencia = competenciasRepository.save(competenciaDB)

        return ResponseEntity.status(HttpStatus.CREATED).body(savedCompetencia)
    }


    // Delete function
    @DeleteMapping("/delete/byId/{id}")
    fun deleteCompetenciaById(@PathVariable id: Int): ResponseEntity<Any> {
        return try {
            val formacao = competenciasRepository.findById(id)
            if (formacao.isPresent) {
                competenciasRepository.delete(formacao.get())
                ResponseEntity.ok("Competencia apagada com sucesso")
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Competencia não encontrada")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Please try again later.")
        }
    }
}