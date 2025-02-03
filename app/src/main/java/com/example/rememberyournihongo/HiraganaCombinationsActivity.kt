package com.example.rememberyournihongo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HiraganaCombinationsActivity : AppCompatActivity() {
    private var backButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hiragana_combinations)

        backButton = findViewById(R.id.backButton)

        backButton?.setOnClickListener {
            // Navigate back to HomeActivity
            finish()
        }
    }
}