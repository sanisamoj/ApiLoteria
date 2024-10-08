package com.sanisamoj

import com.sanisamoj.config.Config
import com.sanisamoj.plugins.*
import io.ktor.server.application.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureRateLimit()
    configureSerialization()
    configureStatusPage()
    configureRouting()
    configureHTTP()
    startTasks()
}

private fun startTasks() {
    CoroutineScope(Dispatchers.IO).launch {
        Config.initialize()
    }
}
