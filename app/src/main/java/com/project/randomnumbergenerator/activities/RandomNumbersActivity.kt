package com.project.randomnumbergenerator.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.interfaces.ToastManager
import com.project.randomnumbergenerator.model.RandomNumbersGenerator
import java.text.DecimalFormat


class RandomNumbersActivity : AppCompatActivity(), ToastManager{

    override val activityContext: Context = this
    private lateinit var lowerLimitBox: EditText
    private lateinit var upperLimitBox: EditText
    private lateinit var drawButton: Button
    private lateinit var resultBox: TextView
    private lateinit var decimalSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var repeatSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var ascendingSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var descendingSwitch: com.google.android.material.switchmaterial.SwitchMaterial
    private lateinit var spinner: Spinner

    private val drawButtonClickListener = View.OnClickListener { drawButtonClicked() }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_numbers)
        setControls()
        modifyActionBar()

        drawButton.setOnClickListener(drawButtonClickListener)
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

    /**
     * @param view view of rng activity
     */
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    /**
     * Changes style of action bar
     */
    private fun modifyActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title = "Random words"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Sets up all controls (buttons, switches, etc.)
     */
    private fun setControls(){
        lowerLimitBox = findViewById(R.id.lowerLimitTextEdit)
        upperLimitBox = findViewById(R.id.upperLimitTextEdit)
        drawButton = findViewById(R.id.generateButton)
        resultBox = findViewById(R.id.resultTextView)
        resultBox.movementMethod = ScrollingMovementMethod()
        decimalSwitch = findViewById(R.id.decimalSwitch)
        spinner = findViewById(R.id.numbersCountSpinner)
        val spinnerAdapter = ArrayAdapter.createFromResource(this,
            R.array.count,
            R.layout.spinner_layout
        )
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        spinner.adapter = spinnerAdapter
        ascendingSwitch = findViewById(R.id.ascendingSortSwitch)
        descendingSwitch = findViewById(R.id.descendingSortSwitch)
        repeatSwitch = findViewById(R.id.noRepeatSwitch)
    }

    /**
     * Action performed on draw button clicked
     */
    private fun drawButtonClicked(){
        resultBox.text=""
        val numbersCount = spinner.selectedItem.toString().toInt()
        if(!lowerLimitBox.text.isNullOrEmpty() && !upperLimitBox.text.isNullOrEmpty()) {
            val rng =
                    RandomNumbersGenerator(
                            numbersCount,
                            lowerLimitBox,
                            upperLimitBox
                    )
            rng.setSortOptions(ascendingSwitch.isChecked, descendingSwitch.isChecked)
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
                        if (repeatSwitch.isChecked) {
                            val list = rng.drawIntsWithRepetition()
                            for (i in 0 until numbersCount)
                                resultBox.append(list[i].toString() + "  ")
                        } else {
                            val list = rng.drawIntsWithoutRepetition()
                            val end = rng.drawWithoutRepetitionNumberCount()
                            for (i in 0 until end)
                                resultBox.append(list[i].toString() + "  ")
                        }
                    }
                }
            } else longToast("Lower limit cannot be greater than upper limit!")
        }else shortToast("Correct all of the input fields!")
    }
}