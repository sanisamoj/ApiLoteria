package com.sanisamoj.config

import com.sanisamoj.data.repository.ApiRepository
import com.sanisamoj.data.repository.DatabaseDefaultRepository

object GlobalContext {
    val apiRepository by lazy { ApiRepository() }
    val databaseRepository by lazy { DatabaseDefaultRepository() }

    const val ONCE_A_MONTH_CRON: String = "0 0 0 1 * ?"
}