package id.anantyan.challengechapter3.domain.base

import id.anantyan.challengechapter3.data.alphabet.AlphabetModel
import id.anantyan.challengechapter3.data.words.WordsModel

interface BaseRepository {
    suspend fun listAlphabet(): List<AlphabetModel>
    fun listWord(key: String): List<WordsModel>
}