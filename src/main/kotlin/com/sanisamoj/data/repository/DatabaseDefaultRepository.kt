package com.sanisamoj.data.repository

import com.sanisamoj.data.models.dataclass.ResultsApiResponse
import com.sanisamoj.data.models.enums.Fields
import com.sanisamoj.data.models.interfaces.DatabaseRepository
import com.sanisamoj.database.CollectionsInDb
import com.sanisamoj.database.MongodbOperations
import com.sanisamoj.database.OperationField
import org.bson.Document
import org.bson.types.ObjectId

class DatabaseDefaultRepository : DatabaseRepository {
    override suspend fun register(result: ResultsApiResponse): ResultsApiResponse {
        val resultIsExists = resultAlreadyExist(result.tipoJogo, result.numero)
        if (resultIsExists != null) throw Exception("Result already exists!")

        val loteria: String = result.tipoJogo
            .lowercase()
            .replace("_", "")
            .replace("loteria", "")

        val item = result.copy(tipoJogo = loteria, id_mongo = ObjectId())
        val resultId = MongodbOperations().register<ResultsApiResponse>(
            collectionInDb = CollectionsInDb.Results,
            item = item
        ).toString()

        return getResultById(resultId)
    }

    private suspend fun resultAlreadyExist(loteria: String, conc: Int): ResultsApiResponse? {
        return MongodbOperations().findOneWithSpecificDocument<ResultsApiResponse>(
            collectionInDb = CollectionsInDb.Results,
            document = Document(Fields.Game.title, loteria).append(Fields.Number.title, conc)
        )
    }

    private suspend fun getResultById(id: String): ResultsApiResponse {
        return MongodbOperations().findOne<ResultsApiResponse>(
            collectionInDb = CollectionsInDb.Results,
            filter = OperationField(Fields.Id, ObjectId(id))
        ) ?: throw Exception()
    }

    override suspend fun getAllResults(loteria: String): List<ResultsApiResponse> {
        return MongodbOperations().findAllWithSpecificDocument<ResultsApiResponse>(
            collectionInDb = CollectionsInDb.Results,
            filter = OperationField(Fields.Game, loteria),
            sort = Document(Fields.Number.title, -1)
        )
    }

    override suspend fun getByConc(loteria: String, conc: Int): ResultsApiResponse {
        return MongodbOperations().findOneWithSpecificDocument<ResultsApiResponse>(
            collectionInDb = CollectionsInDb.Results,
            document = Document(Fields.Game.title, loteria).append(Fields.Number.title, conc)
        ) ?: throw Exception()
    }

    override suspend fun getLatest(loteria: String): ResultsApiResponse {
        return MongodbOperations().findOneWithSpecificDocument<ResultsApiResponse>(
            collectionInDb = CollectionsInDb.Results,
            document = Document(Fields.Game.title, loteria),
            sort = Document(Fields.Number.title, -1)
        )!!
    }
}