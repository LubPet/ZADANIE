package com.example.mobv.model.factory

import com.example.mobv.model.repository.IMessagingRepository
import com.example.mobv.model.repository.MessagingRepository

class MessagingRepositoryFactory {

    companion object {

        fun create(): IMessagingRepository {
            return MessagingRepository()
        }

    }
}
