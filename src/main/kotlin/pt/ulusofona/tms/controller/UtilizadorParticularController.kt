package pt.ulusofona.tms.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import pt.ulusofona.tms.dao.UtilizadorParticular
import pt.ulusofona.tms.repository.*
import pt.ulusofona.tms.request.*

@RestController
@RequestMapping("/api/userParticular")
class UtilizadorParticularController(
    val utilizadorParticularRepository: UtilizadorParticularRepository,
    val formacaoRepository: FormacaoRepository,
    val experienciaRepository: ExperienciaRepository,
    val competenciasRepository: CompetenciasRepository,
    val propostasRepository: PropostasRepository,
    val comentariosRepository: ComentariosRepository
) {

    @GetMapping("/list", produces = ["application/json;charset=UTF-8"])
    fun list(model: ModelMap): List<UtilizadorParticular> = utilizadorParticularRepository.findAll()

    @GetMapping("/searchId/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<Any> {
        val user = utilizadorParticularRepository.findById(id)
        return if (user.isPresent) {
            ResponseEntity.ok(user.get())
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado")
        }
    }

    @GetMapping("/searchName/{name}")
    fun getUserByName(@PathVariable name: String): ResponseEntity<Any> {
        val user = utilizadorParticularRepository.findUtilizadorParticularByName(name)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado")
        }
    }

    @GetMapping("/searchUserName/{username}")
    fun getUserByUserName(@PathVariable username: String): ResponseEntity<Any> {
        val user = utilizadorParticularRepository.findUtilizadorParticularByUsername(username)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado")
        }
    }

    @PostMapping("/add")
    fun createUser(@RequestBody user: CreateUtilizadorParticularRequest): ResponseEntity<Any> {
        // Check if a user with the same username exists
        val existingUser = utilizadorParticularRepository.findUtilizadorParticularByUsername(user.username)
        if (existingUser != null) {
            // User with the same username already exists, return a response with conflict status
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username '${user.username}' is already taken.")
        }

        // Check if a user with the same email exists
        val existingEmailUser = utilizadorParticularRepository.findUtilizadorParticularByEmail(user.email)
        if (existingEmailUser != null) {
            // User with the same email already exists, return a response with conflict status
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email '${user.email}' is already registered.")
        }

        // No existing user with the same username, proceed to save
        var utilizadorDB = UtilizadorParticular(
            user.id, user.email, user.name, user.username, user.gender, user.profissao,
            user.contacto, user.idade, user.password
        )

        // Check if a user with the same ID exists
        if (utilizadorParticularRepository.existsById(user.id)) {
            // Change the ID (you can implement a method to generate a new unique ID if needed)
            utilizadorDB = utilizadorDB.copy(id = 0) // Assuming 0 means auto-generated ID
        }

        val savedUtilizador = utilizadorParticularRepository.save(utilizadorDB)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUtilizador)
    }

    @DeleteMapping("/delete/byId/{id}")
    fun deleteUserById(@PathVariable id: Int): ResponseEntity<Any> {
        return try {
            val user = utilizadorParticularRepository.findById(id)
            if (user.isPresent) {
                utilizadorParticularRepository.delete(user.get())
                ResponseEntity.ok("Utilizador apagado com sucesso")
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado")
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error. Please try again later.")
        }
    }

    @DeleteMapping("/delete/byUsername/{username}")
    fun deleteUserByUsername(@PathVariable username: String): ResponseEntity<String> {
        val user = utilizadorParticularRepository.findUtilizadorParticularByUsername(username)

        return if (user != null) {
            val skills = formacaoRepository.findFormacaoByAuthor(user)
            val competencias = competenciasRepository.findCompetenciasByAuthor(user)
            val experiencias = experienciaRepository.findExperienciaByAuthor(user)
            val propostas = propostasRepository.findByCandidate(user)
            val comentarios = comentariosRepository.findCommentByAuthor(username)

            // Delete associated entities
            formacaoRepository.deleteAll(skills)
            competenciasRepository.deleteAll(competencias)
            experienciaRepository.deleteAll(experiencias)
            propostasRepository.deleteAll(propostas)
            comentariosRepository.deleteAll(comentarios)

            // Delete the user
            utilizadorParticularRepository.delete(user)

            ResponseEntity("Utilizador apagado com sucesso", HttpStatus.OK)
        } else {
            ResponseEntity("Utilizador não encontrado", HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/editUser")
    fun editUserUserById(@RequestBody updateUser: EditUtilizadorParticular): ResponseEntity<Any> {
        val userOptional = utilizadorParticularRepository.findById(updateUser.id)
        return if (userOptional.isPresent) {
            val userToUpdate = userOptional.get()
            userToUpdate.email = updateUser.email
            userToUpdate.name = updateUser.name
            userToUpdate.username = updateUser.username
            userToUpdate.profissao = updateUser.profissao
            userToUpdate.gender = updateUser.gender
            userToUpdate.numeroDeTelemovel = updateUser.contacto
            userToUpdate.age = updateUser.idade
            userToUpdate.password = updateUser.password
            utilizadorParticularRepository.save(userToUpdate)
            ResponseEntity.ok("Utilizador atualizado com sucesso")
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado")
        }
    }

    //validar username e pass no backend
    @GetMapping("/validator/{username}/{password}")
    fun getValidatedUser(@PathVariable username: String, @PathVariable password: String): ResponseEntity<Any> {
        val user = utilizadorParticularRepository.findUtilizadorParticularByUsername(username)

        return if (user != null) {
            if (user.password == password) {
                ResponseEntity.ok(user)
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password incorreta")
            }
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não existe")
        }
    }
}
