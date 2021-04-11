package com.project.randomnumbergenerator.interfaces

import android.content.Context
import android.widget.Toast

interface ToastManager {
    val context: Context

    fun shortToast(message: String){
        Toast.makeText(this.context, message,Toast.LENGTH_SHORT).show()
    }

    fun longToast(message: String){
        Toast.makeText(this.context, message,Toast.LENGTH_LONG).show()
    }
}