package pt.ulusofona.tms.service


interface ITokenGenerationService {
    fun generateToken(size: Int = TOKEN_LENGTH): String
}