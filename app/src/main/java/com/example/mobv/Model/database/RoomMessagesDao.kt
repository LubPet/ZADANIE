package com.example.mobv.Model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobv.Model.database.domain.RoomMessageEntity

@Dao
interface RoomMessagesDao {

    @Query("select * from room_message where roomid = :roomId order by time desc")
    fun getMessagesByRoomId(roomId: String): LiveData<List<RoomMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(roomMessage: RoomMessageEntity)
}