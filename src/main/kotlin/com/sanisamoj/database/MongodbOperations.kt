package com.sanisamoj.database

import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.Document
import org.bson.types.ObjectId

class MongodbOperations {

    // Adds an item to the database
    suspend inline fun <reified T : Any> register(collectionInDb: CollectionsInDb, item: T): ObjectId {

        // Returns the database
        val database = MongoDatabase.getDatabase()

        // Returns the collection
        val collection = database.getCollection<T>(collectionInDb.name)

        val id = collection.insertOne(item).insertedId?.asObjectId()?.value
        return id!!

    }

    // Returns a specific item
    suspend inline fun <  reified T : Any> findOneWithSpecificDocument(collectionInDb: CollectionsInDb, document: Document, sort: Document = Document()): T? {
        val database = MongoDatabase.getDatabase()
        val collection = database.getCollection<T>(collectionInDb.name)
        val result: T? = collection.find<T>(document).sort(sort).limit(1).firstOrNull()

        return result
    }

    // Returns a specific item
    suspend inline fun <  reified T : Any> findAllWithSpecificDocument(collectionInDb: CollectionsInDb, filter: OperationField, sort: Document): List<T> {
        val database = MongoDatabase.getDatabase()
        val collection = database.getCollection<T>(collectionInDb.name)
        val result: List<T> = collection.find<T>(Document(filter.field.title, filter.value)).sort(sort).toList()

        return result
    }

    // Returns an item
    suspend inline fun <reified T : Any> findOne(collectionInDb: CollectionsInDb, filter: OperationField): T? {
        val database = MongoDatabase.getDatabase()
        val collection = database.getCollection<T>(collectionInDb.name)
        val result: T? = collection.find<T>(Document(filter.field.title, filter.value)).firstOrNull()

        return result
    }
}