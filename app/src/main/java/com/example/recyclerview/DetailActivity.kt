package com.example.recyclerview

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import com.example.recyclerview.database.Contact
import com.example.recyclerview.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var contact: Contact

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        contact = intent.getParcelableExtra(ContactActivity.INTENT_ITEM)!!

        contact?.let {
            binding.tvName.text = contact.name
            binding.tvNumber.text = contact.number
            binding.tvNim.text = "NIM: " + contact.nim
        }

        binding.backLink.setOnClickListener {
            finish()
        }

        binding.btnCall.setOnClickListener {
            dialPhone(contact.number)
        }

        binding.tvWhatsapp.setOnClickListener {
            sendWhatsapp(contact.number)
        }

        binding.btnMessage.setOnClickListener {
            sendMessage(contact.number)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialPhone(phoneNumber:String){
        val callIntent = Intent(Intent.ACTION_DIAL)
        callIntent.data = Uri.parse("tel:$phoneNumber")
        try {
            startActivity(Intent.createChooser(callIntent, "Choose"))
        } catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendMessage(phoneNumber: String){
        val messageIntent = Intent(Intent.ACTION_SENDTO)
        messageIntent.data = Uri.parse("sms:$phoneNumber")
        try {
            startActivity(Intent.createChooser(messageIntent, "Choose"))
        } catch (e: Exception){
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendWhatsapp(phoneNumber: String){
        val url = "https://api.whatsapp.com/send?phone=$phoneNumber"
        val whatsappIntent = Intent(Intent.ACTION_VIEW)
        whatsappIntent.data = Uri.parse(url)

        try {
            startActivity(whatsappIntent)
        } catch (e:Exception) {
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
        }
    }
}