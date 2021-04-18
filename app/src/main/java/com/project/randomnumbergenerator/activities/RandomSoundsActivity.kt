package com.project.randomnumbergenerator.activities

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.project.randomnumbergenerator.R
import com.project.randomnumbergenerator.model.SoundManager
import com.project.randomnumbergenerator.listitems.SoundsListItem
import com.project.randomnumbergenerator.adapters.SoundsListAdapter
import com.project.randomnumbergenerator.databinding.ActivityRandomSoundsBinding
import com.project.randomnumbergenerator.interfaces.KeyboardHide
import com.project.randomnumbergenerator.interfaces.ToastManager
import java.util.concurrent.TimeUnit
import kotlin.random.Random


private const val RECORD_AUDIO_PERMISSION = 200

class RandomSoundsActivity : AppCompatActivity(), ToastManager, KeyboardHide {
    override val activity: Activity = this
    override val activityContext: Context = this

    private lateinit var binding: ActivityRandomSoundsBinding
    private lateinit var soundsArray: ArrayList<SoundsListItem>
    private lateinit var soundsListAdapter: SoundsListAdapter
    private var permissionToRecordAccepted = false
    private var isRecording = false
    private var skip = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var manager: SoundManager? = null

    private val recordButtonClickListener = View.OnClickListener { recordButtonClicked() }
    private val drawButtonClickListener = View.OnClickListener { drawButtonClicked() }
    private val clearButtonClickListener = View.OnClickListener { clearButtonClicked() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRandomSoundsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ActivityCompat.requestPermissions(this, permissions, RECORD_AUDIO_PERMISSION)
        manager = SoundManager(this)
        soundsArray = ArrayList()
        setControls()
        modifyActionBar()

        binding.randomSoundsConstraintLayout.setOnClickListener { hideKeyboard() }
        binding.recordSoundImageView.setOnClickListener(recordButtonClickListener)
        binding.generateSoundButton.setOnClickListener(drawButtonClickListener)
        binding.clearSoundsButton.setOnClickListener(clearButtonClickListener)
        binding.skipSoundImageView.setOnClickListener{ skip = true }
    }

    /**
     * Sets up controls like buttons, text fields, etc.
     */
    private fun setControls(){
        binding.skipSoundImageView.isEnabled = false
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<out String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if(requestCode == RECORD_AUDIO_PERMISSION){
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        }
        else false
        if(!permissionToRecordAccepted) finish()
    }

    /**
     * Sets up action bar
     */
    private fun modifyActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title = "Random sounds"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Runs another thread that plays sound drew by user
     * @param soundName sound name
     */
    private fun runSoundPlayingThread(soundName: Int){
        var currentPosition: Int
        manager?.playSound("sound-$soundName")
        runOnUiThread {
            binding.generateSoundButton.isEnabled = false
            binding.clearSoundsButton.isEnabled = false
            binding.recordSoundImageView.isEnabled = false
            binding.skipSoundImageView.isEnabled = true
        }
        val duration = manager?.getDuration()!!
        binding.soundProgressBar.max = duration
        while(manager?.getCurrentPosition()!! < duration) {
            currentPosition = manager?.getCurrentPosition()!!
            binding.soundProgressBar.progress = currentPosition
            runOnUiThread {
                val stringFormat = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(currentPosition.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(currentPosition.toLong()))
                binding.soundTimeTextView.text = stringFormat
            }
            if(skip) {
                manager?.skipSound()
                skip = false
                break
            }
        }
        runOnUiThread {
            binding.soundTimeTextView.text = "00:00"
            binding.generateSoundButton.isEnabled = true
            binding.clearSoundsButton.isEnabled = true
            binding.recordSoundImageView.isEnabled = true
            binding.skipSoundImageView.isEnabled = false
        }
        binding.soundProgressBar.progress = 0
    }

    /**
     * Record sound on clicked
     */
    private fun recordButtonClicked(){
        if(!binding.soundNameEditText.text.isNullOrEmpty()) {
            val itemNumber: Int = soundsArray.size
            val soundName: String = binding.soundNameEditText.text.toString()
            if (isRecording) {
                binding.recordSoundImageView.setImageResource(R.drawable.ic_microphone)
                manager?.stopRecording()
                soundsListAdapter = SoundsListAdapter(applicationContext, soundsArray)
                binding.soundsListView.adapter = soundsListAdapter
                soundsArray.add(SoundsListItem(soundName, "sound-$itemNumber"))
                isRecording = false
                binding.soundNameEditText.setText("")
            } else {
                manager?.startRecording(this, "sound-$itemNumber")
                binding.recordSoundImageView.setImageResource(R.drawable.ic_pause)
                isRecording = true
            }
        }
        else longToast("Enter sound name")
    }

    /**
     * Draw on click
     */
    private fun drawButtonClicked(){
        if(soundsArray.isNotEmpty()){
            val position = Random.nextInt(0, soundsArray.size)
            Thread {
                runSoundPlayingThread(position)
            }.start()
            shortToast("Now playing: " + soundsArray[position].nameOfSound)
        }
    }

    /**
     * Clears on click
     */
    private fun clearButtonClicked(){
        if(soundsArray.isNotEmpty()) {
            soundsArray.clear()
            soundsListAdapter.notifyDataSetChanged()
        }
    }
}