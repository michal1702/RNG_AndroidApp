package com.project.randomnumbergenerator

import android.widget.EditText
import kotlin.random.Random

class RandomNumbersGenerator(private val numbersCount: Int,
                             lowerLimitEditText: EditText,
                             upperLimitEditText: EditText) {
    private var lowerLimit: Int? = null
    private var upperLimit: Int? = null

    init {
        this.lowerLimit = lowerLimitEditText.text.toString().toIntOrNull()
        this.upperLimit = upperLimitEditText.text.toString().toIntOrNull()
    }

    fun validate(): Boolean{
        return this.lowerLimit!! < this.upperLimit!!
    }

    fun drawDoubles(): List<Double>{
        val doubleList = mutableListOf<Double>()
        for (i in 0 until numbersCount) {
            doubleList.add(Random.nextDouble(this.lowerLimit?.toDouble()!!,
                    this.upperLimit?.toDouble()!!))
        }
        return doubleList
    }

    fun drawIntsWithRepetition(): List<Int>{
        val intList = mutableListOf<Int>()
        for (i in 0 until numbersCount) {
            intList.add(Random.nextInt(this.lowerLimit!!, this.upperLimit!!))
        }
        return intList
    }

    fun drawIntsWithoutRepetition(): List<Int>{
        val numbersInRange = (this.upperLimit!! - this.lowerLimit!!)+1
        return if(numbersInRange<this.numbersCount){
            (this.lowerLimit!! .. this.upperLimit!!).shuffled().take(numbersInRange)
        }
        else (this.lowerLimit!! .. this.upperLimit!!).shuffled().take(this.numbersCount)
    }

    fun drawWithoutRepetitionNumberCount(): Int{
        return if((this.upperLimit!! - this.lowerLimit!!)+1 < this.numbersCount)
            (this.upperLimit!! - this.lowerLimit!!)+1
        else
            this.numbersCount
    }
}