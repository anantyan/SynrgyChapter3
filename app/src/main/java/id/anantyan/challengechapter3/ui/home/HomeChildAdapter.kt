package id.anantyan.challengechapter3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import id.anantyan.challengechapter3.databinding.ListItemChildBinding
import id.anantyan.challengechapter3.model.WordsModel

class HomeChildAdapter : ListAdapter<WordsModel, HomeChildAdapter.WordsModelViewHolder>(WordsModelComparator) {

    private object WordsModelComparator : DiffUtil.ItemCallback<WordsModel>() {
        override fun areItemsTheSame(oldItem: WordsModel, newItem: WordsModel): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: WordsModel, newItem: WordsModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsModelViewHolder {
        return WordsModelViewHolder(
            ListItemChildBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordsModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class WordsModelViewHolder(private val binding: ListItemChildBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(item: WordsModel) {
            binding.txtAbjad.text = item.word
        }
    }
}