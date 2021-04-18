package com.project.randomnumbergenerator.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.databinding.ActivityRandomNumbersBinding
import com.project.randomnumbergenerator.interfaces.KeyboardHide
import com.project.randomnumbergenerator.interfaces.ToastManager
import com.project.randomnumbergenerator.model.RandomNumbersGenerator
import java.text.DecimalFormat


class RandomNumbersActivity : AppCompatActivity(), ToastManager, KeyboardHide{

    override val activity: Activity = this
    override val activityContext: Context = this
    private lateinit var binding: ActivityRandomNumbersBinding

    private val drawButtonClickListener = View.OnClickListener { drawButtonClicked() }

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomNumbersBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setControls()
        modifyActionBar()

        binding.generateButton.setOnClickListener(drawButtonClickListener)
        binding.randomNumbersConstraintLayout.setOnClickListener { hideKeyboard() }
        //region switchesListeners
        binding.ascendingSortSwitch.setOnClickListener{
            binding.descendingSortSwitch.isChecked = false
        }
        binding.descendingSortSwitch.setOnClickListener{
            binding.ascendingSortSwitch.isChecked = false
        }
        binding.repeatSwitch.setOnClickListener{
            binding.decimalSwitch.isChecked = false
        }
        binding.decimalSwitch.setOnClickListener{
            binding.repeatSwitch.isChecked = false
        }
        //endregion
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
        binding.resultTextView.movementMethod = ScrollingMovementMethod()
        val spinnerAdapter = ArrayAdapter.createFromResource(this,
            R.array.count,
            R.layout.spinner_layout
        )
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout)
        binding.numbersCountSpinner.adapter = spinnerAdapter
    }

    /**
     * Action performed on draw button clicked
     */
    private fun drawButtonClicked(){
        binding.resultTextView.text=""
        val numbersCount = binding.numbersCountSpinner.selectedItem.toString().toInt()
        if(!binding.lowerLimitTextEdit.text.isNullOrEmpty() && !binding.upperLimitTextEdit.text.isNullOrEmpty()) {
            val rng =
                    RandomNumbersGenerator(
                            numbersCount,
                        binding.lowerLimitTextEdit,
                        binding.upperLimitTextEdit
                    )
            rng.setSortOptions(binding.ascendingSortSwitch.isChecked, binding.descendingSortSwitch.isChecked)
            if (rng.validate()) {
                when (binding.decimalSwitch.isChecked) {
                    true -> {
                        val list = rng.drawDoubles()
                        for (i in 0 until numbersCount) {
                            val decimalFormat = DecimalFormat("##.###")
                            binding.resultTextView.append(decimalFormat.format(list[i]).toString() + "   ")
                        }
                    }
                    false -> {
                        if (binding.repeatSwitch.isChecked) {
                            val list = rng.drawIntsWithRepetition()
                            for (i in 0 until numbersCount)
                                binding.resultTextView.append(list[i].toString() + "  ")
                        } else {
                            val list = rng.drawIntsWithoutRepetition()
                            val end = rng.drawWithoutRepetitionNumberCount()
                            for (i in 0 until end)
                                binding.resultTextView.append(list[i].toString() + "  ")
                        }
                    }
                }
            } else longToast("Lower limit cannot be greater than upper limit!")
        }else shortToast("Correct all of the input fields!")
    }
}