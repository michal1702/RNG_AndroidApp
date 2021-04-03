package com.project.randomnumbergenerator

import android.content.Context
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.widget.Toast
import java.io.File
import java.io.IOException

class SoundManager(var context: Context) {
    private var recorder: MediaRecorder? = null
    private var player: MediaPlayer? = null
    private var outputPath: String? = null

    init {
        this.outputPath = this.context.cacheDir.absolutePath
        this.player = MediaPlayer()
        this.player!!.setOnCompletionListener {
            this.player?.release()
        }
    }

    /**
     * Method responsible for recording. It saves a recorded sound in application cache with *.3gp format.
     * @param context app context
     * @param fileName name of the file.
     */
    fun startRecording(context: Context, fileName: String?){
        this.recorder = MediaRecorder().apply {
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

    /**
     * It is complementary method for startRecording which stops MediaRecorder and releases it
     */
    fun stopRecording(){
        this.recorder?.apply {
            stop()
            release()
            Toast.makeText(context, "Recording stopped", Toast.LENGTH_SHORT).show()
        }
        this.recorder = null
    }

    /**
     * Plays sound file with given filename
     * @param fileName file name
     */
    fun playSound(fileName: String?){
        this.player = MediaPlayer().apply {
            try {
                setDataSource("$outputPath/$fileName.3gp")
                prepare()
                start()
            } catch (e: IOException) {
                Toast.makeText(context, "Cannot play this sound", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Method removes file with given name from cache
     * @param fileName file name
     */
    fun removeFile(fileName: String?){
        try {
            File("${this.outputPath}/$fileName.3gp").delete()
        }catch (e: Exception){
            Toast.makeText(this.context, "Cannot delete this file!", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Returns current position in audio file
     * @return current position in milliseconds
     */
    fun getCurrentPosition(): Int{
        return this.player?.currentPosition!!
    }

    /**
     * Returns duration of the audio file
     * @return duration in milliseconds
     */
    fun getDuration(): Int{
        return this.player?.duration!!
    }
}