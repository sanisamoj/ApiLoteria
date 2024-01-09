package com.example.demo.utils

import com.sanisamoj.service.Operations
import com.sanisamoj.database.ResultsCaixa
import com.sanisamoj.database.enums.Games
import com.sanisamoj.launcher.Launcher

//Responsável por auxiliar a verificação do parâmentro do GET como latest ou número
suspend fun findConc(loteria: String, id: String): ResultsCaixa? {
    return if (id == "latest" || id == "ultimo" || id == "recente") {
        Operations().getLastest(loteria)
    } else {
        Operations().getByConc(loteria, id.toInt())
    }
}

//Responsável por auxilizar a função de integridade, verificando os números faltantes de uma array
fun findMissingNumber(originArray : List<Int>, maxNumber: Int) : List<Int> {

    //Armazena a lista de números faltantes
    val missingNumbers: MutableList<Int> = mutableListOf<Int>()

    for(number in 1..maxNumber){
        if(!originArray.contains(number)) missingNumbers.add(number)
    }

    return missingNumbers

}

//Responsável por registrar os resultados na db
suspend fun registerResults(apiUrl: String, games: Array<Games>, drawType: String) {

    //Percorre a lista de jogos da loteria e registra
    for(game in games) {
        //Instancializa o launcher e registrar todos os resultados
        Launcher().registerAllResults(apiUrl, game.toString(), drawType)

    }
}

//Responsável por transformar em uma array de ‘string’ os valores do enum Games
fun converterGamesInArray() : List<String> {

    //Lista de jogos para iterar na Api
    val games: Array<Games> = Games.values()

    //Armazenará os jogos da loteria em ‘string’
    val arrayGames : MutableList<String> = mutableListOf()

    //Adiciona os itens na nova array
    for(game in games) {
        arrayGames.add(game.toString())
    }

    return arrayGames
}