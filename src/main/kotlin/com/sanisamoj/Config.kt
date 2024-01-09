package com.sanisamoj

import com.example.demo.launcher.SchedulerLauncher
import com.example.demo.launcher.VerifyIntegrety
import com.sanisamoj.database.enums.Games
import com.sanisamoj.launcher.Launcher
import kotlinx.coroutines.runBlocking

//Responsável por configurar o projeto
class Config {

    init {
        runBlocking {

            //Trabalho que irá ser agendado
            val job = VerifyIntegrety()

            //Instancializa o scheduler
            val scheduler = SchedulerLauncher()

            //Executa o job todos dia ás 20h30
            scheduler.init(job, 20, 30)
            //Executa o job todos dia ás 21h
            scheduler.init(job, 21)
            //Executa o job todos dia ás 21h30
            scheduler.init(job, 21, 30)
            //Executa o job todos dia ás 23h
            scheduler.init(job, 23)
            //Executa o job todos dia ás 01h
            scheduler.init(job, 1)

            //API responsáveis por registrar/alimentar os dados dos sorteios no banco de dados | API + /loteria + /último concurso
            val apiCaixa = "https://servicebus2.caixa.gov.br/portaldeloterias/api"

            //Lista de jogos para iterar na Api
            val games : Array<Games> = Games.values()

            //Chama a função que registra os resultados
            //registerResults(apiCaixa, games, "")

            //Para cada game, é verificado a integradade dos resultados
            games.forEach { game ->
                Launcher().veriFyIntegrity(apiCaixa, game.toString())
            }
        }
    }

}