package com.project.randomnumbergenerator

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import kotlin.random.Random
import kotlin.random.nextInt


class RandomNumbersActivity : AppCompatActivity(){

    private lateinit var lowerLimitBox: EditText
    private lateinit var upperLimitBox: EditText
    private lateinit var drawButton: Button
    private lateinit var resultBox: TextView
    private lateinit var warningBox: TextView
    private lateinit var decimalSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var repeatSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var ascendingSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var descendingSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var spinner: Spinner

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_numbers)
        setControls()
        modifyActionBar()
        initSpinner()

        drawButton.setOnClickListener{
            resultBox.text=""
            val numbersCount = spinner.selectedItem.toString().toInt()
            if(!lowerLimitBox.text.isNullOrEmpty() && !upperLimitBox.text.isNullOrEmpty()) {
                val rng = RandomNumbersGenerator(numbersCount, lowerLimitBox, upperLimitBox)
                if (rng.validate()) {
                    when (decimalSwitch.isChecked) {
                        true -> {
                            val list = rng.drawDoubles()
                            for (i in 0 until numbersCount) {
                                val decimalFormat = DecimalFormat("##.###")
                                resultBox.append(decimalFormat.format(list[i]).toString() + "   ")
                            }
                        }
                        false -> {
                            if(repeatSwitch.isChecked) {
                                val list = rng.drawIntsWithRepetition()
                                for (i in 0 until numbersCount)
                                    resultBox.append(list[i].toString() + "  ")
                            }else{
                                val list = rng.drawIntsWithoutRepetition()
                                val end = rng.drawWithoutRepetitionNumberCount()
                                for (i in 0 until end)
                                    resultBox.append(list[i].toString() + "  ")
                            }
                        }
                    }
                }else warningBox.text = "Lower limit cannot be greater than upper limit!"
            }else warningBox.text = "Correct all of the input fields!"
        }
        //region textChangedListeners
        lowerLimitBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    warningBox.text = ""
                }
            }
        })
        upperLimitBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrBlank()) {
                    warningBox.text = ""
                }
            }
        })
        //endregion
        //region switchesListeners
        ascendingSwitch.setOnClickListener{
            descendingSwitch.isChecked = false
        }
        descendingSwitch.setOnClickListener{
            ascendingSwitch.isChecked = false
        }
        repeatSwitch.setOnClickListener{
            decimalSwitch.isChecked = false
        }
        decimalSwitch.setOnClickListener{
            repeatSwitch.isChecked = false
        }
        //endregion
    }
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun modifyActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title = "Random words"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    private fun setControls(){
        lowerLimitBox = findViewById(R.id.lowerLimitTextEdit)
        upperLimitBox = findViewById(R.id.upperLimitTextEdit)
        drawButton = findViewById(R.id.generateButton)
        resultBox = findViewById(R.id.resultTextView)
        resultBox.movementMethod = ScrollingMovementMethod()
        warningBox = findViewById(R.id.warningTextView)
        decimalSwitch = findViewById(R.id.decimalSwitch)
        spinner = findViewById(R.id.numbersCountSpinner)
        ascendingSwitch = findViewById(R.id.ascendingSortSwitch)
        descendingSwitch = findViewById(R.id.descendingSortSwitch)
        repeatSwitch = findViewById(R.id.noRepeatSwitch)
    }

    private fun initSpinner(){
        val spinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.count,
                R.layout.spinner_layout)
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        spinner.adapter = spinnerAdapter
    }
}