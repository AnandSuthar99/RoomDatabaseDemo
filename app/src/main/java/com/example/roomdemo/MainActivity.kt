package com.example.roomdemo

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdemo.databinding.ActivityMainBinding
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberDao
import com.example.roomdemo.db.SubscriberDatabase
import com.example.roomdemo.db.SubscriberRepository

class MainActivity : AppCompatActivity() {

    private lateinit var subscribersAdapter: SubscribersAdapter
    private lateinit var activityMainBinding: ActivityMainBinding
    private lateinit var subscriberViewModel: SubscriberViewModel
    private lateinit var factory: SubscriberViewModelFactory
    private lateinit var subscriberDao: SubscriberDao
    private var subscribers = ArrayList<Subscriber>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        subscriberDao = SubscriberDatabase.getInstance(applicationContext).subscriberDao

        val subscriberRepository = SubscriberRepository(subscriberDao)
        factory = SubscriberViewModelFactory(subscriberRepository)
        subscriberViewModel = ViewModelProvider(this, factory)[SubscriberViewModel::class.java]

        subscribersAdapter =
            SubscribersAdapter(subscribers, object : SubscribersAdapter.SubscriberClickListener {
                override fun onSubscriberClicked(subscriber: Subscriber) {
                    subscriberViewModel.initUpdateOrDelete(subscriber)
                }
            })

        activityMainBinding.subscriberViewModel = subscriberViewModel
        activityMainBinding.lifecycleOwner = this

        activityMainBinding.rvSubscribers.layoutManager =
            LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        activityMainBinding.rvSubscribers.adapter = subscribersAdapter

        subscriberViewModel.subscribers.observe(this) {
            subscribers.clear()
            subscribers.addAll(it)
            subscribersAdapter.notifyDataSetChanged()
        }

        subscriberViewModel.operationStatus.observe(this) { it ->
            it.getContentIfNotHandled()?.let {
                Toast.makeText(MainActivity@ this, it, Toast.LENGTH_SHORT).show()
            }
        }
    }
}