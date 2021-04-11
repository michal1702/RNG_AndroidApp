package com.project.randomnumbergenerator.interfaces

import android.content.Context
import android.widget.Toast

interface ToastManager {
    val context: Context

    /**
     * Shows quick toast
     * @param message toast message
     */
    fun shortToast(message: String){
        Toast.makeText(this.context, message,Toast.LENGTH_SHORT).show()
    }

    /**
     * Shows long toast
     * @param message toast message
     */
    fun longToast(message: String){
        Toast.makeText(this.context, message,Toast.LENGTH_LONG).show()
    }
}