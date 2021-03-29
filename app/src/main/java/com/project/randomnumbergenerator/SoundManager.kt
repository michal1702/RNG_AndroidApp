package com.project.randomnumbergenerator

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.widget.Toast
import java.io.File
import java.io.IOException
import java.lang.Exception

class SoundManager(var context: Context) {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var outputPath: String? = null

    init {
        outputPath = context.cacheDir.absolutePath
    }
    fun startRecording(context: Context, fileName: String?){
        recorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile("$outputPath/$fileName.3gp")
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
                start()
                Toast.makeText(context, "Recording started", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Toast.makeText(context, "Cannot record audio!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun stopRecording(){
        recorder?.apply {
            stop()
            release()
            Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show()
        }
        recorder = null
    }

    fun playRecording(fileName: String?){
        player = MediaPlayer()
        player?.apply {
            try{
                setDataSource("$outputPath/$fileName.3gp")
                prepare()
                start()
            }catch(e: IOException){
                Toast.makeText(context, "Cannot play this sound", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun removeFile(fileName: String?){
        try {
            File("$outputPath/$fileName.3gp").delete()
        }catch (e: Exception){
            Toast.makeText(context, "Cannot delete this file!", Toast.LENGTH_SHORT).show()
        }
    }
}