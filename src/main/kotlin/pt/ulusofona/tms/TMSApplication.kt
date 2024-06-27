package pt.ulusofona.tms

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TMSApplication

fun main(args: Array<String>) {
    runApplication<TMSApplication>(*args)
}
