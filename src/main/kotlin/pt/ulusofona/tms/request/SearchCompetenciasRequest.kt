package pt.ulusofona.tms.request

import pt.ulusofona.tms.dao.*


// Search Skill
data class SearchSkillRequest(val id: Int, val skill: Competencias)

// Create Skill
data class CreateSkillRequest(val id: Int, val nome: String, val type: String, val author: UtilizadorParticular)

// Update Skill
data class UpdateSkillRequest(val id: Int, val nome: String, val type: String)

