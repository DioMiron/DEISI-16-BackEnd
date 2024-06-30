package pt.ulusofona.tms

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Profile

import org.springframework.security.provisioning.UserDetailsManager
import org.springframework.stereotype.Component
import pt.ulusofona.tms.controller.FormacaoController
import pt.ulusofona.tms.dao.*
import pt.ulusofona.tms.repository.*


@Component
@Profile("!test")
class DataLoader(

    val userRepository: UserRepository,
    val userDetailsManager: UserDetailsManager,
    val utilizadorParticularRepository: UtilizadorParticularRepository,
    val utilizadorEmpresarialRepository: UtilizadorEmpresarialRepository,
    private val propostasRepository: PropostasRepository,
    private val comentariosRepository: ComentariosRepository,
    private val formacaoRepository: FormacaoRepository,
    private val experienciaRepository: ExperienciaRepository,
    private val competenciasRepository: CompetenciasRepository,

    ) : ApplicationRunner {

    var logger: Logger = LoggerFactory.getLogger(DataLoader::class.java)

    override fun run(args: ApplicationArguments?) {

        logger.info("Environment variables:")
        for ((key, value) in System.getenv()) {
            logger.info("\t$key : $value")
        }

        val countUsers = userRepository.count()

        if (countUsers == 0L) {
            logger.info("No users yet, let's create an admin user")
            // let's create a super user
            userDetailsManager.createUser(
                User(firstName = "admin", email = "admin", pass = "123",
                    rolesAsList = listOf(Role.ROLE_USER, Role.ROLE_ADMIN),
                    emailConfirmed = true)
            )
            userDetailsManager.createUser(
                User(firstName = "user", email = "user", pass = "password",
                    rolesAsList = listOf(Role.ROLE_USER),
                    emailConfirmed = true)
            )
        } else {
            logger.info("Users already created, nothing to do here...")
        }

        // Creates the Normal User ADMIN
        if (utilizadorParticularRepository.findAll().isEmpty()) {
            logger.info("No Normal User, let´s create an normal user")
            utilizadorParticularRepository.save(UtilizadorParticular(
                id = 0,
                name = "admin",
                email = "admin@admin.pt",
                username = "admin",
                gender = "male",
                password = "Admin123!",
                numeroDeTelemovel = "962821351",
                age = 21,
                profissao = "Engenheiro Informático",
            ))
        }else {
            logger.info("Main normal user already created, nothing to do here...")
        }

        // Creates the Business User
        if (utilizadorEmpresarialRepository.findAll().isEmpty()) {
            logger.info("No Business User, let´s create an business user")
            utilizadorEmpresarialRepository.save(UtilizadorEmpresarial(
                id = 0,
                name = "Rui Ribeiro",
                email = "RuiRibeiro@admin.pt",
                user = "Rui",
                gender = "male",
                password = "Rui123!",
                contacto = "962821351",
                age = 21,
                profissao = "Engenheiro Informático",
                empresa = "Lusofona",
            ))
        }else {
            logger.info("Main Business user already created, nothing to do here...")
        }

        // Creates a Formacao
        if (formacaoRepository.findAll().isEmpty()) {
            logger.info("No Formation, let´s create a Job")
            formacaoRepository.save(FormacaoAcademica(
                id = 0,
                nome = "Python",
                tipoformacao = "Técnica",
                instituto = "Tecnico",
                duracao = "10 meses",
                author = utilizadorParticularRepository.findUtilizadorParticularByUsername("admin"),
            )
            )
        } else {
            logger.info("Formation already created, nothing to do here...")
        }

        // Creates a comment
        if (comentariosRepository.findAll().isEmpty()) {
            logger.info("No Comments, let´s create a Job")
            comentariosRepository.save(Comentarios(
                id = 0,
                author = "admin",
                data = "22/01/2024",
                comentario = "Que te passa uma polo",
            )
            )
        }else {
            logger.info("Comment already created, nothing to do here...")
        }

        // Creates a Job
        if (propostasRepository.findAll().isEmpty()) {
            logger.info("No Jobs, let´s create a Job")
            propostasRepository.save(Propostas(
                id = 0,
                area = "Engenharia Informática",
                descricao = "Procuramos alguém competente e trabalhador.",
                author = utilizadorEmpresarialRepository.findUtilizadorEmpresarialByUser("Rui"),
                candidate = null,
            )
            )
        } else {
            logger.info("Job already created, nothing to do here...")
        }

        // Create Experience
        if (experienciaRepository.findAll().isEmpty()) {
            logger.info("No Experiences, let´s create a Job")
            experienciaRepository.save(ExperienciaLaboral(
                id = 0,
                cidade = "Lisboa",
                profissao = "Engenheiro Informático",
                nomeEmpresa = "Auren",
                duracaoDaExperiencia = "3 meses",
                author = utilizadorParticularRepository.findUtilizadorParticularByUsername("admin"),
            )
            )
        } else {
            logger.info("Experience already created, nothing to do here...")
        }

        // Create Skill
        if (competenciasRepository.findAll().isEmpty()) {
            logger.info("No skills, let´s create a Job")
            competenciasRepository.save(Competencias (
                id = 0,
                nome = "Python",
                type = "Technical Skill",
                author = utilizadorParticularRepository.findUtilizadorParticularByUsername("admin"),
            )
            )
        } else {
            logger.info("Skill already created, nothing to do here...")
        }
    }
}