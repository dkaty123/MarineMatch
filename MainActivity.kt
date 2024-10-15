

package com.codelab.basiclayouts


import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import java.util.Locale


class MainActivity : ComponentActivity() {


    private lateinit var textToSpeech: TextToSpeech
    private lateinit var speakButton: Button
    private lateinit var myButton: Button
    private lateinit var myTextView: TextView
    private lateinit var myLogo: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            setContentView(R.layout.lol_name)


            myButton = findViewById<Button>(R.id.button)
            myTextView = findViewById<TextView>(R.id.textView2)
            myLogo = findViewById<ImageView>(R.id.imageView2)

            textToSpeech = TextToSpeech(this) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    // TextToSpeech engine is successfully initialized
                    textToSpeech.language = Locale.ENGLISH
                    myButton.isEnabled = true
                    val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                    val increasedVolume = (maxVolume * 20).toInt() // Increase by 80%
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)

                    // Listener for button
                    myButton.setOnHoverListener { view, event ->
                        when (event.action) {
                            // Start speech synthesis when hovering over the button
                            android.view.MotionEvent.ACTION_HOVER_ENTER -> {
                                val text = "Button text to speech"
                                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                            }
                            // Stop speech synthesis when hover ends
                            android.view.MotionEvent.ACTION_HOVER_EXIT -> {
                                textToSpeech.stop()
                            }
                        }
                        true
                    }

                    // Listener for TEXT
                    myTextView.setOnTouchListener(object : View.OnTouchListener {
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                            when (event?.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    // Touch event started, speak the text
                                    val text = myTextView.text.toString()
                                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                                }
                            }
                            return true
                        }
                    })

                    myLogo.setOnTouchListener(object : View.OnTouchListener {
                        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                            when (event?.action) {
                                MotionEvent.ACTION_DOWN -> {
                                    val text = "Global Vision"
                                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                                }
                            }
                            return true
                        }
                    })

                } else {
                    // TextToSpeech engine initialization failed
                    myButton.isEnabled = false
                }
            }



            myButton?.setOnClickListener {

                val text = "Continue"
                textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                val intent = Intent(this@MainActivity, MainActivity5::class.java)
                startActivity(intent)
            }







        }



    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown() // Shutdown the TextToSpeech engine to release resources
    }




}