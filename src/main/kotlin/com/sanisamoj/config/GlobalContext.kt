package com.sanisamoj.config

import com.sanisamoj.data.repository.ApiRepository
import com.sanisamoj.data.repository.DatabaseDefaultRepository

object GlobalContext {
    val apiRepository by lazy { ApiRepository() }
    val databaseRepository by lazy { DatabaseDefaultRepository() }

    const val EVERY_DAY_AT_10_PM_CRON: String = "0 0 22 * * ?"

}