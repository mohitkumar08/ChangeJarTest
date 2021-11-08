package com.contactlist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.contact.di.data.Contact
import com.contactlist.databinding.ItemContactBinding
import com.utils.getTextDrawable
import com.utils.loadCircleImage
import kotlin.collections.ArrayList


class ContactAdapter(val context: Context, val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactItemViewHolder>() {
    private var items: ArrayList<Contact> = arrayListOf()

    init {
        setHasStableIds(true)
    }

    fun addContacts(newList: List<Contact>) {
        items.clear()
        items.addAll(newList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactItemViewHolder {
        val binding = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactItemViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size


    override fun onBindViewHolder(holder: ContactItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemId(position: Int): Long {
        return items[position].id
    }

    inner class ContactItemViewHolder(private val binding: ItemContactBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(contact: Contact) {
            with(binding) {
                container.setOnClickListener {
                    itemClickListener.onItemClick(contact)
                }
                contactName.text = contact.name
                contactNumber.text = contact.rawPhone
                if (contact.photoUri == null) {
                    contact.name?.getTextDrawable(contact.contactColor)?.let {
                        contactPhoto.setImageDrawable(it)
                    }
                } else {
                    contact.photoUri!!.loadCircleImage(context, contactPhoto)
                }
                binding.executePendingBindings()
            }
        }
    }
}