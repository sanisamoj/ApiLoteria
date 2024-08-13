package com.sanisamoj.config

import com.sanisamoj.database.MongoDatabase
import com.sanisamoj.utils.analyzers.VerifyIntegrityAllGames
import com.sanisamoj.utils.schedule.ScheduleRoutine
import com.sanisamoj.utils.schedule.models.JobIdentification
import com.sanisamoj.utils.schedule.models.RoutineGroups
import com.sanisamoj.utils.schedule.models.StartRoutineData
import com.sanisamoj.utils.schedule.routines.VerifyIntegrity
import org.quartz.JobKey

object Config {
    private val jobsIdentificationList: MutableList<JobIdentification> = mutableListOf()

    suspend fun initialize() {
        MongoDatabase.initialize()
        routinesInitialize()
    }

    private fun routinesInitialize() {
        verifyIntegrity()
    }

    private fun verifyIntegrity() {
        val routineName = "verifyIntegrity-EveryMonth"
        val startRoutineData = StartRoutineData(
            name = routineName,
            group = RoutineGroups.ResultsUpdate,
            cronExpression = GlobalContext.ONCE_A_MONTH_CRON
        )

        val jobKey: JobKey = ScheduleRoutine().startRoutine<VerifyIntegrity>(startRoutineData)

        val jobIdentification = JobIdentification(jobKey, routineName)
        jobsIdentificationList.add(jobIdentification)

        VerifyIntegrityAllGames.init()
    }
}