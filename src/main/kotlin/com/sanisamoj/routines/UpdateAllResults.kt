package com.sanisamoj.routines

import com.sanisamoj.service.UpdateResultService
import kotlinx.coroutines.runBlocking
import org.quartz.Job
import org.quartz.JobExecutionContext

class UpdateAllResults: Job {
    override fun execute(jobExecutionContext: JobExecutionContext) {
        runBlocking {
            UpdateResultService().verifyAllResultsAndUpdate()
        }
    }
}