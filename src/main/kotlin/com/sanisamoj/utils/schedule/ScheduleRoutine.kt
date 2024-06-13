package com.sanisamoj.utils.schedule

import com.sanisamoj.utils.schedule.models.JobIdentification
import com.sanisamoj.utils.schedule.models.StartRoutineData
import org.quartz.*
import org.quartz.impl.StdSchedulerFactory

class ScheduleRoutine {
    var scheduler: Scheduler = StdSchedulerFactory().scheduler
        private set
    
    companion object {
        var jobsList: MutableList<JobIdentification> = mutableListOf()
            private set
    }

    inline fun <reified T : Job>startRoutine(startRoutineData: StartRoutineData): JobKey {
        
        scheduler.start()

        val job = JobBuilder.newJob(T::class.java)
            .withIdentity(startRoutineData.name, startRoutineData.group.name)
            .build()

        val trigger = TriggerBuilder.newTrigger()
            .withIdentity(startRoutineData.name, startRoutineData.group.name)
            .startNow()
            .withSchedule(
                if(startRoutineData.repeatForever) {
                    SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(startRoutineData.interval)
                        .repeatForever()
                } else {
                    SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMilliseconds(startRoutineData.interval)
                }
            )
            .build()

        scheduler.scheduleJob(job, trigger)
        val jobIdentification = JobIdentification(job.key, startRoutineData.description)
        jobsList.add(jobIdentification)
        return job.key
    }

    fun stopRoutine(jobKey: JobKey) {
        scheduler.deleteJob(jobKey)
    }
}