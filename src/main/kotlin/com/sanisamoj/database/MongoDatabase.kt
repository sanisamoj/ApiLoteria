package com.sanisamoj.database

import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import com.sanisamoj.utils.analyzers.dotEnv
import org.bson.BsonInt64
import org.bson.Document

object MongoDatabase {

    private var database: MongoDatabase? = null
    private lateinit var client: MongoClient
    private val connectionString: String = dotEnv("SERVER_URL")
    private val databaseName: String = dotEnv("NAME_DATABASE")

    private suspend fun init() {
        client = MongoClient.create(connectionString)
        val db = client.getDatabase(databaseName)

        try {
            val command = Document("ping", BsonInt64(1))
            db.runCommand(command)
            println("You successfully connected to MongoDB!")
            database = db

        } catch (e: MongoException) {
            System.err.println(e)
        }
    }

    suspend fun initialize() { if (database == null) init() }

    suspend fun getDatabase(): MongoDatabase {
        if (database == null) init()
        return database!!
    }

}