package com.example.mobv.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobv.model.database.domain.RoomEntity

@Dao
interface RoomsDao {

    @Query("select * from room where uid = :uid order by roomid desc")
    fun getRoomsByUid(uid: String): LiveData<List<RoomEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRoom(room: RoomEntity)
}