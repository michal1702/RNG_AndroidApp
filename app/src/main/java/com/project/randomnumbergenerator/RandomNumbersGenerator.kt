package com.project.randomnumbergenerator

import android.widget.EditText
import kotlin.random.Random

class RandomNumbersGenerator(private val numbersCount: Int,
                             lowerLimitEditText: EditText,
                             upperLimitEditText: EditText) {
    private var lowerLimit: Int? = null
    private var upperLimit: Int? = null
    var sorting: SortOptions = SortOptions.None

    init {
        this.lowerLimit = lowerLimitEditText.text.toString().toIntOrNull()
        this.upperLimit = upperLimitEditText.text.toString().toIntOrNull()
    }

    enum class SortOptions(val value:Int){
        Ascending(1),
        Descending(2),
        None(0)
    }

    /**
     *  Set sorting method
     *  @param asc ascendingSwitch value
     *  @param desc descendingSwitch value
     */
    fun setSortOptions(asc: Boolean, desc: Boolean){
        sorting = when {
            asc -> SortOptions.Ascending
            desc -> SortOptions.Descending
            else -> SortOptions.None
        }
    }

    /**
     * Checks if lower limit isn't greater than upper limit
     * @return if lower limit is greater than upper limit
     */
    fun validate(): Boolean{
        return this.lowerLimit!! < this.upperLimit!!
    }

    /**
     * Draws array of doubles and eventually sorts it
     * @return double array
     */
    fun drawDoubles(): Array<Double>{
        val doubleList = mutableListOf<Double>()
        for (i in 0 until numbersCount) {
            doubleList.add(Random.nextDouble(this.lowerLimit?.toDouble()!!,
                    this.upperLimit?.toDouble()!!))
        }
        val arr: Array<Double> = doubleList.toTypedArray()
        if(sorting == SortOptions.Ascending) arr.sort()
        else if(sorting == SortOptions.Descending) arr.sortDescending()
        return arr
    }

    /**
     * Draws array of ints with repetitions and eventually sorts it
     * @return int array
     */
    fun drawIntsWithRepetition(): Array<Int>{
        val intList = mutableListOf<Int>()
        for (i in 0 until numbersCount) {
            intList.add(Random.nextInt(this.lowerLimit!!, this.upperLimit!!))
        }
        val arr: Array<Int> = intList.toTypedArray()
        if(sorting == SortOptions.Ascending) arr.sort()
        else if(sorting == SortOptions.Descending) arr.sortDescending()
        return arr
    }

    /**
     * Draws array of ints without repetitions and eventually sorts it
     * @return int array
     */
    fun drawIntsWithoutRepetition(): Array<Int>{
        val numbersInRange = (this.upperLimit!! - this.lowerLimit!!)+1
        val arr: Array<Int> = if(numbersInRange<this.numbersCount) (this.lowerLimit!! .. this.upperLimit!!).shuffled().take(numbersInRange).toTypedArray()
        else (this.lowerLimit!!..this.upperLimit!!).shuffled().take(this.numbersCount).toTypedArray()

        if(sorting == SortOptions.Ascending) arr.sort()
        else if(sorting == SortOptions.Descending) arr.sortDescending()
        return arr
    }

    /**
     * Checks a count of numbers to be drawn
     * @return numbers count
     */
    fun drawWithoutRepetitionNumberCount(): Int{
        return if((this.upperLimit!! - this.lowerLimit!!)+1 < this.numbersCount)
            (this.upperLimit!! - this.lowerLimit!!)+1
        else
            this.numbersCount
    }
}