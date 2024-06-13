package com.sanisamoj.models.repository

import com.sanisamoj.database.CollectionsInDb
import com.sanisamoj.database.Fields
import com.sanisamoj.database.MongodbOperations
import com.sanisamoj.database.OperationField
import com.sanisamoj.models.generics.ResultsCaixa
import com.sanisamoj.models.interfaces.LoteriaRepository
import org.bson.Document
import org.bson.types.ObjectId

class LoteriaDefaultRepository : LoteriaRepository {
    override suspend fun register(result: ResultsCaixa): ResultsCaixa {
        val resultIsExists = resultAlreadyExist(result.tipoJogo, result.numero)
        if (resultIsExists != null) throw Exception("Result already exists!")

        // Altera o nome da loteria
        val loteria: String = result.tipoJogo
            .lowercase()
            .replace("_", "")
            .replace("loteria", "")

        val item = result.copy(tipoJogo = loteria, id_mongo = ObjectId())
        val resultId = MongodbOperations().register<ResultsCaixa>(
            collectionName = CollectionsInDb.Results,
            item = item
        ).toString()

        return getResultById(resultId)
    }

    private suspend fun resultAlreadyExist(loteria: String, conc: Int): ResultsCaixa? {
        return MongodbOperations().findOneWithSpecificDocument<ResultsCaixa>(
            collectionName = CollectionsInDb.Results,
            document = Document(Fields.Game.title, loteria).append(Fields.Number.title, conc)
        )
    }

    private suspend fun getResultById(id: String): ResultsCaixa {
        return MongodbOperations().findOne<ResultsCaixa>(
            collectionName = CollectionsInDb.Results,
            filter = OperationField(Fields.Id, ObjectId(id))
        ) ?: throw Exception()
    }

    override suspend fun getAllResults(loteria: String): List<ResultsCaixa> {
        return MongodbOperations().findAllWithSpecificDocument<ResultsCaixa>(
            collectionName = CollectionsInDb.Results,
            filter = OperationField(Fields.Game, loteria),
            sort = Document(Fields.Number.title, -1)
        )
    }

    override suspend fun getByConc(loteria: String, conc: Int): ResultsCaixa {
        return MongodbOperations().findOneWithSpecificDocument<ResultsCaixa>(
            collectionName = CollectionsInDb.Results,
            document = Document(Fields.Game.title, loteria).append(Fields.Number.title, conc)
        ) ?: throw Exception()
    }

    override suspend fun getLatest(loteria: String): ResultsCaixa {
        return MongodbOperations().findOneWithSpecificDocument<ResultsCaixa>(
            collectionName = CollectionsInDb.Results,
            document = Document(Fields.Game.title, loteria),
            sort = Document(Fields.Number.title, -1)
        )!!
    }
}