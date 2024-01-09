package com.sanisamoj

import com.sanisamoj.plugins.configureRouting
import com.sanisamoj.plugins.configureSerialization
import io.ktor.server.application.*
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureRouting()

    launch {
        Config()
    }
}
