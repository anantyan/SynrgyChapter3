package id.anantyan.minichallenge3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.minichallenge3.databinding.ListItemMainBinding

class MainAdapter : ListAdapter<MainAdapterModel, MainAdapter.MainModelViewHolder>(MainModelComparator) {

    private var _onInteraction: MainInteraction? = null

    private object MainModelComparator : DiffUtil.ItemCallback<MainAdapterModel>() {
        override fun areItemsTheSame(oldItem: MainAdapterModel, newItem: MainAdapterModel): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: MainAdapterModel, newItem: MainAdapterModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainModelViewHolder {
        return MainModelViewHolder(
            ListItemMainBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class MainModelViewHolder(private val binding: ListItemMainBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                _onInteraction?.let {
                    it.onClick(bindingAdapterPosition, getItem(bindingAdapterPosition))
                }
            }
        }

        fun bindItem(item: MainAdapterModel) {
            binding.txtTitle.text = item.title
            binding.txtDescription.text = item.description
        }
    }

    fun onInteraction(listener: MainInteraction) {
        _onInteraction = listener
    }
}