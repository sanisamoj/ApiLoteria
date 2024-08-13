package com.sanisamoj.utils.analyzers

import com.sanisamoj.data.models.enums.Games
import com.sanisamoj.launcher.Launcher
import kotlinx.coroutines.runBlocking

object VerifyIntegrityAllGames {
    @OptIn(ExperimentalStdlibApi::class)
    fun init() {
        val launcher = Launcher()
        val games : Array<Games> = Games.entries.toTypedArray()

        runBlocking {
            for(game in games) {
                launcher.verifyIntegrity(game.toString())
            }
        }
    }
}