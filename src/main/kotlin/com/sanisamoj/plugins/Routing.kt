package com.sanisamoj.plugins

import com.sanisamoj.routing.resultRouting
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        resultRouting()
    }
}
