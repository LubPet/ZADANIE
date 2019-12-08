package com.example.mobv.Model.factory

import com.example.mobv.Model.repository.IMessagingRepository
import com.example.mobv.Model.repository.MessagingRepository

class MessagingRepositoryFactory {

    companion object {

        fun create(): IMessagingRepository {
            return MessagingRepository()
        }

    }
}
