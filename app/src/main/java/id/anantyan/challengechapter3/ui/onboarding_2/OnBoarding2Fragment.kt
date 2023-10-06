package id.anantyan.challengechapter3.ui.onboarding_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.androidpoet.metaphor.hold
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.FragmentOnBoarding2Binding
import id.anantyan.challengechapter3.ui.base.BaseActivity

class OnBoarding2Fragment : Fragment(), OnClickListener {

    private var _binding: FragmentOnBoarding2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)
        binding.root.transitionName = getString(R.string.transform_onboardin2)
        doMaterialMotion(binding.root, getString(R.string.transform_onboardin2))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hold()
        bindView()
    }

    private fun bindView() {
        binding.btnNext.transitionName = getString(R.string.transform_home)
        binding.btnNext.setOnClickListener(this)
        (requireActivity() as BaseActivity).setUpAppBar(false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        val extras = FragmentNavigatorExtras(binding.btnNext to getString(R.string.transform_home))
        val destination = OnBoarding2FragmentDirections.actionOnBoarding2FragmentToHomeFragment()
        findNavController().navigate(destination, extras)
    }
}