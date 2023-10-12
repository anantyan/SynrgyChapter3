package id.anantyan.challengechapter3.ui.detail

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ActivityNavigatorExtras
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.ActivityDetailBinding
import id.anantyan.challengechapter3.model.WordsModel
import id.anantyan.challengechapter3.ui.google.GoogleActivity
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(),
    DetailInteraction,
    Toolbar.OnMenuItemClickListener {

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels()
    private val adapter: DetailAdapter by lazy { DetailAdapter() }
    private val key: String by lazy { intent.getStringExtra(EXTRA_DETAIL_ACTIVITY) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        binding.root.transitionName = key
        doMaterialMotion(binding.root, key)
        setContentView(binding.root)
        bindView()
        bindObserver()
    }

    private fun bindObserver() {
        viewModel.getAll(key, false)

        lifecycleScope.launch {
            viewModel.getAll.collect { list ->
                adapter.submitList(list)
            }
        }

        lifecycleScope.launch {
            viewModel.toggleGrid.collect {
                val spanCount = if (it) 1 else 2
                binding.rvList.layoutManager = GridLayoutManager(this@DetailActivity, spanCount)
            }
        }

        lifecycleScope.launch {
            viewModel.toggleSort.collect {
                viewModel.getAll(key, it)
            }
        }
    }

    private fun bindView() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.toolbar.title = getString(R.string.app_name_detail)+" $key"
        binding.collapseToolbar.title = getString(R.string.app_name_detail)+" $key"
        binding.toolbar.setOnMenuItemClickListener(this)

        binding.rvList.setHasFixedSize(true)
        binding.rvList.itemAnimator = DefaultItemAnimator()
        binding.rvList.layoutManager = GridLayoutManager(this, 2)
        binding.rvList.adapter = adapter

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onInteraction(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onClick(position: Int, item: WordsModel, view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(getString(R.string.intent_to_weburl)+item.word)
        startActivity(intent)
    }

    override fun onLongClick(position: Int, item: WordsModel, view: View) {
        val extras = ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            view,
            view.transitionName
        )
        val intent = GoogleActivity.getIntent(this, item.word)
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
        private const val EXTRA_DETAIL_ACTIVITY = "EXTRA_DETAIL_ACTIVIVTY"

        @JvmStatic
        fun getIntent(
            context: Context?,
            key: String?
        ) = Intent(context, DetailActivity::class.java).apply {
            key?.let {
                putExtra(EXTRA_DETAIL_ACTIVITY, it)
            }
        }
    }
}