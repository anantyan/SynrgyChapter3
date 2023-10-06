package id.anantyan.challengechapter3.ui.detail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.challengechapter3.databinding.ListItemDetailBinding
import id.anantyan.challengechapter3.databinding.ListItemHomeBinding
import id.anantyan.challengechapter3.model.WordsModel
import id.anantyan.challengechapter3.ui.home.HomeInteraction

class DetailAdapter : ListAdapter<WordsModel, DetailAdapter.KeyModelViewHolder>(KeyModelComparator) {

    private var _onInteraction: DetailInteraction? = null

    private object KeyModelComparator : DiffUtil.ItemCallback<WordsModel>() {
        override fun areItemsTheSame(oldItem: WordsModel, newItem: WordsModel): Boolean {
            return oldItem.word == newItem.word
        }

        override fun areContentsTheSame(oldItem: WordsModel, newItem: WordsModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyModelViewHolder {
        return KeyModelViewHolder(
            ListItemDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KeyModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class KeyModelViewHolder(private val binding: ListItemDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onInteraction?.let {
                    it.onClick(bindingAdapterPosition, getItem(bindingAdapterPosition), binding.root)
                }
            }
        }

        fun bindItem(item: WordsModel) {
            binding.root.transitionName = item.word
            binding.txtAbjad.text = item.word
        }
    }

    fun onInteraction(listener: DetailInteraction) {
        _onInteraction = listener
    }
}