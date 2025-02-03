package com.example.rememberyournihongo

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val hiraganaButton: Button = findViewById(R.id.hiraganaButton)
        val katakanaButton: Button = findViewById(R.id.katakanaButton)
        val bothButton: Button = findViewById(R.id.bothButton)
        val katakanaCombinationsButton: Button = findViewById(R.id.katakanaCombinationsButton)
        val hiraganaCombinationsButton: Button = findViewById(R.id.hiraganaCombinationsButton)

        hiraganaButton.setOnClickListener {
            val intent = Intent(this, HiraganaActivity::class.java)
            startActivity(intent)
        }

        katakanaButton.setOnClickListener {
            val intent = Intent(this, KatakanaActivity::class.java)
            startActivity(intent)
        }

        bothButton.setOnClickListener {
            val intent = Intent(this, RandomKanaActivity::class.java)
            startActivity(intent)
        }

        hiraganaCombinationsButton.setOnClickListener {
            val intent = Intent(this, HiraganaCombinationsActivity::class.java)
            startActivity(intent)
        }

        katakanaCombinationsButton.setOnClickListener {
            val intent = Intent(this, KatakanaCombinationsActivity::class.java)
            startActivity(intent)
        }
    }
}
