package com.example.mobv.Model.serialization

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class StringToListDeserializer<T> : Deserializer<String, List<T>> {

    override fun deserialize(body: String): List<T> {
        val itemType = object : TypeToken<List<T>>() {}.type
        return Gson().fromJson(body, itemType)
    }

}
