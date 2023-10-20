package id.anantyan.challengechapter3.data.alphabet

import id.anantyan.challengechapter3.data.words.WordsModel

data class AlphabetModel(
    val key: String? = null,
    val list: List<WordsModel>? = listOf()
)
