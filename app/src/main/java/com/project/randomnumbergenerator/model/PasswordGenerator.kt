package com.project.randomnumbergenerator.model

abstract class PasswordGenerator {
    protected var password: String = ""

    /**
     * Method is responsible for generating password
     */
    protected abstract fun generatePassword()

    /**
     * Method returns generated password
     */
    fun getGeneratedPassword():String{
        generatePassword()
        return password
    }
}