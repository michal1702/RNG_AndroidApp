package com.project.randomnumbergenerator.model

import junit.framework.TestCase
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(value = Parameterized::class)
class RandomNumbersGeneratorTest(private val numbersCountTest: Int, private val lowerLimitTest: Int, private val upperLimitTest: Int): TestCase() {

    companion object{
        @JvmStatic
        @Parameterized.Parameters
        fun data(): Iterable<Array<Int>> {
            return arrayListOf(
                    arrayOf(1, -1, 1),
                    arrayOf(2, -1, 1),
                    arrayOf(3, -1, 1),
                    arrayOf(1, 1999, 2001),
                    arrayOf(2, 1999, 2001),
                    arrayOf(3, 1999, 2001)
            ).toList()
        }
    }

    @Test
    fun testDrawDoubles() {
        val rng = RandomNumbersGenerator(numbersCountTest, lowerLimitTest, upperLimitTest)
        val list = rng.drawDoubles()
        var isCorrect = true
        for(item in list){
            if(item > upperLimitTest || item < lowerLimitTest) isCorrect = false
        }
        assertTrue(isCorrect)
    }

    @Test
    fun testDoublesAscending(){
        val rng = RandomNumbersGenerator(numbersCountTest, lowerLimitTest, upperLimitTest)
        rng.setSortOptions(asc = true, desc = false)
        val list = rng.drawDoubles()
        var isCorrect = true
        var prev = list[0]
        for(item in list){
            if(item > upperLimitTest || item < lowerLimitTest){
                if(prev > item){
                    isCorrect = false
                    prev = item
                }
            }
        }
        assertTrue(isCorrect)
    }

    @Test
    fun testDoublesDescending(){
        val rng = RandomNumbersGenerator(numbersCountTest, lowerLimitTest, upperLimitTest)
        rng.setSortOptions(asc = false, desc = true)
        val list = rng.drawDoubles()
        var isCorrect = true
        var prev = list[0]
        for(item in list){
            if(item > upperLimitTest || item < lowerLimitTest){
                if(prev < item){
                    isCorrect = false
                    prev = item
                }
            }
        }
        assertTrue(isCorrect)
    }
}