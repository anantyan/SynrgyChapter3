package id.anantyan.challengechapter3.presentation.home

import android.view.View
import id.anantyan.challengechapter3.data.alphabet.AlphabetModel

interface HomeInteraction {
    fun onClick(position: Int, item: AlphabetModel, view: View)
    fun onLongClick(position: Int, item: AlphabetModel, view: View)
}