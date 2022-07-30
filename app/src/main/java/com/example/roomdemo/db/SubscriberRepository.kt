package com.example.roomdemo.db

class SubscriberRepository(private val subscriberDao: SubscriberDao) {

    val subscribers = subscriberDao.getAllSubscribers()

    suspend fun insert(subscriber: Subscriber): Long {
        return subscriberDao.insertSubscriber(subscriber)
    }

    suspend fun update(subscriber: Subscriber): Int {
        return subscriberDao.updateSubscriber(subscriber)
    }

    suspend fun delete(subscriber: Subscriber): Int {
        return subscriberDao.deleteSubscriber(subscriber)
    }

    suspend fun deleteAll(): Int {
        return subscriberDao.deleteAll()
    }
}