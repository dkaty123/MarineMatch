package com.codelab.basiclayouts

import android.content.Context
import android.content.Intent
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.codelab.basiclayouts.data.dogs
import com.codelab.basiclayouts.data.Dog
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import androidx.compose.ui.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Spa
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.codelab.basiclayouts.ui.theme.taupe100
import java.util.Locale



private lateinit var textToSpeech: TextToSpeech

class hello : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            MySootheTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    BetterWoofApp()
                }
            }

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


    @Composable
    private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
        BottomNavigation(
            backgroundColor = taupe100,
            modifier = modifier.height(64.dp)
        ) {
            BottomNavigationItem(
                icon = {
                    IconButton(onClick = {
                        val text = "Home"
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                        val intent = Intent(this@hello, homeScreen::class.java)
                        startActivity(intent)
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
                        val text = "Camera"
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                        val intent = Intent(this@hello, ImageClassificationActivity::class.java)
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
                        val text = "Profile"
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                        val intent = Intent(this@hello, sideScreen::class.java)
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


    /**
     * Composable that displays an app bar and a list of dogs.
     */
    @Composable
    fun WoofApp() {
        Scaffold(
            topBar = {
                WoofTopAppBar()
            }
        ) { it ->
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color.Yellow, // Set the desired background color here
                contentColor = Color.Black, // Set the desired content color here
                content = {
                    LazyColumn(contentPadding = it) {
                        items(dogs) {
                            DogItem(
                                dog = it,
                                context = LocalContext.current,
                                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))
                            )
                        }
                    }
                }
            )
        }
    }

    @Composable
    fun BetterWoofApp() {

        Scaffold(
            topBar = {
                WoofTopAppBar()
            },
            bottomBar = { SootheBottomNavigation() }
        ) { it ->
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.hi), // Set the drawable resource here
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.FillBounds
                )

                LazyColumn(contentPadding = it) {
                    items(dogs) { dog ->
                        DogItem(
                            dog = dog,
                            context = LocalContext.current,
                            modifier = Modifier
                                .padding(dimensionResource(R.dimen.padding_small))
                        )
                    }
                }
            }
        }
    }

    /**
     * Composable that displays a list item containing a dog icon and their information.
     *
     * @param dog contains the data that populates the list item
     * @param modifier modifiers to set to this composable
     */
    @OptIn(ExperimentalMaterialApi::class)
    @Composable
    fun DogItem(
        dog: Dog,
        context: Context,
        modifier: Modifier = Modifier,
    ) {
        val context = LocalContext.current
        var expanded by remember { mutableStateOf(false) }

        Card(
            modifier = modifier
        ) {
            Column(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
                    .clickable {
                        val questionText = context.getString(dog.questions)
                        textToSpeech.speak(
                            questionText, TextToSpeech.QUEUE_FLUSH, null, null
                        )
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(dimensionResource(R.dimen.padding_small))
                ) {
                    DogIcon(dog.imageResourceId)
                    Box(modifier = Modifier.weight(1.2f)) {
                        DogInformation(dog.name, dog.questions)
                    }
                    DogItemButton(
                        expanded = expanded,
                        onClick = { expanded = !expanded },
                    )
                }
                if (expanded) {
                    val text = "Answer" + dog.age
                    textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                    DogHobby(
                        dog.age, modifier = Modifier.padding(
                            start = dimensionResource(R.dimen.padding_medium),
                            top = dimensionResource(R.dimen.padding_small),
                            bottom = dimensionResource(R.dimen.padding_medium),
                            end = dimensionResource(R.dimen.padding_medium),
                        )
                    )
                }
            }
        }
    }

    /**
     * Composable that displays a button that is clickable and displays an expand more or an expand less
     * icon.
     *
     * @param expanded represents whether the expand more or expand less icon is visible
     * @param onClick is the action that happens when the button is clicked
     * @param modifier modifiers to set to this composable
     */
    @Composable
    private fun DogItemButton(
        expanded: Boolean,
        onClick: () -> Unit,
        modifier: Modifier = Modifier
    ) {
        IconButton(
            onClick = onClick,
            modifier = modifier
        ) {
            Icon(
                imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                contentDescription = stringResource(R.string.expand_button_content_description),
                tint = Color.Black
            )
        }
    }

    @Composable
    private fun returnString(hello: Int): String {
        val text = stringResource(id = hello)
        return text
    }

    /**
     * Composable that displays a Top Bar with an icon and text.
     *
     * @param modifier modifiers to set to this composable
     */

    @Composable
    fun WoofTopAppBar(modifier: Modifier = Modifier) {
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .size(dimensionResource(R.dimen.image_size))
                            .padding(dimensionResource(R.dimen.padding_small)),
                        painter = painterResource(R.drawable.toplog),

                        // Content Description is not needed here - image is decorative, and setting a
                        // null content description allows accessibility services to skip this element
                        // during navigation.

                        contentDescription = null
                    )
                    Text(
                        text = stringResource(R.string.app_names),
                        style = MaterialTheme.typography.h1
                    )
                }
            },
            modifier = modifier
        )
    }

    /**
     * Composable that displays a photo of a dog.
     *
     * @param dogIcon is the resource ID for the image of the dog
     * @param modifier modifiers to set to this composable
     */
    @Composable
    fun DogIcon(
        @DrawableRes dogIcon: Int,
        modifier: Modifier = Modifier
    ) {
        Image(
            modifier = modifier
                .size(dimensionResource(R.dimen.image_size))
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(MaterialTheme.shapes.small),
            contentScale = ContentScale.Crop,
            painter = painterResource(dogIcon),

            // Content Description is not needed here - image is decorative, and setting a null content
            // description allows accessibility services to skip this element during navigation.

            contentDescription = null
        )
    }

    @Composable
    fun BetterDogIcon(
        dogIcon: ImageVector,
        modifier: Modifier = Modifier
    ) {
        val painter = rememberVectorPainter(dogIcon)

        Icon(
            modifier = modifier
                .size(dimensionResource(R.dimen.image_size))
                .padding(dimensionResource(R.dimen.padding_small))
                .clip(MaterialTheme.shapes.small),
            painter = painter,
            contentDescription = null
        )
    }


    /**
     * Composable that displays a dog's name and age.
     *
     * @param dogName is the resource ID for the string of the dog's name
     * @param dogAge is the Int that represents the dog's age
     * @param modifier modifiers to set to this composable
     */
    @Composable
    fun DogInformation(
        @StringRes dogName: Int,
        @StringRes dogAge: Int,
        modifier: Modifier = Modifier
    ) {
        Column(modifier = modifier) {
            Text(
                text = stringResource(dogName),
                style = MaterialTheme.typography.h1,
                modifier = Modifier.padding(top = dimensionResource(R.dimen.padding_small))
            )
            Text(
                text = stringResource(dogAge),
                style = MaterialTheme.typography.h4
            )
        }
    }

    /**
     * Composable that displays a dog's hobbies.
     *
     * @param dogHobby is the resource ID for the text string of the hobby to display
     * @param modifier modifiers to set to this composable
     */
    @Composable
    fun DogHobby(
        dogHobby: String,
        modifier: Modifier = Modifier
    ) {
        Column(
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.about),
                style = MaterialTheme.typography.h1
            )
            Text(
                text = dogHobby,
                style = MaterialTheme.typography.h4
            )
        }
    }
}