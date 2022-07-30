package com.example.roomdemo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.roomdemo.db.Subscriber
import com.example.roomdemo.db.SubscriberRepository
import kotlinx.coroutines.launch

class SubscriberViewModel(private val repository: SubscriberRepository) : ViewModel() {

    var name = MutableLiveData<String>()
    var email = MutableLiveData<String>()

    private var isUpdateOrDelete = false
    private var subscriberToUpdateOrDelete: Subscriber? = null
    var saveOrUpdateBtnText = MutableLiveData<String>()
    var deleteOrClearAllBtnText = MutableLiveData<String>()

    private var dbOperationStatus = MutableLiveData<Event<String>>()
    val operationStatus: LiveData<Event<String>>
        get() = dbOperationStatus

    init {
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
    }

    var subscribers = repository.subscribers

    private fun insert(subscriber: Subscriber) = viewModelScope.launch {
        val rowId = repository.insert(subscriber)
        dbOperationStatus.value = if (rowId > -1) {
            Event("Subscriber inserted successfully.")
        } else {
            Event("Error occurred while saving a subscriber.")
        }
    }

    private fun update(subscriber: Subscriber) = viewModelScope.launch {
        val updatedRows = repository.update(subscriber)
        dbOperationStatus.value = if (updatedRows > 0) {
            Event("Subscriber updated successfully.")
        } else {
            Event("Error occurred while updating a subscriber.")
        }
    }

    private fun delete(subscriber: Subscriber) = viewModelScope.launch {
        val deletedRows = repository.delete(subscriber)
        dbOperationStatus.value = if (deletedRows > 0) {
            Event("Subscriber deleted successfully.")
        } else {
            Event("Error occurred while deleting a subscriber.")
        }
    }

    private fun deleteAll() = viewModelScope.launch {
        val deletedRows = repository.deleteAll()
        dbOperationStatus.value = if (deletedRows > 0) {
            Event("All subscribers deleted successfully.")
        } else {
            Event("Error occurred while deleting all subscribers.")
        }
    }

    fun saveOrUpdate() {
        if (name.value.isNullOrEmpty() || email.value.isNullOrEmpty()) {
            dbOperationStatus.value =
                Event("Subscriber name and email address are mandatory fields.")
            return
        }
        val name = name.value!!
        val email = email.value!!
        if (isUpdateOrDelete) {
            subscriberToUpdateOrDelete?.name = name
            subscriberToUpdateOrDelete?.email = email
            update(subscriberToUpdateOrDelete!!)
        } else {
            insert(Subscriber(0, name, email))
        }
        this.name.value = null
        this.email.value = null
        isUpdateOrDelete = false
        subscriberToUpdateOrDelete = null
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
    }

    fun initUpdateOrDelete(subscriber: Subscriber) {
        subscriberToUpdateOrDelete = subscriber
        isUpdateOrDelete = true
        saveOrUpdateBtnText.value = "Update"
        deleteOrClearAllBtnText.value = "Delete"
        this.name.value = subscriber.name
        this.email.value = subscriber.email
    }

    fun deleteOrClearAll() {
        if (isUpdateOrDelete) {
            delete(subscriberToUpdateOrDelete!!)
        } else {
            deleteAll()
        }
        this.name.value = null
        this.email.value = null
        isUpdateOrDelete = false
        subscriberToUpdateOrDelete = null
        saveOrUpdateBtnText.value = "Save"
        deleteOrClearAllBtnText.value = "Clear All"
    }
}