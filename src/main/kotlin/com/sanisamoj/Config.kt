package com.sanisamoj

import com.sanisamoj.utils.schedule.models.SchedulerLauncher
import com.sanisamoj.routines.UpdateAllResults

// Responsável por configurar o projeto
object Config {

    suspend fun initilize() {
        val scheduler = SchedulerLauncher()
        scheduler.init(UpdateAllResults(), 23)
    }

}