package com.project.randomnumbergenerator.model

import kotlin.random.Random

class FullyRandomPasswordGenerator(private val charactersCount: Int): PasswordGenerator() {

    override fun generatePassword() {
        for(i in 0 until charactersCount){
            password+= Random.nextInt(33,126).toChar()
        }
    }
}