package com.firman.submission.data

import com.firman.submission.model.FakeDataSource
import com.firman.submission.model.GenshinCharacter
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ListCharacterRepository {
    private val listCharacter = mutableListOf<Character>()

    fun getAllCharacter(): Flow<List<GenshinCharacter>> {
        return flowOf(FakeDataSource.dummyListCharacter)
    }

    fun getCharacterById(id: Long): GenshinCharacter? {
        return FakeDataSource.dummyListCharacter.find { it.id.toInt().toLong() == id }
    }

    companion object{
        @Volatile
        private var instance: ListCharacterRepository? = null

        fun getInstance(): ListCharacterRepository {
            return instance ?: synchronized(this) {
                instance ?: ListCharacterRepository().also { instance = it }
            }
        }
    }
}