package com.book.Archiving

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class ArchivingApplication

fun main(args: Array<String>) {
    runApplication<ArchivingApplication>(*args)
}
