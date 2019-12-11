package com.example.mobv.model.factory

import com.example.mobv.model.repository.MessagingRepository
import com.example.mobv.model.repositorymessage.IMessagingRepository

class MessagingRepositoryFactory {

    companion object {

        fun create(): IMessagingRepository {
            return MessagingRepository()
        }

    }
}
