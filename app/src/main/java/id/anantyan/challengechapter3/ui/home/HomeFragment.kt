package id.anantyan.challengechapter3.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
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
import id.anantyan.challengechapter3.common.Resource
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.FragmentHomeBinding
import id.anantyan.challengechapter3.model.AlphabetModel
import id.anantyan.challengechapter3.ui.base.BaseActivity
import id.anantyan.challengechapter3.ui.base.BaseInteraction
import id.anantyan.challengechapter3.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class HomeFragment : Fragment(),
    HomeInteraction,
    BaseInteraction {

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
            viewModel.getAll.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is Resource.Success -> {
                        binding.progressBar.isVisible = false
                        adapter.submitList(resource.data)
                    }
                }
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
        adapter.onInteraction(this)
        (requireActivity() as BaseActivity).setUpAppBar(true)
        (requireActivity() as BaseActivity).onInteraction(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(position: Int, item: AlphabetModel, view: View) {
        val extras = FragmentNavigatorExtras(view to (item.key ?: ""))
        val destination = HomeFragmentDirections.actionHomeFragmentToDetailFragment(item.key)
        findNavController().navigate(destination, extras)
    }

    override fun onLongClick(position: Int, item: AlphabetModel, view: View) {
        val intent = DetailActivity.getIntent(requireContext(), item.key)
        startActivity(intent)
    }

    override fun onClickGridView() {
        viewModel.toggleGrid()
    }

    override fun onClickSortView() {
        viewModel.toggleSort()
    }
}