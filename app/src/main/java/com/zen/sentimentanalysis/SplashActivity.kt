package com.zen.sentimentanalysis

import android.content.Intent
import android.os.Bundle
import android.os.Handler

class SplashActivity : GenericActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(Intent(this, CustomActivity::class.java))
            finish()
        }, 3000)
    }
}