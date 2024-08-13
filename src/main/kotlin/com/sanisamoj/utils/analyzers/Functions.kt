package com.sanisamoj.utils.analyzers

import com.sanisamoj.data.models.dataclass.ResultsApiResponse
import com.sanisamoj.data.models.enums.Games
import com.sanisamoj.service.ResultService

// Responsible for helping verify GET parameters such as latest or number
suspend fun findConc(loteria: String, id: String): ResultsApiResponse? {
    return if (id == "latest" || id == "ultimo" || id == "recente") {
        ResultService().returnLatest(loteria)
    } else {
        ResultService().returnByConc(loteria, id.toInt())
    }
}

// Responsible for assisting the integrity function, checking missing numbers in an array
fun findMissingNumber(originArray : List<Int>, maxNumber: Int) : List<Int> {
    val missingNumbers: MutableList<Int> = mutableListOf<Int>()
    for(number in 1..maxNumber){
        if(!originArray.contains(number)) missingNumbers.add(number)
    }
    return missingNumbers
}

// Responsible for transforming the values of the Games enum into a ‘string’ array
fun converterGamesInArray() : List<String> {
    val games: Array<Games> = Games.values()
    val arrayGames : MutableList<String> = mutableListOf()
    for(game in games) {
        arrayGames.add(game.toString())
    }
    return arrayGames
}