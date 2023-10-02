package id.anantyan.challengechapter3.ui.onboarding_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.FragmentOnBoarding2Binding

class OnBoarding2Fragment : Fragment() {

    private var _binding: FragmentOnBoarding2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoarding2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        doMaterialMotion(view, R.string.transform_onboardin1)
        binding.btnSingle.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.btnSingle to getString(R.string.transtorm_home))
            val destination = OnBoarding2FragmentDirections.actionOnBoarding2FragmentToHomeFragment()
            findNavController().navigate(destination, extras)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}