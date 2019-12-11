package com.example.mobv.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobv.model.database.domain.ContactMessageEntity

@Dao
interface ContactMessagesDao {

    @Query("select * from contact_message where uid = :uid or contact_name = :contactName order by time desc")
    fun getMessagesByUsers(uid: String, contactName: String): LiveData<List<ContactMessageEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(contactMessage: ContactMessageEntity)
}