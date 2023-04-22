package com.example.recyclerview.helper

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.recyclerview.database.Contact
import com.example.recyclerview.database.ContactRoomDB
import com.example.recyclerview.database.DAOContact
import com.example.recyclerview.repository.ContactRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ContactViewModel(application: Application) : AndroidViewModel(application){

    private val repository: ContactRepository
    private val contactDao: DAOContact

    lateinit var allContacts : MutableLiveData<List<Contact>>

    private var vmJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + vmJob)

    init {
        contactDao = ContactRoomDB.getDatabase(application).daoContact()
        repository = ContactRepository(contactDao)
        allContacts = MutableLiveData()
        getAllContacts()
    }

    fun getAllContactsObservers(): MutableLiveData<List<Contact>> {
        return allContacts
    }

    private fun getAllContacts(){
        val list = contactDao.getAllContact()
        allContacts.postValue(list)
    }

    fun addContact(name: String, number: String, nim: String, instagram:String){
        uiScope.launch {
            repository.addContact(Contact(0, name, number, nim, instagram))
            getAllContacts()
        }
    }

    fun updateContact(name: String, number: String, nim: String, instagram:String){
        uiScope.launch {
            repository.updateContact(Contact(0, name, number, nim, instagram))
            getAllContacts()
        }
    }

    fun deleteContact(contact: Contact){
        uiScope.launch {
            repository.deleteContact(contact)
            getAllContacts()
        }
    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}