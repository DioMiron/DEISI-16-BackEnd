package pt.ulusofona.tms.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import pt.ulusofona.tms.dao.Propostas
import pt.ulusofona.tms.repository.PropostasRepository
import pt.ulusofona.tms.request.CreatePropostaRequest
import pt.ulusofona.tms.request.SearchPropostasRequest
import pt.ulusofona.tms.request.UpdatePropostaRequest

@RestController
@RequestMapping("/api/propostas")
class PropostasController(private val propostasRepository: PropostasRepository) {
    // Gives all the Jobs
    @GetMapping("/list", produces = ["application/json;charset=UTF-8"])
    fun list(model: ModelMap): List<Propostas> = propostasRepository.findAll()

    // Get by id.
    @GetMapping("/search/{id}")
    fun getPropostaById(@PathVariable id: Int): ResponseEntity<Any> {
        val proposta = propostasRepository.findJobById(id)
        return if (proposta != null) {
            ResponseEntity(proposta, HttpStatus.OK)
        } else {
            ResponseEntity("Proposta not found", HttpStatus.NOT_FOUND)
        }
    }

    // Add a new Job.
    @PostMapping("/add")
    fun addProposta(@RequestBody request: CreatePropostaRequest): ResponseEntity<Any> {
        try {
            // Check for existing Proposta based on area and descricao
            val existingProposta = propostasRepository.findByAreaAndDescricao(request.area, request.descricao)
            if (existingProposta != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Job proposal with the same area and description already exists.")
            }

            // Check if the author object is null
            if (request.author == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Author details cannot be null.")
            }

            // Create Propostas entity from request
            var newProposta = Propostas(
                id = request.id,
                comment = null, // Initialize comment with null
                candidate = null, // Initialize candidate with null
                area = request.area,
                descricao = request.descricao,
                author = request.author
            )

            // Check if a job with the same ID exists
            if (propostasRepository.existsById(request.id)) {
                // Change the ID (generate a new unique ID if needed)
                newProposta = newProposta.copy(id = 0) // Assuming 0 means auto-generated ID
            }

            // Save new Propostas entity
            val savedProposta = propostasRepository.save(newProposta)

            return ResponseEntity(savedProposta, HttpStatus.CREATED)
        } catch (ex: Exception) {
            // Log the exception for debugging purposes
            ex.printStackTrace()

            // Return a more informative error response
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while processing the request.")
        }
    }

    // Update the job, getting is id.
    @PutMapping("/update")
    fun updateProposta(@RequestBody updateProposta: UpdatePropostaRequest): ResponseEntity<Propostas> {
        val propostaOptional = propostasRepository.findById(updateProposta.id)

        return if (propostaOptional.isPresent) {
            val propostaToUpdate = propostaOptional.get()

            // Update properties based on the request body
            propostaToUpdate.area = updateProposta.area
            propostaToUpdate.descricao = updateProposta.descricao
            propostaToUpdate.skillsRequired = updateProposta.skills

            // Save the updated proposta
            val updatedProposta = propostasRepository.save(propostaToUpdate)
            ResponseEntity(updatedProposta, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    // Delete a certain Job.
    @DeleteMapping("/delete/{id}")
    fun deleteProposta(@PathVariable id: SearchPropostasRequest): ResponseEntity<Any> {
        return if (propostasRepository.existsById(id.id)) {
            propostasRepository.deleteById(id.id)
            ResponseEntity(HttpStatus.OK)
        } else {
            ResponseEntity("Proposta not found", HttpStatus.NOT_FOUND)
        }
    }
}