package id.anantyan.challengechapter3.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.Resource
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.ActivityHomeBinding
import id.anantyan.challengechapter3.model.AlphabetModel
import id.anantyan.challengechapter3.ui.detail.DetailActivity
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(), HomeInteraction, Toolbar.OnMenuItemClickListener {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
    private val adapter: HomeAdapter by lazy { HomeAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
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
                binding.rvList.layoutManager = GridLayoutManager(this@HomeActivity, spanCount)
            }
        }

        lifecycleScope.launch {
            viewModel.toggleSort.collect {
                viewModel.getAll(it)
            }
        }
    }

    private fun bindView() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = getString(R.string.app_name)
        binding.collapseToolbar.title = getString(R.string.app_name)
        binding.toolbar.setOnMenuItemClickListener(this)

        binding.rvList.setHasFixedSize(true)
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.layoutManager = GridLayoutManager(this, 1)
        binding.rvList.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onInteraction(this)
    }

    override fun onClick(position: Int, item: AlphabetModel, view: View) {
        val extras = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            view,
            view.transitionName
        )
        val intent = DetailActivity.getIntent(this, item.key)
        startActivity(intent, extras.toBundle())
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.grid_view -> {
                viewModel.toggleGrid()
                false
            }
            R.id.sort_view -> {
                viewModel.toggleSort()
                false
            }
            else -> true
        }
    }

    companion object {
        private const val EXTRA_HOME_ACTIVITY = "EXTRA_HOME_ACTIVITY"

        @JvmStatic
        fun getIntent(
            context: Context?,
        ) = Intent(context, HomeActivity::class.java)
    }
}