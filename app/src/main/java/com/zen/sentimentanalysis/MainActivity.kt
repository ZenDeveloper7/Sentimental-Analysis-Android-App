package com.zen.sentimentanalysis

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = intent.getCharSequenceExtra(Intent.EXTRA_PROCESS_TEXT)

        Toast.makeText(this, text.toString(), Toast.LENGTH_LONG).show()
    }
}