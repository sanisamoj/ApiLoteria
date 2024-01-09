package com.example.demo.launcher

import com.sanisamoj.database.enums.Games
import com.sanisamoj.launcher.Launcher
import kotlinx.coroutines.runBlocking
import org.quartz.CronScheduleBuilder
import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobExecutionContext
import org.quartz.TriggerBuilder
import org.quartz.impl.StdSchedulerFactory
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Date

//Classe responsável pelo que irá executar no scheduler
class VerifyIntegrety : Job {
    override fun execute(jobExecutionContext: JobExecutionContext) {
        //Inicia o launcher
        val launcher = Launcher()
        //Lista de jogos para iterar na Api
        val games : Array<Games> = Games.entries.toTypedArray()

        //Api responsável pela atualização dos resultados
        val apiUrl : String = "https://servicebus2.caixa.gov.br/portaldeloterias/api"

        runBlocking {
            for(game in games) {
                launcher.veriFyIntegrity(apiUrl, game.toString())
            }
        }
    }
}

//Classe que executa alguma função em determinado tempo
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