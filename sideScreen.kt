package com.codelab.basiclayouts

import android.content.Intent
import android.content.res.Configuration
import android.media.AudioManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Spa
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import java.util.Locale

class sideScreen : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EvenGreetings()

            textToSpeech = TextToSpeech(this) { status ->
                if (status == TextToSpeech.SUCCESS) {
                    // TextToSpeech engine is successfully initialized
                    textToSpeech.language = Locale.ENGLISH
                    val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
                    val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
                    val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
                    val increasedVolume = (maxVolume * 20).toInt() // Increase by 80%
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, maxVolume, 0)


                    // Listener for TEXT

                } else {
                    // TextToSpeech engine initialization failed
                }
            }


        }
    }
    // START OF START SCREEN


    @Composable
    fun OnboardingScreen(
        onContinueClicked: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to GlobalVision")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }

    @Composable
    fun Greetings(
        modifier: Modifier = Modifier,
        names: List<String> = List(1000) { "$it" }
    ) {
        Box(modifier = modifier.fillMaxSize()) {
        LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
            items(items = names) { name ->
                Greeting(name = name)
            }
        }

            SootheBottomNavigation(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }

    @Composable
    fun EvenGreetings(
        modifier: Modifier = Modifier,
        names: List<String> = List(1000) { "$it" }
    ) {
        Box(modifier = modifier.fillMaxSize()) {
            LazyColumn(modifier = modifier.padding(vertical = 4.dp)) {
                items(alignYourBodyData) { item ->
                    FunnyEvenBetterAlignYourBodyElement(item.drawable, item.text, onClick = {})
                }
            }

            SootheBottomNavigation(modifier = Modifier.align(Alignment.BottomCenter))
        }
    }

    @Preview(showBackground = true, widthDp = 320, heightDp = 320)
    @Composable
    fun OnboardingPreview() {
        MySootheTheme {
            OnboardingScreen(onContinueClicked = {})
        }
    }

    @Composable
    private fun Greeting(name: String) {

        var expanded by remember { mutableStateOf(false) }

        val extraPadding by animateDpAsState(
            if (expanded) 48.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
            Row(modifier = Modifier.padding(24.dp)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = extraPadding.coerceAtLeast(0.dp))
                ) {
                    Text(text = "Hello, ")
                    Text(text = name)
                }


            }
        }
    }

    @Composable
    private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
        BottomNavigation(
            backgroundColor = MaterialTheme.colors.background,
            modifier = modifier.height(64.dp)
        ) {
            BottomNavigationItem(
                icon = {
                    IconButton(onClick = {
                        val intent = Intent(this@sideScreen, homeScreen::class.java)
                        startActivity(intent)
                        textToSpeech.speak("Home", TextToSpeech.QUEUE_FLUSH, null, null)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Spa,
                            modifier = Modifier.padding(top = 5.dp),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                label = {
                    Text(
                        stringResource(R.string.bottom_navigation_home),
                        color = Color.Black // Set the text color to black
                    )
                },
                selected = true,
                onClick = {}

            )

            BottomNavigationItem(
                icon = {
                    IconButton(onClick = {
                        textToSpeech.speak("Camera", TextToSpeech.QUEUE_FLUSH, null, null)
                        val intent = Intent(this@sideScreen, homeScreen::class.java)
                        startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Camera,
                            modifier = Modifier.padding(top = 8.dp),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                label = {
                    Text(
                        stringResource(R.string.maincamera_home),
                        color = Color.Black // Set the text color to black
                    )
                },
                selected = true,
                onClick = {}
            )
            BottomNavigationItem(
                icon = {
                    IconButton(onClick = {
                        val intent = Intent(this@sideScreen, sideScreen::class.java)
                        startActivity(intent)
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            modifier = Modifier.padding(top = 9.dp),
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                },
                label = {
                    Text(
                        stringResource(R.string.bottom_navigation_profile),
                        color = Color.Black // Set the text color to black
                    )
                },
                selected = false,
                onClick = {}
            )
        }
    }

    @Composable
    fun FunnyEvenBetterAlignYourBodyElement(
        @DrawableRes drawable: Int,
        @StringRes text: Int,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        val context = LocalContext.current

        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color.White,
            modifier = modifier.clickable {
                onClick()
                val spokenText = context.getString(text)
                textToSpeech.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        ) {
            Column {
                Box(modifier = Modifier.fillMaxWidth()) {
                    Image(
                        painter = painterResource(drawable),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(

                        text = context.getString(text), // Retrieve the text from resources using context.getString()
                        style = MaterialTheme.typography.h4,
                        color = Color.Black,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }

    private val alignYourBodyData = listOf(
        R.drawable.image1 to R.string.affirmation1,
        R.drawable.image2 to R.string.affirmation2,
        R.drawable.image3 to R.string.affirmation3,
        R.drawable.image4 to R.string.affirmation4,
        R.drawable.image5 to R.string.affirmation5,
        R.drawable.image6 to R.string.affirmation6,
        R.drawable.image7 to R.string.affirmation7,
        R.drawable.image8 to R.string.affirmation8,
        R.drawable.image9 to R.string.affirmation9,
        R.drawable.image10 to R.string.affirmation10
    ).map { DrawableStringPair(it.first, it.second) }

    private data class DrawableStringPair(
        @DrawableRes val drawable: Int,
        @StringRes val text: Int
    )


    @Composable
    fun ElevatedButton(onClick: () -> Unit, function: () -> Unit) {
        TODO("Not yet implemented")
    }

    @Preview(
        showBackground = true,
        widthDp = 320,
        uiMode = Configuration.UI_MODE_NIGHT_YES,
        name = "Dark"
    )
    @Preview(showBackground = true, widthDp = 320)
    @Composable
    fun DefaultPreview() {
        MySootheTheme {
            Greetings()
        }
    }
}