package com.example.mobv.Model.serialization

interface Deserializer<T, U> {

    fun deserialize(body: T): U
}
