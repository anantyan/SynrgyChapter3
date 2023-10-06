package id.anantyan.challengechapter3.ui.google

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import id.anantyan.challengechapter3.databinding.FragmentGoogleBinding


class GoogleFragment : Fragment() {

    private var _binding: FragmentGoogleBinding? = null
    private val binding get() = _binding!!
    private val args: GoogleFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGoogleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindView()
    }

    private fun bindView() {
        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.loadUrl("https://www.google.com/search?q=Apa+itu+"+args.word)
        binding.webView.webChromeClient = chromeProgressBar
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.webView.stopLoading()
        binding.webView.destroy()
        _binding = null
    }

    private val chromeProgressBar = object : WebChromeClient() {
        override fun onProgressChanged(view: WebView?, newProgress: Int) {
            super.onProgressChanged(view, newProgress)
            if (newProgress == 100) {
                binding.progressBar.isVisible = false
            }
            if (newProgress < 100) {
                binding.progressBar.isVisible = true
                binding.progressBar.progress = newProgress
            }
        }
    }
}