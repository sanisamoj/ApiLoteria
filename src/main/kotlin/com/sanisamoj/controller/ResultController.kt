package com.sanisamoj.controller

import com.example.demo.utils.converterGamesInArray
import com.example.demo.utils.findConc
import com.sanisamoj.database.PrizeApportionmentList
import com.sanisamoj.database.ResultsCaixa
import com.sanisamoj.service.Operations
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.resultRouting() {
    route("/api") {
        //Rota que retorna uma lista dos jogos da api
        get {
            //Converte os jogos em uma 'array'
            val result : List<String> = converterGamesInArray()
            call.respond(result)
        }
        //Rota que retorna todos os resultados de um jogo especifico
        get("/{loteria?}"){
            //Armazena o parametro
            val loteria : String = call.parameters["loteria"].toString()

            //Retorna todos resultados
            val result: List<ResultsCaixa> = Operations().getAll(loteria)

            if(result.isEmpty()) {
                return@get call.respond(HttpStatusCode.NotFound)
            }

            call.respond(result)

        }
        //Rota para um concurso de um jogo especifico
        get("{loteria?}/{id?}") {

            //Armazena os parâmetros
            val loteria: String = call.parameters["loteria"].toString()
            val id : String = call.parameters["id"].toString()

            try{
                //Retorna o resultado
                val result: ResultsCaixa? = findConc(loteria, id)

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