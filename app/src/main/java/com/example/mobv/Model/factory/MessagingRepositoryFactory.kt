package com.example.mobv.Model.factory

import com.example.mobv.Model.IMessagingRepository
import com.example.mobv.Model.MessagingRepository
import com.example.mobv.Model.mock.FakeMessagingRepository

class MessagingRepositoryFactory {

    companion object {

        fun create(): IMessagingRepository {
            return FakeMessagingRepository()
        }

    }
}
