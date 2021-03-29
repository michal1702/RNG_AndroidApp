package com.project.randomnumbergenerator

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.ImageView
import android.widget.ListView
import androidx.core.app.ActivityCompat

private const val RECORD_AUDIO_PERMISSION = 200

class RandomSoundsActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var generateButton: Button
    private lateinit var recordButton: ImageView
    private lateinit var soundsListAdapter: SoundsListAdapter
    private lateinit var soundsArray: ArrayList<SoundsListItem>
    private var permissionToRecordAccepted = false
    private var isRecording = false
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
            val itemNumber: Int = soundsArray.size
            if(isRecording){
                recordButton.setImageResource(R.drawable.ic_microphone)
                manager?.stopRecording()
                soundsListAdapter = SoundsListAdapter(applicationContext, soundsArray)
                listView.adapter = soundsListAdapter
                soundsArray.add(SoundsListItem("Sound no. $itemNumber", "sound-$itemNumber"))
                isRecording = false
            }else {
                manager?.startRecording(this, "sound-$itemNumber")
                recordButton.setImageResource(R.drawable.ic_pause)
                isRecording = true
            }
        }
        generateButton.setOnClickListener{
        }
    }

    private fun setControls(){
        listView = findViewById(R.id.soundsListView)
        generateButton = findViewById(R.id.generateSoundButton)
        recordButton = findViewById(R.id.recordSoundImageView)
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
    fun hideKeyboard(view: View) {
        val inputMethodManager: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

    private fun modifyActionBar(){
        val actionBar = supportActionBar
        actionBar!!.title = "Random sounds"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}