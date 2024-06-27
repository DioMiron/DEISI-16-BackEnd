package pt.ulusofona.tms

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import pt.ulusofona.tms.filters.ControllerRequestsLoggingFilter


@Configuration
class ControllerRequestsLoggingFilterConfig {

    // ********** IMPORTANT **********
    // don't forget to include the following line in application.properties:
    // logging.level.org.springframework.web.filter.ControllerRequestsLoggingFilter=INFO

    @Bean
    fun logFilter() = ControllerRequestsLoggingFilter()
}
