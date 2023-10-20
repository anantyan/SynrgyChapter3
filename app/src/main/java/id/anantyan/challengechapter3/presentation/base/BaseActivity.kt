package id.anantyan.challengechapter3.presentation.base

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.ActivityBaseBinding
import id.anantyan.challengechapter3.data.words.WordsModel
import id.anantyan.challengechapter3.presentation.detail.DetailAdapter
import id.anantyan.challengechapter3.presentation.detail.DetailInteraction
import kotlinx.coroutines.launch

class BaseActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener,
    Toolbar.OnMenuItemClickListener,
    DetailInteraction {

    private var _onInteraction: BaseInteraction? = null
    private lateinit var navController: NavController
    private lateinit var navDestination: NavDestination
    private lateinit var binding: ActivityBaseBinding
    private val viewModel: BaseViewModel by viewModels()
    private val adapter: DetailAdapter by lazy { DetailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
        bindObserver()
    }

    private fun bindObserver() {
        lifecycleScope.launch {
            viewModel.getAll.collect { list ->
                adapter.submitList(list)
            }
        }
    }

    private fun bindView() {
        setUpNavigation()
        setUpSearchAdapter()
    }

    private fun setUpSearchAdapter() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.itemAnimator = DefaultItemAnimator()
        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = adapter

        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        adapter.onInteraction(this)
    }

    private fun setUpNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        navController = host.navController
        navController.addOnDestinationChangedListener(this)
        val appBar = AppBarConfiguration(
            setOf(
                R.id.baseOnBoardingFragment,
                R.id.homeFragment
            )
        )
        binding.toolbar.setOnMenuItemClickListener(this)
        binding.toolbar.setupWithNavController(navController, appBar)

        binding.searchBar.setOnClickListener { binding.searchBar.collapse(binding.searchView, binding.appBar) }
        binding.searchBar.isDefaultScrollFlagsEnabled = false

        binding.searchView.editText.addTextChangedListener(textWatcher())
    }

    private fun setUpMenuItem(bool: Boolean = false) {
        binding.toolbar.menu.findItem(R.id.grid_view).isVisible = bool
        binding.toolbar.menu.findItem(R.id.sort_view).isVisible = bool
    }

    private fun setSearchView(listener: () -> Unit) = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            if (binding.searchView.isShowing) {
                binding.searchView.hide()
            } else {
                listener.invoke()
            }
        }
    }

    fun setUpAppBar(bool: Boolean = false) {
        binding.searchBar.isVisible = bool
        binding.toolbar.isVisible = bool
    }

    fun onInteraction(listener: BaseInteraction) {
        _onInteraction = listener
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        navDestination = destination
        when (destination.id) {
            R.id.baseOnBoardingFragment -> {
                setUpMenuItem(false)
                setUpAppBar(false)
            }
            R.id.homeFragment -> {
                binding.toolbar.doMaterialMotion(binding.appBar)
                setUpMenuItem(true)
                setUpAppBar(true)
                onBackPressedDispatcher.addCallback(setSearchView { finish() })
            }
            R.id.detailFragment -> {
                binding.toolbar.doMaterialMotion(binding.appBar)
                setUpMenuItem(true)
                setUpAppBar(true)
                onBackPressedDispatcher.addCallback(setSearchView { navController.navigateUp() })
            }
            R.id.googleFragment -> {
                setUpMenuItem(false)
                setUpAppBar(false)
            }
            else -> {
                setUpMenuItem(false)
                setUpAppBar(false)
            }
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.grid_view -> {
                _onInteraction?.onClickGridView()
                false
            }
            R.id.sort_view -> {
                _onInteraction?.onClickSortView()
                false
            }
            else -> true
        }
    }

    private fun textWatcher() = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) { }

        override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.getAll(s.toString(), false)
        }

        override fun afterTextChanged(p0: Editable?) { }
    }

    override fun onClick(position: Int, item: WordsModel, view: View) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(getString(R.string.intent_to_weburl)+item.word)
        startActivity(intent)
    }
}