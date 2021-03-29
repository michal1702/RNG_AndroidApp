package com.project.randomnumbergenerator

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import org.w3c.dom.Text
import java.text.DecimalFormat
import kotlin.random.Random

class RandomNumbersActivity : AppCompatActivity() {

    private lateinit var lowerLimitBox: EditText
    private lateinit var upperLimitBox: EditText
    private lateinit var button: Button
    private lateinit var resultBox: TextView
    private lateinit var warningBox: TextView
    private lateinit var decimalSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var repeatSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var ascendingSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var descendingSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var numbersCount: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_numbers)

        modifyActionBar()
        setVariables()

        button.setOnClickListener(){
            if(lowerLimitBox.text.isEmpty() or upperLimitBox.text.isEmpty())
                warningBox.text = "Fill all of the fields"
            else {
                val lowerLimit = getLowerLimit(lowerLimitBox)
                val upperLimit = getUpperLimit(upperLimitBox)
                if (lowerLimit != null && upperLimit != null) {
                    if (lowerLimit.compareTo(upperLimit) > 0)
                        warningBox.text = "Lower limit cannot be greater than upper limit!"
                    else {
                        if (decimalSwitch.isChecked) {
                            val rand = Random.nextDouble(lowerLimit, upperLimit)
                            val decimalFormat = DecimalFormat("##.###")
                            resultBox.text = decimalFormat.format(rand).toString()
                        } else {
                            val rand = Random.nextInt(lowerLimit.toInt(), upperLimit.toInt())
                            resultBox.text = rand.toString()
                        }
                    }
                } else warningBox.text = "Enter valid values!"
            }
        }
        //region textChangedListeners
        lowerLimitBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrBlank()) {
                    warningBox.text = ""
                }
            }
        })
        upperLimitBox.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(!s.isNullOrBlank()) {
                    warningBox.text = ""
                }
            }
        })
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

    private fun setVariables(){
        lowerLimitBox = findViewById(R.id.lowerLimitTextEdit)
        upperLimitBox = findViewById(R.id.upperLimitTextEdit)
        button = findViewById(R.id.generateButton)
        resultBox = findViewById(R.id.resultTextView)
        warningBox = findViewById(R.id.warningTextView)
        decimalSwitch = findViewById(R.id.decimalSwitch)
    }

    private fun getLowerLimit(lowerLimit: EditText): Double?{
        return lowerLimit.text.toString().toDoubleOrNull()
    }

    private fun getUpperLimit(upperLimit: EditText): Double?{
        return upperLimit.text.toString().toDoubleOrNull()
    }
}