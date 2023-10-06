package id.anantyan.challengechapter3.ui.base

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener,
    Toolbar.OnMenuItemClickListener {

    private var _onInteraction: BaseInteraction? = null
    private lateinit var navController: NavController
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        setUpNavigation()
    }

    private fun setUpNavigation() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        navController = host.navController
        navController.addOnDestinationChangedListener(this)
        val appBar = AppBarConfiguration(
            setOf(
                R.id.onBoarding1Fragment,
                R.id.onBoarding2Fragment
            )
        )
        binding.toolbar.setOnMenuItemClickListener(this)
        binding.collapseToolbar.setupWithNavController(binding.toolbar, navController, appBar)
    }

    private fun setUpMenuItem(bool: Boolean = false) {
        binding.toolbar.menu.findItem(R.id.grid_view).isVisible = bool
        binding.toolbar.menu.findItem(R.id.sort_view).isVisible = bool
    }

    fun setUpAppBar(bool: Boolean = false) {
        binding.collapseToolbar.isVisible = bool
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
        when (destination.id) {
            R.id.onBoarding1Fragment -> {
                setUpMenuItem(false)
                setUpAppBar(false)
            }
            R.id.onBoarding2Fragment -> {
                setUpMenuItem(false)
                setUpAppBar(false)
            }
            R.id.homeFragment -> {
                binding.collapseToolbar.doMaterialMotion(binding.appBar)
                setUpMenuItem(true)
                setUpAppBar(true)
            }
            R.id.detailFragment -> {
                binding.collapseToolbar.doMaterialMotion(binding.appBar)
                setUpMenuItem(true)
                setUpAppBar(true)
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
}