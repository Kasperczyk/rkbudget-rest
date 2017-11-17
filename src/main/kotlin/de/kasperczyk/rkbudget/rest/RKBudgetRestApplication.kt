package de.kasperczyk.rkbudget.rest

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class RKBudgetRestApplication {

    @Bean
    @Qualifier("objectMapper")
    fun objectMapper(): ObjectMapper {
        val objectMapper = ObjectMapper()
        objectMapper.registerModule(JavaTimeModule())
        objectMapper.registerModule(KotlinModule())
        return objectMapper
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(RKBudgetRestApplication::class.java, *args)
}
