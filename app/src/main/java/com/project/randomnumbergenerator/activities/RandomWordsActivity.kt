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
import com.project.randomnumbergenerator.databinding.ActivityRandomWordsBinding
import com.project.randomnumbergenerator.interfaces.ToastManager
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class RandomWordsActivity : AppCompatActivity(), ToastManager{

    override val activityContext: Context = this
    private lateinit var binding: ActivityRandomWordsBinding
    private lateinit var wordsAdapter: WordsListAdapter
    private lateinit var wordsList: ArrayList<String>

    private val generateButtonClickListener = View.OnClickListener { generateButtonClicked() }
    private val clearButtonClickListener = View.OnClickListener { clearButtonClicked() }
    private val addButtonClickListener = View.OnClickListener { addButtonClicked() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        modifyActionBar()
        wordsList = ArrayList()

        binding.addButton.setOnClickListener(addButtonClickListener)
        binding.clearButton.setOnClickListener(clearButtonClickListener)
        binding.generateWordButton.setOnClickListener(generateButtonClickListener)
        binding.microphoneImageView.setOnClickListener{
            getSpeechInput()
        }
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
                        binding.wordTextEdit.setText(text[0])
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
            binding.resultTextView.text = wordsList[position]
        }
    }

    /**
     * Clear on clicked
     */
    private fun clearButtonClicked(){
        wordsList.clear()
        wordsAdapter.notifyDataSetChanged()
        binding.resultTextView.text=""
    }

    /**
     * Adds new word on clicked
     */
    @SuppressLint("SetTextI18n")
    private fun addButtonClicked(){
        if(binding.wordTextEdit.text.isEmpty()) {
            shortToast("Enter a word")
        }
        else {
            wordsList.add(binding.wordTextEdit.text.toString() + "\n")
            wordsAdapter = WordsListAdapter(applicationContext, wordsList)
            binding.wordsListView.adapter = wordsAdapter
            binding.wordTextEdit.setText("")
        }
    }
}