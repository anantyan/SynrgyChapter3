package id.anantyan.challengechapter3.ui.google

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.common.doMaterialMotion
import id.anantyan.challengechapter3.databinding.ActivityGoogleBinding
import id.anantyan.challengechapter3.ui.detail.DetailActivity

class GoogleActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityGoogleBinding
    private val word: String by lazy { intent.getStringExtra(EXTRA_GOOGLE_ACTIVITY) ?: "" }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoogleBinding.inflate(layoutInflater)
        binding.cardView.transitionName = word
        doMaterialMotion(binding.cardView, word)
        setContentView(binding.root)
        bindView()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.webView.stopLoading()
        binding.webView.destroy()
    }

    private fun bindView() {
        binding.cardView.setOnClickListener(this)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.settings.useWideViewPort = true
        binding.webView.loadUrl(getString(R.string.intent_to_weburl)+word)
        binding.webView.webChromeClient = chromeProgressBar
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

    companion object {
        private const val EXTRA_GOOGLE_ACTIVITY = "EXTRA_GOOGLE_ACTIVIVTY"

        @JvmStatic
        fun getIntent(
            context: Context?,
            word: String?
        ) = Intent(context, GoogleActivity::class.java).apply {
            word?.let {
                putExtra(EXTRA_GOOGLE_ACTIVITY, it)
            }
        }
    }

    override fun onClick(p0: View?) {
        onBackPressedDispatcher.onBackPressed()
    }
}