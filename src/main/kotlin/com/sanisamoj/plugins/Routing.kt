package com.sanisamoj.plugins

import com.sanisamoj.controller.resultRouting
import com.sanisamoj.models.pages.HomePage
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.html.html
import kotlinx.html.stream.appendHTML

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText(
                text = buildString {
                    appendHTML().html {
                        HomePage()
                    }
                },
                contentType = ContentType.Text.Html
            )
        }

        resultRouting()
    }
}
