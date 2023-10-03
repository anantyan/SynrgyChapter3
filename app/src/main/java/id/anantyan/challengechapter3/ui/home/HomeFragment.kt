package id.anantyan.challengechapter3.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidpoet.metaphor.hold
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.FragmentHomeBinding
import id.anantyan.challengechapter3.ui.base.BaseActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val adapter: HomeAdapter by lazy { HomeAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.root.transitionName = getString(R.string.transform_home)
        doMaterialMotion(binding.root, getString(R.string.transform_home))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hold()
        bindView()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.getAll(false)

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
                viewModel.getAll(it)
            }
        }
    }

    private fun bindView() {
        binding.rvList.setHasFixedSize(true)
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.layoutManager = GridLayoutManager(requireContext(), 1)
        binding.rvList.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onClick { _, item, view ->
            val extras = FragmentNavigatorExtras(view to (item.key ?: ""))
            val destination = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item.key)
            findNavController().navigate(destination, extras)
        }

        (activity as BaseActivity).setTitle(getString(R.string.app_name))
        (activity as BaseActivity).onClickGridView { viewModel.toggleGrid() }
        (activity as BaseActivity).onClickSortView { viewModel.toggleSort() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}