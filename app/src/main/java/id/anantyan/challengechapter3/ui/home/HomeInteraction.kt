package id.anantyan.challengechapter3.ui.home

import android.view.View
import id.anantyan.challengechapter3.model.AlphabetModel

interface HomeInteraction {
    fun onClick(position: Int, item: AlphabetModel, view: View)
}