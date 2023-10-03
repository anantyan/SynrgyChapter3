package id.anantyan.challengechapter3.ui.onboarding_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.androidpoet.metaphor.hold
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.databinding.FragmentOnBoarding1Binding

class OnBoarding1Fragment : Fragment() {

    private var _binding: FragmentOnBoarding1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoarding1Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnNext.transitionName = getString(R.string.transform_onboardin2)
        binding.btnNext.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.btnNext to getString(R.string.transform_onboardin2))
            val destination = OnBoarding1FragmentDirections.actionOnBoarding1FragmentToOnBoarding2Fragment()
            findNavController().navigate(destination, extras)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}