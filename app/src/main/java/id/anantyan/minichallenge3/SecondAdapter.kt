package id.anantyan.minichallenge3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import id.anantyan.minichallenge3.databinding.ListItemSecondBinding

class SecondAdapter : ListAdapter<String, SecondAdapter.MainModelViewHolder>(MainModelComparator) {

    private object MainModelComparator : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainModelViewHolder {
        return MainModelViewHolder(
            ListItemSecondBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class MainModelViewHolder(private val binding: ListItemSecondBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: String) {
            binding.txtBio.text = item
        }
    }
}