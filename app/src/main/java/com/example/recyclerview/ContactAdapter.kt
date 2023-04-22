package com.example.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.database.Contact
import com.example.recyclerview.databinding.ItemListBinding

class ContactAdapter : RecyclerView.Adapter<ContactAdapter.ViewHolder> () {
    lateinit var onItemClick: ((Contact) -> Unit)
    lateinit var onDeleteClick: ((Contact) -> Unit)

    var contactList = arrayListOf<Contact>()

    fun setContact(notes: ArrayList<Contact>) {
        contactList.clear()
        contactList.addAll(notes)
        notifyDataSetChanged()
    }

    class ViewHolder (var binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = contactList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactList[position]
        holder.binding.tvName.text = contact.name
        holder.binding.tvNim.text = "NIM: " + contact.nim
        holder.binding.tvNumber.text = contact.number

        holder.itemView.setOnClickListener {
            onItemClick.invoke(contact)
        }

        holder.binding.tvDelete.setOnClickListener {
            onDeleteClick.invoke(contact)
        }
    }
}

