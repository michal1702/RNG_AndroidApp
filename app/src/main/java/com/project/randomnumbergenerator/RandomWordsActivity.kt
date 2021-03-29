package com.project.randomnumbergenerator

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.random.Random


class RandomWordsActivity : AppCompatActivity(){

    private lateinit var wordsArray: MutableList<String>
    private lateinit var addButton: Button
    private lateinit var wordBox: EditText
    private lateinit var wordsTextView: TextView
    private lateinit var warningBox: TextView
    private lateinit var clearButton: Button
    private lateinit var generateButton: Button
    private lateinit var resultTextBox: TextView
    private lateinit var listDescription: TextView
    private lateinit var micorphone: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_words)

        setVariables()
        modifyActionBar()
        //region onClickListeners
        addButton.setOnClickListener{
            addButtonClick()
        }

        clearButton.setOnClickListener {
            wordsTextView.text=""
            resultTextBox.text=""
            listDescription.text="Words list"+"\u2026"
            wordsArray.clear()
        }

        generateButton.setOnClickListener{
            if(wordsArray.isNotEmpty()) {
                resultTextBox.text = wordsArray[Random.nextInt(0, wordsArray.size)]
            }
        }

        micorphone.setOnClickListener{
            getSpeechInput()
        }
        //endregion
        wordBox.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { if(!s.isNullOrBlank()) warningBox.text = "" }
        })
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun setVariables(){
        wordsArray = mutableListOf()
        addButton = findViewById(R.id.addButton)
        wordBox = findViewById(R.id.wordTextEdit)
        wordsTextView = findViewById(R.id.wordsTextView)
        wordsTextView.movementMethod = ScrollingMovementMethod()
        warningBox = findViewById(R.id.wordWarningTextView)
        clearButton = findViewById(R.id.clearButton)
        generateButton = findViewById(R.id.generateWordButton)
        resultTextBox = findViewById(R.id.resultTextView)
        listDescription = findViewById(R.id.listViewDescriptionTextView)
        micorphone = findViewById(R.id.microphoneImageView)
    }

    private fun modifyActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title = "Random words"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    @SuppressLint("SetTextI18n")
    private fun addButtonClick(){
        if(wordBox.text.isEmpty()) {
            warningBox.text = "Enter a word"
        }
        else{
            wordsArray.add(wordBox.text.toString())
            listDescription.text=""
        }
        wordsTextView.text=""
        wordBox.setText("")
        for (word in wordsArray) {
            wordsTextView.append(" \u2022 ")
            wordsTextView.append(word + "\n")
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun getSpeechInput() {
        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        if(speechIntent.resolveActivity(packageManager) != null)
            startActivityForResult(speechIntent, 10)
        else
            Toast.makeText(this, "Your device does not support speech recognition!", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            10->{
                if(resultCode == Activity.RESULT_OK && data != null){
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if(text != null)
                        wordBox.setText(text[0])
                }
            }
        }
    }
}