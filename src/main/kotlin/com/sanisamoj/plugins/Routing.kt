package com.sanisamoj.plugins

import com.sanisamoj.controller.resultRouting
import com.sanisamoj.service.Operations
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        resultRouting()
    }
}
