package com.example.roomdemo

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository): ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()

    var saveOrUpdateBtnText = MutableLiveData<String>()
    var deleteOrClearAllBtnText = MutableLiveData<String>()

    init {
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
    }

    var subscribers = repository.subscribers

    fun insert(subscriber: Subscriber) = viewModelScope.launch {
        repository.insert(subscriber)
    }

    fun update(subscriber: Subscriber) = viewModelScope.launch {
        repository.update(subscriber)
    }

    fun delete(subscriber: Subscriber) = viewModelScope.launch {
        repository.delete(subscriber)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun saveOrUpdate() {
        val name = name.value!!
        val email = email.value!!
        insert(Subscriber(0, name, email))
        this.name.value = null
        this.email.value = null
    }

    fun deleteOrClearAll() {

    }
}