package com.example.recyclerview.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DAOContact {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("SELECT * FROM contact ORDER BY id ASC")
    fun getAllContact(): List<Contact>
}