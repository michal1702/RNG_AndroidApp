package com.project.randomnumbergenerator.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.adapters.WordsListAdapter
import com.project.randomnumbergenerator.interfaces.ToastManager
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class RandomWordsActivity : AppCompatActivity(), ToastManager{

    override val activityContext: Context = this
    private lateinit var addButton: Button
    private lateinit var wordBox: EditText
    private lateinit var warningBox: TextView
    private lateinit var clearButton: Button
    private lateinit var generateButton: Button
    private lateinit var resultTextBox: TextView
    private lateinit var micorphone: ImageView
    private lateinit var wordsAdapter: WordsListAdapter
    private lateinit var wordsListView: ListView
    private lateinit var wordsList: ArrayList<String>

    private val generateButtonClickListener = View.OnClickListener { generateButtonClicked() }
    private val clearButtonClickListener = View.OnClickListener { clearButtonClicked() }
    private val addButtonClickListener = View.OnClickListener { addButtonClicked() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_words)
        setControls()
        modifyActionBar()
        wordsList = ArrayList()

        addButton.setOnClickListener(addButtonClickListener)
        clearButton.setOnClickListener(clearButtonClickListener)
        generateButton.setOnClickListener(generateButtonClickListener)
        micorphone.setOnClickListener{
            getSpeechInput()
        }

        wordBox.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { if(!s.isNullOrBlank()) warningBox.text = "" }
        })
    }

    /**
     * Method hides keyboard on focus off
     * @param view view of this activity
     */
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    /**
     * Sets up controls like buttons, text fields, etc.
     */
    private fun setControls(){
        addButton = findViewById(R.id.addButton)
        wordBox = findViewById(R.id.wordTextEdit)
        warningBox = findViewById(R.id.wordWarningTextView)
        clearButton = findViewById(R.id.clearButton)
        generateButton = findViewById(R.id.generateWordButton)
        resultTextBox = findViewById(R.id.resultTextView)
        micorphone = findViewById(R.id.microphoneImageView)
        wordsListView = findViewById(R.id.wordsListView)
    }

    /**
     * Sets up action bar
     */
    private fun modifyActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title = "Random words"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Method gets speech input and converts it to text
     */
    @SuppressLint("QueryPermissionsNeeded")
    private fun getSpeechInput() {
        val speechIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        speechIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        if(speechIntent.resolveActivity(packageManager) != null)
            startActivityForResult(speechIntent, 10)
        else
            longToast("Your device does not support speech recognition!")
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

    /**
     * Draw on clicked
     */
    private fun generateButtonClicked(){
        if(wordsList.isNotEmpty()) {
            val position = Random.nextInt(0, wordsList.size)
            resultTextBox.text = wordsList[position]
        }
    }

    /**
     * Clear on clicked
     */
    private fun clearButtonClicked(){
        wordsList.clear()
        wordsAdapter.notifyDataSetChanged()
        resultTextBox.text=""
    }

    /**
     * Adds new word on clicked
     */
    @SuppressLint("SetTextI18n")
    private fun addButtonClicked(){
        if(wordBox.text.isEmpty()) {
            warningBox.text = "Enter a word"
        }
        else {
            wordsList.add(wordBox.text.toString() + "\n")
            wordsAdapter = WordsListAdapter(applicationContext, wordsList)
            wordsListView.adapter = wordsAdapter
            wordBox.setText("")
        }
    }
}