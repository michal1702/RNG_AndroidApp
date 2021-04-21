package com.project.randomnumbergenerator.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.adapters.WordsListAdapter
import com.project.randomnumbergenerator.databinding.ActivityRandomWordsBinding
import com.project.randomnumbergenerator.interfaces.KeyboardHide
import com.project.randomnumbergenerator.interfaces.ToastManager
import java.util.*
import kotlin.collections.ArrayList
import kotlin.random.Random


class RandomWordsActivity : AppCompatActivity(), ToastManager, KeyboardHide{

    override val activity: Activity = this
    override val activityContext: Context = this
    private lateinit var binding: ActivityRandomWordsBinding
    private lateinit var wordsAdapter: WordsListAdapter
    private lateinit var wordsList: ArrayList<String>

    private val generateButtonClickListener = View.OnClickListener { generateButtonClicked() }
    private val clearButtonClickListener = View.OnClickListener { clearButtonClicked() }
    private val addButtonClickListener = View.OnClickListener { addButtonClicked() }

    private lateinit var resultTextViewAnimation: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomWordsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resultTextViewAnimation = AnimationUtils.loadAnimation(this, R.anim.result_animation)
        modifyActionBar()
        wordsList = ArrayList()

        binding.randomWordsConstraintLayout.setOnClickListener {
            hideKeyboard()
        }
        binding.addButton.setOnClickListener(addButtonClickListener)
        binding.clearButton.setOnClickListener(clearButtonClickListener)
        binding.generateWordButton.setOnClickListener(generateButtonClickListener)
        binding.microphoneImageView.setOnClickListener{
            getSpeechInput()
        }
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
            binding.resultTextView.startAnimation(resultTextViewAnimation)
            val position = Random.nextInt(0, wordsList.size)
            binding.resultTextView.text = wordsList[position]
        }else shortToast("Enter some words")
    }

    /**
     * Clear on clicked
     */
    private fun clearButtonClicked(){
        if(wordsList.isNotEmpty()) {
            wordsList.clear()
            wordsAdapter.notifyDataSetChanged()
            binding.resultTextView.text = ""
        }
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