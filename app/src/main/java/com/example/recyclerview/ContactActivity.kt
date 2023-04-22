package com.example.recyclerview

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.recyclerview.database.Contact
import com.example.recyclerview.database.ContactRoomDB
import com.example.recyclerview.databinding.ActivityContactBinding
import com.example.recyclerview.helper.ContactViewModel
import com.example.recyclerview.repository.ContactRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ContactActivity : AppCompatActivity() {

    companion object {
        const val INTENT_ITEM = "intent_item"
    }

    private lateinit var binding: ActivityContactBinding
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var contactAdapter: ContactAdapter
    private var listContact = arrayListOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onBackPressedDispatcher.addCallback(this, onBackPressedCallback)

        setContactRecycle()
        onAction()
    }

    private fun onAction() {
        binding.tvOption.setOnClickListener {
            if (binding.rv.visibility == View.VISIBLE){
                binding.rv.visibility = View.GONE
                binding.layoutAdd.visibility = View.VISIBLE
            }else {
                binding.rv.visibility = View.VISIBLE
                binding.layoutAdd.visibility = View.GONE
            }
        }

        binding.btnClear.setOnClickListener {
            clearData()
        }

        binding.btnSubmit.setOnClickListener {
            if (binding.etFName.text.isEmpty() || binding.etLName.text.isEmpty() || binding.etNumber.text.isEmpty() || binding.etInstagram.text.isEmpty()){
                Toast.makeText(this, "Please fill in the entire form", Toast.LENGTH_SHORT).show()
            }else{
                var name: String = binding.etFName.text.toString() + " " + binding.etLName.text.toString()
                var number = binding.etNumber.text.toString()
                var nim = binding.etNim.text.toString()
                var instagram = binding.etInstagram.text.toString()
                contactViewModel.addContact(name, number, nim, instagram)
                clearData()
                binding.layoutAdd.visibility = View.GONE
                binding.rv.visibility = View.VISIBLE
            }
        }

        contactAdapter.onItemClick = { contact: Contact ->
            startActivity(Intent(this@ContactActivity, DetailActivity::class.java).putExtra(
                INTENT_ITEM, contact))
        }

        contactAdapter.onDeleteClick = { contact: Contact ->
            contactViewModel.deleteContact(contact)
        }
    }

    private fun setContactRecycle(){
        contactAdapter = ContactAdapter()
        binding.rv.apply {
            layoutManager = LinearLayoutManager(this@ContactActivity)
            adapter = contactAdapter
        }
        contactViewModel = ViewModelProvider(this)[ContactViewModel::class.java]
        contactViewModel.getAllContactsObservers().observe(this, Observer {
            contactAdapter.setContact(ArrayList(it))
            contactAdapter.notifyDataSetChanged()
        })
    }

    private fun clearData(){
        binding.etFName.text.clear()
        binding.etLName.text.clear()
        binding.etNumber.text.clear()
        binding.etInstagram.text.clear()
        binding.etNim.text.clear()
    }

    private val onBackPressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.layoutAdd.visibility == View.VISIBLE) {
                binding.rv.visibility = View.VISIBLE
                binding.layoutAdd.visibility = View.GONE
            }else{
                showAppClosingDialog()
            }
        }
    }

    private fun showAppClosingDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Warning")
            .setMessage("Do you really want to close the app?")
            .setPositiveButton("Yes") { _, _ -> finish() }
            .setNegativeButton("No", null)
            .show()
    }
}