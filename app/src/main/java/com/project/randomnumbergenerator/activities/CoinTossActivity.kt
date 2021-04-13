package com.project.randomnumbergenerator.activities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.interfaces.ToastManager
import com.project.randomnumbergenerator.model.CoinTosser
import kotlin.math.sqrt

class CoinTossActivity : AppCompatActivity(), ToastManager, SensorEventListener {

    override val activityContext: Context = this
    private lateinit var coinImage: ImageView
    private lateinit var headsCount: TextView
    private lateinit var tailsCount: TextView
    private lateinit var clearButton: Button
    private lateinit var sensorManager: SensorManager
    private lateinit var tosser: CoinTosser

    private var accelerometer: Sensor? = null

    private var accelerometerLastValue = 0.0f
    private var accelerometerValue = 0.0f
    private var shake = 0.0f

    private val coinTossClickListener = View.OnClickListener { tossCoinClicked() }
    private val clearButtonClickListener = View.OnClickListener { clearClicked() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coin_toss)
        setControls()
        initiateSensor()
        modifyActionBar()
        tosser = CoinTosser(this.coinImage,this)
        this.coinImage.setOnClickListener(coinTossClickListener)
        this.clearButton.setOnClickListener(clearButtonClickListener)
    }

    /**
     * Method executes coin toss and changes a score
     */
    private fun tossCoinClicked(){
        when(tosser.drawSide()){
            0 -> headsCount.text = (headsCount.text.toString().toInt() + 1).toString()
            1 -> tailsCount.text = (tailsCount.text.toString().toInt() + 1).toString()
        }
    }

    /**
     * Method clears head/tails count
     */
    private fun clearClicked(){
        headsCount.text = "0"
        tailsCount.text = "0"
    }

    /**
     * Sets up all controls (buttons, switches, etc.)
     */
    private fun setControls(){
        this.coinImage = findViewById(R.id.coinImage)
        this.headsCount = findViewById(R.id.headsCountTextView)
        this.tailsCount = findViewById(R.id.tailsCountTextView)
        this.clearButton = findViewById(R.id.clearCoinTossCount)
    }

    /**
     * Changes style of action bar
     */
    private fun modifyActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title = "Coin Toss"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Creates instance of SensorManager
     */
    private fun initiateSensor(){
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)
        accelerometerLastValue = SensorManager.GRAVITY_EARTH
        accelerometerValue = SensorManager.GRAVITY_EARTH
        shake = 0.0f
    }

    override fun onSensorChanged(event: SensorEvent) {
        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        accelerometerLastValue = accelerometerValue
        accelerometerValue = sqrt(x*x + y*y + z*z)
        val delta = accelerometerValue - accelerometerLastValue
        shake = shake * 0.9f + delta

        if(shake>12 && tosser.isAnimationFinished()){
            tossCoinClicked()
        }
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }
}