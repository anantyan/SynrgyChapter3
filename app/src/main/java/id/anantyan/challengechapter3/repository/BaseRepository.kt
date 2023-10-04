package id.anantyan.challengechapter3.repository

import id.anantyan.challengechapter3.model.AlphabetModel
import id.anantyan.challengechapter3.model.WordsModel
import kotlinx.coroutines.flow.Flow

interface BaseRepository {
    suspend fun listAlphabet(): List<AlphabetModel>
    fun listWord(key: String): List<WordsModel>
}