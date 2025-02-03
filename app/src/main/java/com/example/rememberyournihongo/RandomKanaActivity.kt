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

class RandomKanaActivity : AppCompatActivity() {
    private var kanaTextView: TextView? = null
    private var answerEditText: EditText? = null
    private var submitButton: Button? = null
    private var score: Int = 0
    private var scoreTextView: TextView? = null
    private var backButton: Button? = null

    private val hiraganaList = arrayOf(
        "あ", "い", "う", "え", "お", "か", "き", "く", "け", "こ", "さ", "し", "す", "せ", "そ", "た",
        "ち", "つ", "て", "と", "な", "に", "ぬ", "ね", "の", "は", "ひ", "ふ", "へ", "ほ", "ま", "み",
        "む", "め", "も", "や", "ゆ", "よ", "ら", "り", "る", "れ", "ろ", "わ", "を", "ん", "が", "ぎ",
        "ぐ", "げ", "ご", "ざ", "じ", "ず", "ぜ", "ぞ", "だ", "ぢ", "づ", "で", "ど", "ば", "び", "ぶ",
        "べ", "ぼ", "ぱ", "ぴ", "ぷ", "ぺ", "ぽ"
    )

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

    private var currentKanaList: Array<String>? = null
    private var currentIndex: Int = -1
    private var incorrectCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_kana)

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
            // Navigate back to HomeActivity
            finish()
        }

        // Randomly select between Hiragana and Katakana lists
        currentKanaList = if (Random().nextBoolean()) hiraganaList else katakanaList
        currentIndex = generateRandomIndex()
        displayKanaAtIndex(currentIndex)
    }

    private fun generateRandomIndex(): Int {
        return Random().nextInt(currentKanaList?.size ?: 0)
    }

    private fun displayKanaAtIndex(index: Int) {
        kanaTextView?.text = currentKanaList?.get(index)
    }

    private fun checkAnswer() {
        val userAnswer = answerEditText?.text.toString()
        val displayedKana = kanaTextView?.text.toString()

        val currentRomajiList = if (currentKanaList == hiraganaList) hiraganaList else katakanaList

        if (currentIndex != -1 && currentIndex < romajiList.size) {
            val correctAnswer = romajiList[currentIndex]

            if (userAnswer == correctAnswer) {
                // Answer is correct
                showToast("Correct!")
                // Change kana character only if the answer is correct
                currentKanaList = if (Random().nextBoolean()) hiraganaList else katakanaList
                currentIndex = generateRandomIndex()
                score++
                incorrectCount = 0 // Reset incorrect count on correct answer
            } else {
                // Answer is incorrect
                incorrectCount++
                score-- // Subtract score on incorrect answer
                if (incorrectCount >= 3) {
                    showErrorDialog("Incorrect, the answer is: $correctAnswer")
                    currentKanaList = if (Random().nextBoolean()) hiraganaList else katakanaList
                    currentIndex = generateRandomIndex()
                    incorrectCount = 0 // Reset incorrect count after showing the dialog
                } else {
                    showErrorDialog("Incorrect, Try again")
                }
            }
            answerEditText?.setText("")
            displayKanaAtIndex(currentIndex)
            updateScoreTextView()
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showErrorDialog(message: String) {
        // Inflate the custom layout for the dialog
        val dialogView = layoutInflater.inflate(R.layout.custom_error_dialog, null)

        // Find the TextView and Button in the custom layout
        val messageTextView = dialogView.findViewById<TextView>(R.id.dialog_message)
        val okButton = dialogView.findViewById<Button>(R.id.dialog_ok_button)

        // Set the message in the TextView
        messageTextView.text = message

        // Create the dialog
        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(true) // Allow the dialog to be dismissed by tapping outside
            .create()

        // Set the click listener for the OK button to dismiss the dialog
        okButton.setOnClickListener {
            dialog.dismiss()
        }

        // Show the dialog
        dialog.show()
    }

    private fun updateScoreTextView() {
        scoreTextView?.text = "Score: $score"
    }
}
