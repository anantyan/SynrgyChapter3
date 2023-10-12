package id.anantyan.challengechapter3.ui.onboarding

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.androidpoet.metaphor.hold
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.databinding.ActivityBaseOnBoardingBinding
import id.anantyan.challengechapter3.ui.detail.DetailActivity
import id.anantyan.challengechapter3.ui.home.HomeActivity

class BaseOnBoardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseOnBoardingBinding
    private val adapter: BaseOnBoardingViewPagerAdapter by lazy {
        BaseOnBoardingViewPagerAdapter(
            supportFragmentManager,
            lifecycle
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseOnBoardingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindView()
    }

    private fun bindView() {
        binding.vpOnBoarding.adapter = adapter
        binding.vpOnBoarding.registerOnPageChangeCallback(onPageChangeCallback)
    }

    private val onPageChangeCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            super.onPageSelected(position)
            if (position == 0 || position > 0 && position < adapter.itemCount - 1) {
                binding.btnNext.setText(R.string.btn_onboarding)
                binding.btnNext.setOnClickListener {
                    binding.vpOnBoarding.currentItem++
                }
            }
            if (position == adapter.itemCount - 1) {
                binding.btnNext.setText(R.string.btn_last_onboarding)
                binding.btnNext.setOnClickListener {
                    val intent = HomeActivity.getIntent(this@BaseOnBoardingActivity)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}