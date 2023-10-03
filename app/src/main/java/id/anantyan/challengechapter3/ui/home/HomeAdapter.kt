package id.anantyan.challengechapter3.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import id.anantyan.challengechapter3.databinding.ListItemHomeBinding
import id.anantyan.challengechapter3.model.AlphabetModel

class HomeAdapter : ListAdapter<AlphabetModel, HomeAdapter.KeyModelViewHolder>(KeyModelComparator) {

    private var _onClick: ((position: Int, item: AlphabetModel, view: View) -> Unit)? = null

    private object KeyModelComparator : DiffUtil.ItemCallback<AlphabetModel>() {
        override fun areItemsTheSame(oldItem: AlphabetModel, newItem: AlphabetModel): Boolean {
            return oldItem.key == newItem.key
        }

        override fun areContentsTheSame(oldItem: AlphabetModel, newItem: AlphabetModel): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeyModelViewHolder {
        return KeyModelViewHolder(
            ListItemHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: KeyModelViewHolder, position: Int) {
        holder.bindItem(getItem(position))
    }

    inner class KeyModelViewHolder(private val binding: ListItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                _onClick?.let {
                    it(bindingAdapterPosition, getItem(bindingAdapterPosition), binding.root)
                }
            }
        }

        fun bindItem(item: AlphabetModel) {
            val adapter = HomeChildAdapter()

            binding.root.transitionName = item.key
            binding.txtAbjad.text = item.key
            binding.rvChild.setHasFixedSize(true)
            binding.rvChild.itemAnimator = DefaultItemAnimator()
            binding.rvChild.layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
            binding.rvChild.adapter = adapter

            adapter.stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
            adapter.submitList(item.list)
        }
    }

    fun onClick(listener: (position: Int, item: AlphabetModel, view: View) -> Unit) {
        _onClick = listener
    }
}