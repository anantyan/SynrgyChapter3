package id.anantyan.challengechapter3.ui.onboarding_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.androidpoet.metaphor.hold
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.databinding.FragmentOnBoarding1Binding
import id.anantyan.challengechapter3.ui.base.BaseActivity
import kotlinx.coroutines.launch

class OnBoarding1Fragment : Fragment(), OnClickListener {

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
        bindView()
    }

    private fun bindView() {
        binding.btnNext.transitionName = getString(R.string.transform_onboardin2)
        binding.btnNext.setOnClickListener(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View?) {
        val extras = FragmentNavigatorExtras(binding.btnNext to getString(R.string.transform_onboardin2))
        val destination = OnBoarding1FragmentDirections.actionOnBoarding1FragmentToOnBoarding2Fragment()
        findNavController().navigate(destination, extras)
    }
}