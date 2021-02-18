package com.zen.sentimentanalysis

import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

open class GenericActivity : AppCompatActivity() {

    fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}