package com.github.bawey.demokotlinspringboot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.CrudRepository
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.toList

@SpringBootApplication
class DemoKotlinSpringbootApplication

interface MessageRepository : CrudRepository<Message, String>

@Table("MESSAGES")
data class Message(@Id var id: String?, val text: String)

@RestController
class DemoController(val repo: MessageRepository) {
    @GetMapping("/")
    fun index(@RequestParam("name") name: String?) = repo.findAll().toList()

    @GetMapping("/{id}")
    fun findOne(@PathVariable(name = "id") id: String): List<Message> = repo.findById(id).toList()

    @PostMapping
    fun insert(@RequestBody message: Message) {
        repo.save(message);
    }

}

fun main(args: Array<String>) {
    runApplication<DemoKotlinSpringbootApplication>(*args)
}
