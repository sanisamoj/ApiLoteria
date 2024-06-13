package com.sanisamoj

import com.sanisamoj.database.MongoDatabase
import com.sanisamoj.plugins.configureRateLimit
import com.sanisamoj.plugins.configureRouting
import com.sanisamoj.plugins.configureSerialization
import com.sanisamoj.plugins.configureStatusPage
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
    startTasks()
}

private fun startTasks() {
    CoroutineScope(Dispatchers.IO).launch {
        launch { MongoDatabase.initialize() }
        launch { Config.initilize() }
    }
}
