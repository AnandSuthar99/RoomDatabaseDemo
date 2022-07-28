package com.example.roomdemo

import android.os.Bundle
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

        activityMainBinding.subscriberViewModel = subscriberViewModel
        activityMainBinding.lifecycleOwner = this

        subscribersAdapter = SubscribersAdapter(subscribers)
        activityMainBinding.rvSubscribers.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        activityMainBinding.rvSubscribers.adapter = subscribersAdapter

        activityMainBinding.btnSubmit.setOnClickListener {
            val name = activityMainBinding.etName.text.toString()
            val email = activityMainBinding.etEmail.text.toString()

            val newSubscriber = Subscriber(0, name, email)
            subscriberViewModel.insert(newSubscriber)
        }

        activityMainBinding.btnClearAll.setOnClickListener {
            subscriberViewModel.deleteAll()
        }
    }

    override fun onStart() {
        super.onStart()
        subscriberViewModel.subscribers.observe(this) {
            subscribers.clear()
            subscribers.addAll(it)
            subscribersAdapter.notifyDataSetChanged()
        }
    }
}