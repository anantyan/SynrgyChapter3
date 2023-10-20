package id.anantyan.challengechapter3.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.androidpoet.metaphor.hold
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.databinding.FragmentBaseOnBoardingBinding

class BaseOnBoardingFragment : Fragment() {

    private var _binding: FragmentBaseOnBoardingBinding? = null
    private val binding get() = _binding!!
    private val adapter: BaseOnBoardingViewPagerAdapter by lazy {
        BaseOnBoardingViewPagerAdapter(
            childFragmentManager,
            viewLifecycleOwner.lifecycle
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBaseOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hold()
        bindView()
    }

    private fun bindView() {
        binding.btnNext.transitionName = getString(R.string.transform_home)

        binding.vpOnBoarding.adapter = adapter
        binding.vpOnBoarding.registerOnPageChangeCallback(onPageChangeCallback)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val onPageChangeCallback = object : OnPageChangeCallback() {
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
                    val extras = FragmentNavigatorExtras(it to getString(R.string.transform_home))
                    val destination =
                        BaseOnBoardingFragmentDirections.actionBaseOnBoardingFragmentToHomeFragment()
                    findNavController().navigate(destination, extras)
                }
            }
        }
    }
}