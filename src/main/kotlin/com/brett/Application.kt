package com.brett

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration

@SpringBootApplication
class Application {
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}

@Configuration
class AppConfig