package pt.ulusofona.tms.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.ui.ModelMap
import org.springframework.web.bind.annotation.*
import pt.ulusofona.tms.dao.UtilizadorEmpresarial
import pt.ulusofona.tms.repository.ComentariosRepository
import pt.ulusofona.tms.repository.PropostasRepository
import pt.ulusofona.tms.repository.UtilizadorEmpresarialRepository
import pt.ulusofona.tms.request.*


@RestController
@RequestMapping("/api/businessUsers")
class UtilizadorEmpresarialController(
    private val utilizadorEmpresarialRepository: UtilizadorEmpresarialRepository,
    private val utilizadorEmpresaRepository: UtilizadorEmpresarialRepository,
    private val comentariosRepository: ComentariosRepository,
    private val propostasRepository: PropostasRepository
) {

    // Gives all the normal users
    @GetMapping("/list", produces = ["application/json;charset=UTF-8"])
    fun list(model: ModelMap): List<UtilizadorEmpresarial> = utilizadorEmpresarialRepository.findAll()

    // Search by id
    @GetMapping("/searchId/{id}")
    fun getBusinessUserById(@PathVariable id: Int): ResponseEntity<Any> {
        // Getting the user by ID
        val user = utilizadorEmpresaRepository.findUtilizadorEmpresarialById(id)

        // Check if the user exists
        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            // Return a not found response
            ResponseEntity("Utilizador Empresarial não encontrado", HttpStatus.NOT_FOUND)
        }
    }

    // Search By name
    @GetMapping("/searchName/{name}")
    fun getBusinessUserByName(@PathVariable name: String): ResponseEntity<out Any> {
        val user = utilizadorEmpresarialRepository.findUtilizadorEmpresarialsByname(name)

        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity("Utilizador Empresarial não encontrado", HttpStatus.NOT_FOUND)
        }
    }

    // Search By username
    @GetMapping("/searchUserName/{username}")
    fun getBusinessUserByUserName(@PathVariable username: String): ResponseEntity<out Any> {

        val user = utilizadorEmpresarialRepository.findUtilizadorEmpresarialByUser(username)

        return if (user != null) {
            ResponseEntity(user, HttpStatus.OK)
        } else {
            ResponseEntity("Utilizador Empresarial não encontrado", HttpStatus.NOT_FOUND)
        }
    }

    // Adding User
    @PostMapping("/add")
    fun createBusinessUser(@RequestBody user: CreateUtilizadorEmpresarialRequest): ResponseEntity<Any> {
        // Check if a user with the same username exists
        val existingUser = utilizadorEmpresarialRepository.findUtilizadorEmpresarialByUser(user.username)
        if (existingUser != null) {
            // User with the same username already exists, return a response with conflict status
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username '${user.username}' is already taken.")
        }

        // Check if a user with the same email exists
        val existingEmailUser = utilizadorEmpresarialRepository.findUtilizadorEmpresarialsByEmail(user.email)
        if (existingEmailUser != null) {
            // User with the same email already exists, return a response with conflict status
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email '${user.email}' is already registered.")
        }

        // Create UtilizadorEmpresarial instance
        var utilizadorDB = UtilizadorEmpresarial(
            user.id, user.email, user.name, user.username, user.gender, user.profissao,
            user.contacto, user.age, user.password, user.empresa
        )

        // Check if a user with the same ID exists
        if (utilizadorEmpresarialRepository.existsById(user.id)) {
            // Change the ID (you can implement a method to generate a new unique ID if needed)
            utilizadorDB = utilizadorDB.copy(id = 0) // Assuming 0 means auto-generated ID
        }


        // Saving the UtilizadorEmpresarial object
        val savedUtilizador = utilizadorEmpresarialRepository.save(utilizadorDB)

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUtilizador)
    }

    // Delete User
    @DeleteMapping("/delete/{id}")
    fun deleteBusinessUserById(@PathVariable id: Int): ResponseEntity<Any> {
        return if (utilizadorEmpresaRepository.existsById(id)) {
            utilizadorEmpresaRepository.deleteById(id)
            ResponseEntity("Utilizador apagado com sucesso", HttpStatus.OK)
        } else {
            ResponseEntity("Utilizador não encontrado", HttpStatus.NOT_FOUND)
        }
    }

    // Delete User by username
    @DeleteMapping("/deleteByUserName/{username}")
    fun deleteBusinessUserByUsername(@PathVariable username: String): ResponseEntity<Any> {
        val user = utilizadorEmpresarialRepository.findUtilizadorEmpresarialByUser(username)

        return if (user != null) {
            // Delete comments by the user
            comentariosRepository.findCommentByAuthor(username).forEach { comment ->
                comentariosRepository.delete(comment)
            }

            // Delete proposals by the user
            propostasRepository.findByAuthor(user).forEach { proposta ->
                propostasRepository.delete(proposta)
            }

            // Delete the user itself
            utilizadorEmpresarialRepository.delete(user)

            ResponseEntity("Utilizador apagado com sucesso", HttpStatus.OK)
        } else {
            ResponseEntity("Utilizador não encontrado", HttpStatus.NOT_FOUND)
        }
    }


    // Edit User
    @PutMapping("/editBusinessUser")
    fun editBusinessUserById(@RequestBody updateBusinessUser: EditUtilizadorEmpresarial): ResponseEntity<Any> {
        // Find the user by id.
        val user = utilizadorEmpresarialRepository.findById(updateBusinessUser.id)

        // Checks if the user exists.
        return if (user.isPresent) {
            // Creates a user to update the previous.
            val userToUpdate = user.get()
            userToUpdate.email = userToUpdate.email
            userToUpdate.name = userToUpdate.name
            userToUpdate.user = userToUpdate.user
            userToUpdate.gender = userToUpdate.gender
            userToUpdate.profissao = userToUpdate.profissao
            userToUpdate.contacto = userToUpdate.contacto
            userToUpdate.age = userToUpdate.age
            userToUpdate.password = userToUpdate.password
            userToUpdate.empresa = userToUpdate.empresa

            // Save the updated user
            utilizadorEmpresarialRepository.save(userToUpdate)
            ResponseEntity("Utilizador atualizado com sucesso", HttpStatus.OK)
        } else {
            ResponseEntity("Utilizador não encontrado", HttpStatus.NOT_FOUND)
        }
    }

    //validar username e pass no backend
    @GetMapping("/validator/{username}/{password}")
    fun getValidatedBusinessUser(@PathVariable username: String, @PathVariable password: String): ResponseEntity<Any> {
        val user = utilizadorEmpresarialRepository.findUtilizadorEmpresarialByUser(username)

        return if (user != null) {
            if (user.password == password) {
                ResponseEntity.ok(user)
            } else {
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Password incorreta")
            }
        } else {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Utilizador não encontrado")
        }
    }
}


