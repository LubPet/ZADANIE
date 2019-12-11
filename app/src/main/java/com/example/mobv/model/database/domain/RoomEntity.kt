package com.example.mobv.model.database.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "room")
class RoomEntity(@PrimaryKey @ColumnInfo(name = "roomid") val roomid: String, @ColumnInfo(name = "uid") val uid: String, @ColumnInfo(name = "time") val time: Date)