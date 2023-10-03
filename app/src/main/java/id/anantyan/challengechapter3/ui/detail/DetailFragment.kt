package id.anantyan.challengechapter3.ui.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.FragmentDetailBinding
import id.anantyan.challengechapter3.ui.base.BaseActivity
import kotlinx.coroutines.launch

class DetailFragment : Fragment() {

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
                val spanCount = if (it) 2 else 1
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
        binding.rvList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rvList.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onClick { _, item ->
            Toast.makeText(requireContext(), item.word, Toast.LENGTH_LONG).show()
        }

        (activity as BaseActivity).setTitle("Alphabet " + args.key)
        (activity as BaseActivity).onClickGridView { viewModel.toggleGrid() }
        (activity as BaseActivity).onClickSortView { viewModel.toggleSort() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}