package com.example.recyclerview.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.recyclerview.database.Contact
import com.example.recyclerview.database.ContactRoomDB
import com.example.recyclerview.database.DAOContact
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ContactRepository (private val daoContact: DAOContact) {
    val allContacts = daoContact.getAllContact()

    suspend fun addContact(contact: Contact){
        daoContact.insert(contact)
    }

    suspend fun updateContact(contact: Contact){
        daoContact.update(contact)
    }

    suspend fun deleteContact(contact: Contact){
        daoContact.delete(contact)
    }

}