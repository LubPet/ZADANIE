package com.example.mobv.model.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mobv.model.database.domain.ContactEntity

@Dao
interface ContactsDao {

    @Query("select * from contact where uid = :uid order by uid desc")
    fun getContactsById(uid: String): LiveData<List<ContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContact(contact: ContactEntity)

}