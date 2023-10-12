package id.anantyan.challengechapter3.ui.detail

import android.view.View
import id.anantyan.challengechapter3.model.WordsModel

interface DetailInteraction {
    fun onClick(position: Int, item: WordsModel, view: View)
    fun onLongClick(position: Int, item: WordsModel, view: View)
}