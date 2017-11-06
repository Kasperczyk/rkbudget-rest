package de.kasperczyk.rkbudget.rest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class RkbudgetRestApplication

fun main(args: Array<String>) {
    SpringApplication.run(RkbudgetRestApplication::class.java, *args)
}
