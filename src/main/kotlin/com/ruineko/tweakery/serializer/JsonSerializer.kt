package com.ruineko.tweakery.serializer

import com.google.gson.Gson
import com.google.gson.JsonParser

object JsonSerializer {
    private val gson = Gson()
    private const val NAMESPACE_KEY = "namespace"

    @JvmStatic
    fun <T> serialize(namespace: String, value: T): String {
        val json = gson.toJsonTree(value).asJsonObject
        json.addProperty(NAMESPACE_KEY, namespace)
        return gson.toJson(json)
    }

    @JvmStatic
    fun <T> deserialize(namespace: String, json: String, type: Class<T>): T? {
        return try {
            val objectJson = JsonParser.parseString(json).asJsonObject

            if (objectJson.get(NAMESPACE_KEY)?.asString != namespace) {
                return null
            }

            gson.fromJson(objectJson, type)
        } catch (_: Exception) {
            null
        }
    }
}