package com.sanisamoj.utils.schedule.models

import org.quartz.CronScheduleBuilder
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory

// Classe que executa alguma função em determinado tempo
class SchedulerLauncher() {

    fun init(job : Job, hours: Int, minutes : Int = 0) {
        //Instancializa o fator scheduler
        val schedulerFactory = StdSchedulerFactory()
        val scheduler = schedulerFactory.scheduler

        //Inicia o scheduler
        scheduler.start()

        //Programa os detalhes da função a ser executada
        val jobDetail = JobBuilder.newJob(job::class.java)
            .build()

        //Constrói o tempo que irá executar a tarefa
        val trigger = TriggerBuilder.newTrigger()
            .withSchedule(CronScheduleBuilder.dailyAtHourAndMinute(hours, minutes))
            .build()

        //Inicializa o agendador
        scheduler.scheduleJob(jobDetail, trigger)
    }

}