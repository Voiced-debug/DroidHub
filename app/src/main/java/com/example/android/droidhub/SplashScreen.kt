package com.example.android.droidhub

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.android.droidhub.databinding.ActivitySplashScreenBinding

@Suppress("DEPRECATION")
class SplashScreen : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding

    //Delayed time for screen to display
    private val splashTimeOut:Long = 3000 // 3 sec

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }
}