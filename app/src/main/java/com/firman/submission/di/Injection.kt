package com.firman.submission.di

import com.firman.submission.data.ListCharacterRepository

object Injection {
    fun provideRepository(): ListCharacterRepository {
        return ListCharacterRepository.getInstance()
    }
}
