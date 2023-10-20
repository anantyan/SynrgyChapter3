package id.anantyan.challengechapter3.presentation.detail

import android.view.View
import id.anantyan.challengechapter3.data.words.WordsModel

interface DetailInteraction {
    fun onClick(position: Int, item: WordsModel, view: View)
}