package com.sanisamoj.service

import com.sanisamoj.database.DatabaseManager
import com.sanisamoj.database.ResultsCaixa
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.mongodb.kotlin.client.coroutine.MongoDatabase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId

class Operations {

    //Registra um ‘item’ no banco de dados
    suspend fun addItem(result: ResultsCaixa?) : Unit {

        //Instancializa o serviço com o banco de dados
        val database: MongoDatabase = DatabaseManager.getDatabase()

        //Caso lance um ‘item’ nulo
        if (result == null) {
            return
        }

        try {

            //Nome da coleção
            val collection: MongoCollection<ResultsCaixa> = database.getCollection<ResultsCaixa>("results")

            //Altera o nome da loteria
            val loteria: String = result.tipoJogo.lowercase().replace("_", "").replace("loteria", "")

            //Verifica se já existe o sorteio no banco de dados
            val isExists: ResultsCaixa? = collection.find<ResultsCaixa>(Document("tipoJogo", loteria).append("numero", result.numero)).firstOrNull()

            //Caso haja um registro na db, nada faz
            if (isExists != null) {
                return
            }

            //Instancializa o resultado da caixa para inserir no banco de dados
            val item = ResultsCaixa(
                id_mongo = ObjectId(),
                tipoJogo = loteria,
                numero = result.numero,
                acumulado = result.acumulado,
                dataApuracao = result.dataApuracao,
                dataProximoConcurso = result.dataProximoConcurso,
                dezenasSorteadasOrdemSorteio = result.dezenasSorteadasOrdemSorteio,
                exibirDetalhamentoPorCidade = result.exibirDetalhamentoPorCidade,
                id = result.id,
                indicadorConcursoEspecial = result.indicadorConcursoEspecial,
                listaDezenas = result.listaDezenas,
                trevosSorteados = result.trevosSorteados,
                listaDezenasSegundoSorteio = result.listaDezenasSegundoSorteio,
                listaMunicipioUFGanhadores = result.listaMunicipioUFGanhadores,
                listaRateioPremio = result.listaRateioPremio,
                listaResultadoEquipeEsportiva = result.listaResultadoEquipeEsportiva,
                localSorteio = result.localSorteio,
                nomeMunicipioUFSorteio = result.nomeMunicipioUFSorteio,
                nomeTimeCoracaoMesSorte = result.nomeTimeCoracaoMesSorte,
                numeroConcursoAnterior = result.numeroConcursoAnterior,
                numeroConcursoFinal_0_5 = result.numeroConcursoFinal_0_5,
                numeroConcursoProximo = result.numeroConcursoProximo,
                numeroJogo = result.numeroJogo,
                observacao = result.observacao,
                tipoPublicacao = result.tipoPublicacao,
                ultimoConcurso = result.ultimoConcurso,
                valorArrecadado = result.valorArrecadado,
                valorAcumuladoConcurso_0_5 = result.valorAcumuladoConcurso_0_5,
                valorAcumuladoConcursoEspecial = result.valorAcumuladoConcursoEspecial,
                valorAcumuladoProximoConcurso = result.valorAcumuladoProximoConcurso,
                valorEstimadoProximoConcurso = result.valorEstimadoProximoConcurso,
                valorSaldoReservaGarantidora = result.valorSaldoReservaGarantidora,
                valorTotalPremioFaixaUm = result.valorTotalPremioFaixaUm,
            )

            //Registra no banco de dados
            collection.insertOne(item)

        } catch (error : Throwable) {
            return
        }

    }

    //Retorna todos os resultados de um tipo de jogo da loteria
    suspend fun getAll(loteria: String): List<ResultsCaixa> {
        //Instancializa o serviço com o banco de dados
        val database: MongoDatabase = DatabaseManager.getDatabase()

        val collection: MongoCollection<ResultsCaixa> = database.getCollection<ResultsCaixa>("results")
        val result: List<ResultsCaixa> =
            collection.find<ResultsCaixa>(Document("tipoJogo", loteria)).sort(Document("numero", -1)).toList()
        return result
    }

    //Retorna um concurso de algum jogo da loteria
    suspend fun getByConc(loteria: String, conc: Int): ResultsCaixa? {
        //Instancializa o serviço com o banco de dados
        val database: MongoDatabase = DatabaseManager.getDatabase()

        val collection: MongoCollection<ResultsCaixa> = database.getCollection<ResultsCaixa>("results")
        val result: ResultsCaixa? =
            collection.find<ResultsCaixa>(Document("tipoJogo", loteria).append("numero", conc)).firstOrNull()
        return result
    }

    suspend fun dropCollection() {
        //Instancializa o serviço com o banco de dados
        val database: MongoDatabase = DatabaseManager.getDatabase()
        database.getCollection<ResultsCaixa>("results").drop()
    }

    //Retorna o último resultado
    suspend fun getLastest(loteria: String): ResultsCaixa? {
        //Instancializa o serviço com o banco de dados
        val database: MongoDatabase = DatabaseManager.getDatabase()

        val collection: MongoCollection<ResultsCaixa> = database.getCollection<ResultsCaixa>("results")
        val lastConc: ResultsCaixa? =
            collection.find<ResultsCaixa>(Document("tipoJogo", loteria)).sort(Document("numero", -1)).limit(1)
                .firstOrNull()
        return lastConc

    }
}