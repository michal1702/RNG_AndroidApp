package com.project.randomnumbergenerator

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.concurrent.TimeUnit
import kotlin.random.Random


private const val RECORD_AUDIO_PERMISSION = 200

class RandomSoundsActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var drawButton: Button
    private lateinit var recordButton: ImageView
    private lateinit var soundNameTextEdit: EditText
    private lateinit var clearSounds: Button
    private lateinit var soundsListAdapter: SoundsListAdapter
    private lateinit var soundProgressBar: ProgressBar
    private lateinit var soundTime: TextView
    private lateinit var skipSound: ImageView
    private lateinit var soundsArray: ArrayList<SoundsListItem>
    private var permissionToRecordAccepted = false
    private var isRecording = false
    private var skip = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    private var manager: SoundManager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random_sounds)
        ActivityCompat.requestPermissions(this, permissions, RECORD_AUDIO_PERMISSION)
        manager = SoundManager(this)
        soundsArray = ArrayList()
        setControls()
        modifyActionBar()

        recordButton.setOnClickListener{
            if(!soundNameTextEdit.text.isNullOrEmpty()) {
                val itemNumber: Int = soundsArray.size
                val soundName: String = soundNameTextEdit.text.toString()
                if (isRecording) {
                    recordButton.setImageResource(R.drawable.ic_microphone)
                    manager?.stopRecording()
                    soundsListAdapter = SoundsListAdapter(applicationContext, soundsArray)
                    listView.adapter = soundsListAdapter
                    soundsArray.add(SoundsListItem(soundName, "sound-$itemNumber"))
                    isRecording = false
                    soundNameTextEdit.setText("")
                } else {
                    manager?.startRecording(this, "sound-$itemNumber")
                    recordButton.setImageResource(R.drawable.ic_pause)
                    isRecording = true
                }
            }
            else Toast.makeText(this, "Enter sound name", Toast.LENGTH_LONG).show()
        }
        drawButton.setOnClickListener{
            if(soundsArray.isNotEmpty()){
                val position = Random.nextInt(0, soundsArray.size)
                Thread {
                    runSoundPlayingThread(position)
                }.start()
               Toast.makeText(this, "Now playing: " + soundsArray[position].nameOfSound, Toast.LENGTH_SHORT).show()
            }
        }

        clearSounds.setOnClickListener{
            if(soundsArray.isNotEmpty()) {
                soundsArray.clear()
                soundsListAdapter.notifyDataSetChanged()
            }
        }

        skipSound.setOnClickListener{
            skip = true
        }
    }

    /**
     * Sets up controls like buttons, text fields, etc.
     */
    private fun setControls(){
        listView = findViewById(R.id.soundsListView)
        drawButton = findViewById(R.id.generateSoundButton)
        recordButton = findViewById(R.id.recordSoundImageView)
        soundNameTextEdit = findViewById(R.id.soundNameEditText)
        clearSounds = findViewById(R.id.clearSoundsButton)
        soundProgressBar = findViewById(R.id.soundProgressBar)
        soundTime = findViewById(R.id.soundTimeTextView)
        skipSound = findViewById(R.id.skipSoundImageView)
        skipSound.isEnabled = false
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
     * Method hides keyboard on focus off
     * @param view view of this activity
     */
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
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
            drawButton.isEnabled = false
            clearSounds.isEnabled = false
            recordButton.isEnabled = false
            skipSound.isEnabled = true
        }
        val duration = manager?.getDuration()!!
        soundProgressBar.max = duration
        while(manager?.getCurrentPosition()!! < duration) {
            currentPosition = manager?.getCurrentPosition()!!
            soundProgressBar.progress = currentPosition
            runOnUiThread {
                val stringFormat = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(currentPosition.toLong()),
                    TimeUnit.MILLISECONDS.toSeconds(currentPosition.toLong()))
                soundTime.text = stringFormat
            }
            if(skip) {
                manager?.skipSound()
                skip = false
                break
            }
        }
        runOnUiThread {
            soundTime.text = "00:00"
            drawButton.isEnabled = true
            clearSounds.isEnabled = true
            recordButton.isEnabled = true
            skipSound.isEnabled = false
        }
        soundProgressBar.progress = 0
    }
}