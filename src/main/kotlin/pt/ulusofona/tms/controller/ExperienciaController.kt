package pt.ulusofona.tms.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.ulusofona.tms.dao.ExperienciaLaboral
import pt.ulusofona.tms.repository.ExperienciaRepository
import pt.ulusofona.tms.repository.UtilizadorParticularRepository
import pt.ulusofona.tms.request.CreateExperienciaRequest

@RestController
@RequestMapping("/api/experiencias")
class ExperienciaController(
    private val experienciaRepository: ExperienciaRepository,
    private val utilizadorParticularRepository: UtilizadorParticularRepository
) {
    // Get all Experiencias
    @GetMapping("/list", produces = ["application/json;charset=UTF-8"])
    fun list(): List<ExperienciaLaboral> = experienciaRepository.findAll()

    // Get a Experiencia by id
    @GetMapping("/searchId/{id}")
    fun getExperienciaById(@PathVariable id: Int): ResponseEntity<Any> {
        val experiencia = experienciaRepository.findById(id)
        return if (experiencia.isPresent) {
            ResponseEntity.ok(experiencia.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Experiencia não encontrada.")
        }
    }

    // Get a Experiencia by author
    @GetMapping("/searchByAuthor/{username}")
    fun getExperienciaByAuthor(@PathVariable username: String): ResponseEntity<Any> {
        val author = utilizadorParticularRepository.findUtilizadorParticularByUsername(username)
        return if (author != null) {
            val experiencia = experienciaRepository.findExperienciaByAuthor(author)
            ResponseEntity.ok(experiencia)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Author não encontrado.")
        }
    }

    // Get a Experiencia by company name
    @GetMapping("/searchByCompany/{nomeempresa}")
    fun getExperienciaByNomeEmpresa(@PathVariable nomeempresa: String): ResponseEntity<Any> {
        val experiencias = experienciaRepository.findExperienciaByNomeEmpresa(nomeempresa)
        return if (!experiencias.isNullOrEmpty()) {
            ResponseEntity.ok(experiencias)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Nenhuma experiência encontrada para a empresa $nomeempresa.")
        }
    }

    @PostMapping("/add")
    fun createExperiencia(@RequestBody experiencia: CreateExperienciaRequest): ResponseEntity<Any> {
        // Check if a formation with the same attributes already exists
        val existingFormacao = experienciaRepository.findByCidadeAndProfissaoAndNomeEmpresa(
            experiencia.cidade, experiencia.profissao, experiencia.nomeempresa
        )

        if (existingFormacao != null) {
            // Formation with the same attributes already exists, return a conflict response
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Experiencia with these details already exists.")
        }

        // Create FormacaoAcademica instance
        var experienciaDB = ExperienciaLaboral(
            experiencia.id,
            experiencia.cidade,
            experiencia.profissao,
            experiencia.nomeempresa,
            experiencia.duracao,
            experiencia.author
        )

        // Check if there is a formation with the same ID exists
        if (experienciaRepository.existsById(experiencia.id)) {
            // Change the ID (you can implement a method to generate a new unique ID if needed)
            experienciaDB = experienciaDB.copy(id = 0) // Assuming 0 means auto-generated ID
        }

        // Save the FormacaoAcademica object
        val savedFormacao = experienciaRepository.save(experienciaDB)

        return ResponseEntity.status(HttpStatus.CREATED).body(savedFormacao)
    }

    // Delete function
    @DeleteMapping("/delete/byId/{id}")
    fun deleteExperienciaById(@PathVariable id: Int): ResponseEntity<Any> {
        return try {
            val experiencia = experienciaRepository.findById(id)
            if (experiencia.isPresent) {
                experienciaRepository.delete(experiencia.get())
                ResponseEntity.ok("Experiencia apagada com sucesso")
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Experiencia não encontrada")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Internal server error. Please try again later.")
        }
    }
}
