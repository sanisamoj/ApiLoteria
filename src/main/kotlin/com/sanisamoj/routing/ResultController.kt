package com.sanisamoj.routing

import com.sanisamoj.data.models.dataclass.ResultsApiResponse
import com.sanisamoj.service.ResultService
import com.sanisamoj.utils.analyzers.converterGamesInArray
import com.sanisamoj.utils.analyzers.findConc
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.resultRouting() {
    route("/api") {

        // Route that returns a list of api games
        get {
            val result : List<String> = converterGamesInArray()
            call.respond(result)
        }

        // Route that returns all results for a specific game
        get("/{loteria?}"){
            val loteria : String = call.parameters["loteria"].toString()
            val result: List<ResultsApiResponse> = ResultService().returnAll(loteria)
            if(result.isEmpty()) return@get call.respond(HttpStatusCode.NotFound)
            call.respond(result)
        }

        // Route to a contest for a specific game
        get("{loteria?}/{id?}") {
            val loteria: String = call.parameters["loteria"].toString()
            val id : String = call.parameters["id"].toString()

            try{
                val result: ResultsApiResponse? = findConc(loteria, id)

                if(result != null) {
                    call.respond(result)
                } else {
                    call.respond(HttpStatusCode.BadRequest, "Concurso $id para $loteria não encontrado!")
                }

            } catch (e : Throwable) {
                call.respond(HttpStatusCode.InternalServerError, "Erro interno do servidor.")
            }
        }
    }
}