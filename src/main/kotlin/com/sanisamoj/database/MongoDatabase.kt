package com.sanisamoj.database

import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.cdimascio.dotenv.Dotenv
import org.bson.BsonInt64
import org.bson.Document

object MongoDatabase {

    private val dotenv: Dotenv = Dotenv.configure().ignoreIfMissing().load()

    private var database: MongoDatabase? = null
    private lateinit var client: MongoClient
    private val connectionString: String = dotenv["SERVER_URL"]
    private val databaseName: String = dotenv["NAME_DATABASE"]

    // Incia a conexão com o banco de dados
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

    // Inicia o banco de dados caso não tenha sido iniciado
    suspend fun initialize() { if (database == null) init() }

    // Retorna o banco de dados inicializado
    suspend fun getDatabase(): MongoDatabase {
        if (database == null) init()
        return database!!
    }

}