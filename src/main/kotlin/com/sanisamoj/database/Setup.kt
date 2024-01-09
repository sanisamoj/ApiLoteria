package com.sanisamoj.database

import com.mongodb.MongoException
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import io.github.cdimascio.dotenv.Dotenv
import org.bson.BsonInt64
import org.bson.Document

object DatabaseManager {

    //Configura o dotenv
    private val dotenv : Dotenv = Dotenv.configure().ignoreIfMissing().load()

    //Variável responsável por armazenar a db
    private var database: MongoDatabase? = null

    //Variável responsável por armazenar o client Mongo_DB
    private lateinit var client : MongoClient

    //Variável responsável por armazenar a url da conexão com o banco de dados
    private val connectionString: String = dotenv["SERVER_URL"]

    //Variável responsável por armazenar o nome do banco de dados no MONGODB
    private val nameDatabase : String = dotenv["NAME_DATABASE"]

    //Inicia a conexão com o banco de dados
    private suspend fun init() {

        //Cliente que irá se conectar ao banco de dados
        client = MongoClient.create(connectionString)

        //Retorna o banco de dados
        val db: MongoDatabase = client.getDatabase(nameDatabase)

        try {
            val command = Document("ping", BsonInt64(1))
            db.runCommand(command)
            println("You successfully connected to MongoDB!")
            database = db
        } catch (me: MongoException) {
            System.err.println(me)
        }
    }

    suspend fun getDatabase(): MongoDatabase {
        if (database == null) {
            init()
        }
        return database!!
    }

}
