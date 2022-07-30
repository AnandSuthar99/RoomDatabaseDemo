package com.example.roomdemo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.LayoutSubscriberListItemBinding
import com.example.roomdemo.db.Subscriber

class SubscribersAdapter(
    private val subscribers: List<Subscriber>,
    private var subscriberClickListener: SubscriberClickListener
) :
    RecyclerView.Adapter<SubscribersAdapter.SubscriberViewHolder>() {

    class SubscriberViewHolder(private val binding: LayoutSubscriberListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(subscriber: Subscriber, onClickListener: SubscriberClickListener) {
            binding.subscriber = subscriber
            binding.cvSubscriber.setOnClickListener {
                onClickListener.onSubscriberClicked(subscriber)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubscriberViewHolder {
        val binding: LayoutSubscriberListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_subscriber_list_item, parent, false
        )
        return SubscriberViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubscriberViewHolder, position: Int) {
        holder.bind(subscribers[position], subscriberClickListener)
    }

    override fun getItemCount(): Int {
        return subscribers.size
    }

    interface SubscriberClickListener {
        fun onSubscriberClicked(subscriber: Subscriber)
    }
}