package com.example.recyclerview.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1)
abstract class ContactRoomDB : RoomDatabase(){
    abstract fun daoContact() : DAOContact
    companion object {
        @Volatile
        private var INSTANCE: ContactRoomDB? = null
        @JvmStatic
        fun getDatabase(context: Context) :ContactRoomDB {
            if (INSTANCE == null){
                synchronized(ContactRoomDB::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext, ContactRoomDB::class.java, "contact_db")
                        .fallbackToDestructiveMigration()
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as ContactRoomDB
        }
    }
}