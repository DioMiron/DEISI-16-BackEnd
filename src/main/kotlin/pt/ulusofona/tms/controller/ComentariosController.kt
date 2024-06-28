package pt.ulusofona.tms.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pt.ulusofona.tms.repository.ComentariosRepository
import pt.ulusofona.tms.repository.UtilizadorParticularRepository
import pt.ulusofona.tms.dao.Comentarios
import pt.ulusofona.tms.repository.UtilizadorEmpresarialRepository
import pt.ulusofona.tms.request.CreateNewComentariosRequest

@RestController
@RequestMapping("api/comentarios")
class ComentariosController(
    private val comentariosRepository: ComentariosRepository,
    private val utilizadorParticularRepository: UtilizadorParticularRepository,
    private val utilizadorEmpresarialRepository: UtilizadorEmpresarialRepository,
) {
    // Gives all the Comments
    @GetMapping("/list", produces = ["application/json;charset=UTF-8"])
    fun list(): List<Comentarios> = comentariosRepository.findAll()

    // Search by id
    @GetMapping("/listOfUser/{id}")
    fun getCommentsOfUserById(@PathVariable id: Int): ResponseEntity<out Any> {
        val userParticularOptional = utilizadorParticularRepository.findById(id)
        if (userParticularOptional.isPresent) {
            val user = userParticularOptional.get()
            val username = user.username

            // Retrieve comments where author equals the username
            val comments = comentariosRepository.findCommentByAuthor(username)

            return ResponseEntity(comments, HttpStatus.OK)
        }

        // If not found in UtilizadorParticularRepository, check in UtilizadorEmpresarialRepository
        val userEmpresaOptional = utilizadorEmpresarialRepository.findById(id)
        if (userEmpresaOptional.isPresent) {
            val user = userEmpresaOptional.get()
            val username = user.user

            // Retrieve comments where author equals the username
            val comments = comentariosRepository.findCommentByAuthor(username)

            return ResponseEntity(comments, HttpStatus.OK)
        }

        // If user is not found in either repository
        return ResponseEntity("Utilizador não encontrado", HttpStatus.NOT_FOUND)
    }

    // Creates a new Comment
    @PostMapping("/createComment")
    fun createCommentForUser(@RequestBody comment: CreateNewComentariosRequest): ResponseEntity<Any> {
        // Check if the user exists in UtilizadorParticularRepository
        val userParticularOptional = utilizadorParticularRepository.findUtilizadorParticularByUsername(comment.username)

        if (userParticularOptional != null) {
            // Create new comment instance
            var newComment = Comentarios(
                author = comment.username,
                data = comment.datetime,
                comentario = comment.comentario
            )

            // Check if a comment with the same author, datetime, and comentario already exists
            val existingComment = comentariosRepository.findByAuthorAndComentario(
                newComment.author, newComment.comentario
            )

            if (existingComment != null) {
                // Comment with the same attributes already exists, return a conflict response
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Comment with these details already exists.")
            }

            // Check if there is a comment with the same ID exists
            if (comentariosRepository.existsById(comment.id)) {
                // Change the ID (you can implement a method to generate a new unique ID if needed)
                newComment = newComment.copy(id = 0) // Assuming 0 means auto-generated ID
            }

            val savedComment = comentariosRepository.save(newComment)
            return ResponseEntity(savedComment, HttpStatus.CREATED)
        }

        // If not found in UtilizadorParticularRepository, check in UtilizadorEmpresarialRepository
        val userEmpresaOptional = utilizadorEmpresarialRepository.findUtilizadorEmpresarialByUser(comment.username)
        if (userEmpresaOptional != null) {
            // Create new comment instance
            var newComment = Comentarios(
                author = comment.username,
                data = comment.datetime,
                comentario = comment.comentario
            )

            // Check if a comment with the same author, datetime, and comentario already exists
            val existingComment = comentariosRepository.findByAuthorAndComentario(
                newComment.author, newComment.comentario
            )

            if (existingComment != null) {
                // Comment with the same attributes already exists, return a conflict response
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Comment with these details already exists.")
            }

            // Check if there is a comment with the same ID exists
            if (comentariosRepository.existsById(comment.id)) {
                // Change the ID (you can implement a method to generate a new unique ID if needed)
                newComment = newComment.copy(id = 0) // Assuming 0 means auto-generated ID
            }

            val savedComment = comentariosRepository.save(newComment)
            return ResponseEntity(savedComment, HttpStatus.CREATED)
        }

        // If user is not found in either repository
        return ResponseEntity("Utilizador não encontrado", HttpStatus.NOT_FOUND)
    }

    //Delete comments by username
    @DeleteMapping("/deleteComment/{id}")
    fun deleteCommentsByAuthor(@PathVariable id: Int): ResponseEntity<Any> {
        // Find the comment by ID
        val commentOptional = comentariosRepository.findById(id)

        // Check if the comment exists
        if (commentOptional.isPresent) {
            val comment = commentOptional.get()

            // Delete the comment
            comentariosRepository.delete(comment)

            return ResponseEntity.ok("Deleted comment with ID $id")
        } else {
            return ResponseEntity.notFound().build()
        }
    }
}

