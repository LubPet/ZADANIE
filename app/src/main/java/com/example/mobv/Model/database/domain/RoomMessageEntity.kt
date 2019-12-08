package com.example.mobv.Model.database.domain

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "room_message")
class RoomMessageEntity(@PrimaryKey @ColumnInfo(name = "uid") val uid: String,
                        @ColumnInfo(name = "roomid") val roomid: String,
                        @ColumnInfo(name = "message") val message: String,
                        @ColumnInfo(name = "time") val time: Date,
                        @ColumnInfo(name = "uid_name") val uid_name: String,
                        @ColumnInfo(name = "contact_name") val contact_name: String,
                        @ColumnInfo(name = "uid_fid") val uid_fid: String,
                        @ColumnInfo(name = "contact_fid") val contact_fid: String)
