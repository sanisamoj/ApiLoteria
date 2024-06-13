package com.example.demo.utils

import com.sanisamoj.models.generics.ResultsCaixa
import com.sanisamoj.models.enums.Games
import com.sanisamoj.service.ResultService

// Responsável por auxiliar a verificação do parâmentro do GET como latest ou número
suspend fun findConc(loteria: String, id: String): ResultsCaixa? {
    return if (id == "latest" || id == "ultimo" || id == "recente") {
        ResultService().returnLatest(loteria)
    } else {
        ResultService().returnByConc(loteria, id.toInt())
    }
}

// Responsável por auxilizar a função de integridade, verificando os números faltantes de uma array
fun findMissingNumber(originArray : List<Int>, maxNumber: Int) : List<Int> {

    //Armazena a lista de números faltantes
    val missingNumbers: MutableList<Int> = mutableListOf<Int>()

    for(number in 1..maxNumber){
        if(!originArray.contains(number)) missingNumbers.add(number)
    }

    return missingNumbers

}

// Responsável por transformar em uma array de ‘string’ os valores do enum Games
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