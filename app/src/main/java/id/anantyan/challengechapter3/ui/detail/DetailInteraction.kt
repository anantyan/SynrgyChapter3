package id.anantyan.challengechapter3.ui.detail

import id.anantyan.challengechapter3.model.WordsModel

interface DetailInteraction {
    fun onClick(position: Int, item: WordsModel)
}