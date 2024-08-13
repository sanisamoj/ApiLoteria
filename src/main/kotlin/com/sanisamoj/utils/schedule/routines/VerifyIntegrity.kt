package com.sanisamoj.utils.schedule.routines

import com.sanisamoj.data.models.enums.Games
import com.sanisamoj.launcher.Launcher
import com.sanisamoj.utils.analyzers.VerifyIntegrityAllGames
import kotlinx.coroutines.runBlocking
import org.quartz.*

class VerifyIntegrity : Job {

    @OptIn(ExperimentalStdlibApi::class)
    override fun execute(jobExecutionContext: JobExecutionContext) {
        VerifyIntegrityAllGames.init()
    }
}