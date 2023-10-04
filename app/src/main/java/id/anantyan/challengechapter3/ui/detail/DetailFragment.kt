package id.anantyan.challengechapter3.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.FragmentDetailBinding
import id.anantyan.challengechapter3.model.WordsModel
import id.anantyan.challengechapter3.ui.base.BaseActivity
import id.anantyan.challengechapter3.ui.base.BaseInteraction
import kotlinx.coroutines.launch

class DetailFragment : Fragment(), DetailInteraction, BaseInteraction {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel: DetailViewModel by viewModels()
    private val args: DetailFragmentArgs by navArgs()
    private val adapter: DetailAdapter by lazy { DetailAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        binding.root.transitionName = args.key
        doMaterialMotion(binding.root, args.key ?: "")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.getAll(args.key, false)

        lifecycleScope.launch {
            viewModel.getAll.collect { list ->
                adapter.submitList(list)
            }
        }

        lifecycleScope.launch {
            viewModel.toggleGrid.collect {
                val spanCount = if (it) 1 else 2
                binding.rvList.layoutManager = GridLayoutManager(requireContext(), spanCount)
            }
        }

        lifecycleScope.launch {
            viewModel.toggleSort.collect {
                viewModel.getAll(args.key, it)
            }
        }
    }

    private fun bindView() {
        binding.rvList.setHasFixedSize(true)
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.layoutManager = GridLayoutManager(requireContext(), 2)
        binding.rvList.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onInteraction(this)
        (requireActivity() as BaseActivity).onInteraction(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickGridView() {
        viewModel.toggleGrid()
    }

    override fun onClickSortView() {
        viewModel.toggleSort()
    }

    override fun onClick(position: Int, item: WordsModel) {
        val destination = DetailFragmentDirections.actionDetailFragmentToGoogleFragment(item.word)
        findNavController().navigate(destination)
    }
}