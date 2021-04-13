package com.project.randomnumbergenerator.model

import android.content.Context
import android.widget.ImageView
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.interfaces.ToastManager

class CoinTosser(private val coinImage: ImageView, context: Context): ToastManager {

    override val activityContext: Context = context
    private var animationFinished: Boolean = true

    /**
     * Method draws side of a coin and returns a result
     */
    fun drawSide(): Int{
        return if((0..1).random() == 1){
            tossACoin(R.drawable.tails, "Tails")
            1
        } else{
            tossACoin(R.drawable.heads, "Heads")
            0
        }
    }

    fun isAnimationFinished(): Boolean{
        return this.animationFinished
    }

    /**
     * Method performs a tossing animation
     * @param imageID id of drawable
     * @param coinSide side name - head or tails
     */
    private fun tossACoin(imageID: Int, coinSide: String){
        this.coinImage.animate().apply {
            duration = 1000
            rotationYBy(1800f)
            coinImage.isClickable = false
            animationFinished = false
        }.withEndAction{
            coinImage.setImageResource(imageID)
            shortToast(coinSide)
            coinImage.isClickable = true
            animationFinished = true
        }.start()
    }
}