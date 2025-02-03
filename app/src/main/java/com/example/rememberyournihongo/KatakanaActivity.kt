package com.example.rememberyournihongo

import android.os.Bundle
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import java.util.Random

class KatakanaActivity : AppCompatActivity() {
    private var kanaTextView: TextView? = null
    private var answerEditText: EditText? = null
    private var submitButton: Button? = null
    private var score: Int = 0
    private var scoreTextView: TextView? = null
    private var backButton: Button? = null

    private val katakanaList = arrayOf(
        "ア", "イ", "ウ", "エ", "オ", "カ", "キ", "ク", "ケ", "コ", "サ", "シ", "ス", "セ", "ソ", "タ",
        "チ", "ツ", "テ", "ト", "ナ", "ニ", "ヌ", "ネ", "ノ", "ハ", "ヒ", "フ", "ヘ", "ホ", "マ", "ミ",
        "ム", "メ", "モ", "ヤ", "ユ", "ヨ", "ラ", "リ", "ル", "レ", "ロ", "ワ", "ヲ", "ン", "ガ", "ギ",
        "グ", "ゲ", "ゴ", "ザ", "ジ", "ズ", "ゼ", "ゾ", "ダ", "ヂ", "ヅ", "デ", "ド", "バ", "ビ", "ブ",
        "ベ", "ボ", "パ", "ピ", "プ", "ペ", "ポ"
    )

    private val romajiList = arrayOf(
        "a", "i", "u", "e", "o", "ka", "ki", "ku", "ke", "ko", "sa", "shi", "su", "se", "so", "ta",
        "chi", "tsu", "te", "to", "na", "ni", "nu", "ne", "no", "ha", "hi", "fu", "he", "ho", "ma", "mi",
        "mu", "me", "mo", "ya", "yu", "yo", "ra", "ri", "ru", "re", "ro", "wa", "wo", "n", "ga", "gi",
        "gu", "ge", "go", "za", "ji", "zu", "ze", "zo", "da", "ji", "zu", "de", "do", "ba", "bi", "bu",
        "be", "bo", "pa", "pi", "pu", "pe", "po"
    )

    private var currentIndex: Int = -1
    private var incorrectCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_katakana)

        kanaTextView = findViewById(R.id.kanaTextView)
        answerEditText = findViewById(R.id.answerEditText)
        submitButton = findViewById(R.id.submitButton)
        scoreTextView = findViewById(R.id.scoreTextView)
        backButton = findViewById(R.id.backButton)

        submitButton?.setOnClickListener { checkAnswer() }
        answerEditText?.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                checkAnswer()
                true
            } else {
                false
            }
        }

        backButton?.setOnClickListener {
            finish()
        }

        currentIndex = generateRandomIndex()
        displayKanaAtIndex(currentIndex)
    }

    private fun generateRandomIndex(): Int {
        return Random().nextInt(katakanaList.size)
    }

    private fun displayKanaAtIndex(index: Int) {
        kanaTextView?.text = katakanaList[index]
    }

    private fun checkAnswer() {
        val userAnswer = answerEditText?.text.toString()
        val displayedKana = kanaTextView?.text.toString()

        if (currentIndex != -1 && currentIndex < romajiList.size) {
            val correctAnswer = romajiList[currentIndex]
            if (userAnswer == correctAnswer) {
                showToast("Correct!")
                currentIndex = generateRandomIndex()
                displayKanaAtIndex(currentIndex)
                score++
                incorrectCount = 0 // Reset incorrect count on correct answer
            } else {
                incorrectCount++
                score-- // Subtract score on incorrect answer
                if (incorrectCount >= 3) {
                    showErrorDialog("Incorrect, the answer is: $correctAnswer")
                    currentIndex = generateRandomIndex()
                    displayKanaAtIndex(currentIndex)
                    incorrectCount = 0 // Reset incorrect count after showing the dialog
                } else {
                    showErrorDialog("Incorrect, Try again")
                }
            }
            answerEditText?.setText("")
            updateScoreTextView()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorDialog(message: String) {
        val dialogView = layoutInflater.inflate(R.layout.custom_error_dialog, null)
        val dialogMessage = dialogView.findViewById<TextView>(R.id.dialog_message)
        val dialogOkButton = dialogView.findViewById<Button>(R.id.dialog_ok_button)

        dialogMessage.text = message

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true) // Allows dismissing by tapping outside
            .create()

        dialogOkButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun updateScoreTextView() {
        scoreTextView?.text = "Score: $score"
    }
}
