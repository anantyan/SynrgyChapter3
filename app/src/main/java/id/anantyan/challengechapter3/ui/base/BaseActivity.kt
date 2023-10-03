package id.anantyan.challengechapter3.ui.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.databinding.ActivityBaseBinding
import kotlinx.coroutines.launch

class BaseActivity : AppCompatActivity(),
    NavController.OnDestinationChangedListener,
    Toolbar.OnMenuItemClickListener {

    private var _onClickGridView: (() -> Unit)? = null
    private var _onClickSortView: (() -> Unit)? = null
    private lateinit var navController: NavController
    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        val host = supportFragmentManager.findFragmentById(R.id.nav_host_main) as NavHostFragment
        navController = host.navController
        navController.addOnDestinationChangedListener(this)
        val appBar = AppBarConfiguration(
            setOf(
                R.id.onBoarding1Fragment,
                R.id.onBoarding2Fragment
            )
        )
        NavigationUI.setupWithNavController(binding.toolbar, navController, appBar)
        binding.toolbar.setOnMenuItemClickListener(this)
    }

    fun onClickGridView(listener: () -> Unit) {
        _onClickGridView = listener
    }

    fun onClickSortView(listener: () -> Unit) {
        _onClickSortView = listener
    }

    fun setTitle(newTitle: String) {
        binding.toolbar.title = newTitle
        binding.collapseToolbar.title = newTitle
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
                binding.collapseToolbar.isVisible = false
            }
            R.id.onBoarding2Fragment -> {
                binding.collapseToolbar.isVisible = false
            }
            R.id.homeFragment -> {
                binding.collapseToolbar.isVisible = true
                binding.toolbar.menu.findItem(R.id.grid_view).isVisible = true
                binding.toolbar.menu.findItem(R.id.sort_view).isVisible = true
            }
            R.id.detailFragment -> {
                binding.collapseToolbar.isVisible = true
                binding.toolbar.menu.findItem(R.id.grid_view).isVisible = true
                binding.toolbar.menu.findItem(R.id.sort_view).isVisible = true
            }
            R.id.googleFragment -> {
                binding.collapseToolbar.isVisible = false
            }
            else -> {}
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.grid_view -> {
                _onClickGridView?.invoke()
                true
            }
            R.id.sort_view -> {
                _onClickSortView?.invoke()
                true
            }
            else -> false
        }
    }
}