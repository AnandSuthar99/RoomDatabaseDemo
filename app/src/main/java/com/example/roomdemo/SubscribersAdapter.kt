package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.LayoutSubscriberListItemBinding
import com.example.roomdemo.db.Subscriber

class SubscribersAdapter(private val subscribers: List<Subscriber>):
    RecyclerView.Adapter<SubscribersAdapter.SubscriberViewHolder>() {

    class SubscriberViewHolder(val binding: LayoutSubscriberListItemBinding): RecyclerView.ViewHolder(binding.root) {
        init {
            binding.cvSubscriber.setOnClickListener {
                Toast.makeText(
                    binding.root.context, "Clicked Item is ${binding.subscriber?.name}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val binding: LayoutSubscriberListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context),
        R.layout.layout_subscriber_list_item, parent, false)
        return SubscriberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.binding.subscriber = subscribers[position]
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }
}