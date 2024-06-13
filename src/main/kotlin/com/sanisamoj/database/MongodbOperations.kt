package com.sanisamoj.database

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.toList
import org.bson.Document
import javax.print.Doc

class MongodbOperations {

    // Adds an item to the database
    suspend inline fun <reified T: Any>register(collectionName: CollectionsInDb, item: T) {

        // Returns the database
        val database = MongoDatabase.getDatabase()

        // Returns the collection
        val collection = database.getCollection<T>(collectionName.title)

        collection.insertOne(item)

        return
    }

    // Returns a specific item
    suspend inline fun <  reified T : Any> findOneWithSpecificDocument(collectionName: CollectionsInDb, document: Document, sort: Document = Document()): T? {
        val database = MongoDatabase.getDatabase()
        val collection = database.getCollection<T>(collectionName.title)
        val result: T? = collection.find<T>(document).sort(sort).limit(1).firstOrNull()

        return result
    }

    // Returns a specific item
    suspend inline fun <  reified T : Any> findAllWithSpecificDocument(collectionName: CollectionsInDb, filter: OperationField, sort: Document): List<T> {
        val database = MongoDatabase.getDatabase()
        val collection = database.getCollection<T>(collectionName.title)
        val result: List<T> = collection.find<T>(Document(filter.field.title, filter.value)).sort(sort).toList()

        return result
    }

    // Returns an item
    suspend inline fun <reified T : Any> findOne(collectionName: CollectionsInDb, filter: OperationField): T? {
        val database = MongoDatabase.getDatabase()
        val collection = database.getCollection<T>(collectionName.name)
        val result: T? = collection.find<T>(Document(filter.field.title, filter.value)).firstOrNull()

        return result
    }
}