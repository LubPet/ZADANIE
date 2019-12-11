package com.example.mobv.model.database.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "contact")
class ContactEntity(@PrimaryKey @ColumnInfo(name = "uid") val uid: String, @ColumnInfo(name = "name") val name: String)