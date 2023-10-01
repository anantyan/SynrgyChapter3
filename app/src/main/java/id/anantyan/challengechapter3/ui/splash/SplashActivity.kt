package id.anantyan.challengechapter3.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import id.anantyan.challengechapter3.R
import id.anantyan.challengechapter3.ui.base.BaseActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(3000)
            val intent = Intent(this@SplashActivity, BaseActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}