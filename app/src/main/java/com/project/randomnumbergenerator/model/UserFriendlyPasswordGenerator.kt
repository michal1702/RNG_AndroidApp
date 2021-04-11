package com.project.randomnumbergenerator.model

import java.util.*
import kotlin.random.Random

@ExperimentalStdlibApi
class UserFriendlyPasswordGenerator {
    private var password: String = ""
    private var separators: List<String> = listOf(".","-","_","|")
    private var specialCharacters: List<String> = listOf("@","#","$","%","&","*")

    /**
     * Generates password based on words given by the user
     * @param stringArray array of words
     */
    private fun generatePasswordBasedOnWords(stringArray: Array<String>){
        val separatorPosition = Random.nextInt(0, separators.size)
        val separator = separators[separatorPosition]
        val specialCharacterPosition = Random.nextInt(0, specialCharacters.size)
        val specialCharacter = specialCharacters[specialCharacterPosition]

        password = password + specialCharacter + stringArray[0].capitalize(Locale.ROOT) + separator
        password = password + stringArray[1].capitalize(Locale.ROOT) + separator
        password += stringArray[2].capitalize(Locale.ROOT) + specialCharacter

        replaceLetters()
    }

    /**
     * Replaces certain letters in a word
     */
    private fun replaceLetters(){
        password = password.replace("i","1")
        password = password.replace("a","@")
        password = password.replace("e","3")
        password = password.replace("E","3")
    }

    /**
     * Returns password
     */
    fun getPasswordBasedOnWords(stringArray: Array<String>): String{
        generatePasswordBasedOnWords(stringArray)
        return password
    }
}