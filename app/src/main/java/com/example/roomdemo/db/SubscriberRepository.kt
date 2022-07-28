package com.example.roomdemo.db

class SubscriberRepository(private val subscriberDao: SubscriberDao) {

    val subscribers = subscriberDao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber) {
        subscriberDao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber) {
        subscriberDao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber) {
        subscriberDao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll() {
        subscriberDao.deleteAll()
    }
}