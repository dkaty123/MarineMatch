package com.codelab.basiclayouts


import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap

import android.graphics.drawable.Drawable
import android.media.AudioManager
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.ManageSearch
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Spa
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.outlined.BikeScooter
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import com.codelab.basiclayouts.ui.theme.LightBlue
import com.codelab.basiclayouts.ui.theme.MySootheTheme
import com.codelab.basiclayouts.ui.theme.stats
import com.google.mlkit.vision.common.InputImage

import java.util.Locale



class homeScreen : AppCompatActivity() {

    private lateinit var textToSpeech: TextToSpeech
    private lateinit var myButton: Button
    private lateinit var myTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MySootheApp()





            // Alternatively, set a background resource
            // rootLayout.setBackgroundResource(R.drawable.background_image)




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

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown() // Shutdown the TextToSpeech engine to release resources
    }




    @Composable
    fun MyTopBar(onClick: () -> Unit) {
        MenuBar()
        TopAppBar(
            title = {
                ClickableText(
                    text = AnnotatedString("Global Vision"),
                    onClick = { offset ->
                        onClick()
                        val text = "Global Vision"
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Spa,
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    val intent = Intent(this@homeScreen, MenuTop::class.java)
                    startActivity(intent)
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Localized description"
                    )
                }
            }
        )
    }

    @Composable
    fun TipCard(
        onClick: () -> Unit,
        tipTitle: String,
        @DrawableRes imageResourceId: Int,
        tipDescription: String,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .clickable {
                    onClick()
                    val tempString = tipTitle + tipDescription
                    textToSpeech.speak(tempString, TextToSpeech.QUEUE_FLUSH, null, null)
                }
                .background(Color.White, MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust the padding as per your requirement
        ) {
            Column(modifier = Modifier.padding(bottom = 8.dp).fillMaxHeight(0.8f)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = LightBlue)
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust the padding as per your requirement
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = tipTitle,
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )
                        Image(
                            painter = painterResource(id = imageResourceId),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(74.dp)
                                .padding(start = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp)) // Add space between the blue box and the description
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .height(80.dp)
                        .fillMaxWidth()
                        .background(color = Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust the padding as per your requirement
                ) {
                    Text(
                        text = tipDescription,
                        style = MaterialTheme.typography.h4,
                      //  style = MaterialTheme.typography.body1,
                        color = Color.Black
                    )
                }
            }
        }
    }

    @Composable
    fun statCard(
        onClick: () -> Unit,
        tipTitle: String,
        @DrawableRes imageResourceId: Int,
        tipDescription: String,
        modifier: Modifier = Modifier
    ) {
        Box(
            modifier = modifier
                .clickable {
                    onClick()
                    val tempString = tipTitle + tipDescription
                    textToSpeech.speak(tempString, TextToSpeech.QUEUE_FLUSH, null, null)
                }
                .background(Color.White, MaterialTheme.shapes.medium)
                .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust the padding as per your requirement
        ) {
            Column(modifier = Modifier.padding(bottom = 8.dp)) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // Decrease the width to 80% of the available width
                        .background(color = stats)
                        .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust the padding as per your requirement
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
                    ) {
                        Text(
                            text = tipTitle,
                            style = MaterialTheme.typography.h6,
                            color = Color.Black
                        )
                        Image(
                            painter = painterResource(id = imageResourceId),
                            contentDescription = "Image",
                            modifier = Modifier
                                .size(74.dp)
                                .padding(start = 8.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp)) // Add space between the blue box and the description
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // Decrease the width to 80% of the available width
                        .background(color = Color.White)
                        .padding(horizontal = 16.dp, vertical = 2.dp) // Adjust the padding as per your requirement
                ) {
                    Text(
                        text = tipDescription,
                        style = MaterialTheme.typography.h4,
                       // style = MaterialTheme.typography.body1,
                        color = Color.Black
                    )
                }
            }
        }
    }

    @Composable
    fun TipCardList(
        modifier: Modifier = Modifier
    ) {
        val tipData = listOf(
            TipCardData(
                title = "Tip : Backtracking",
                imageResourceId = R.drawable.swiping,
                description = "To return to the previous screen, simply swipe from the left side"
            ),
            TipCardData(
                title = "Tip : Camera",
                imageResourceId = R.drawable.perm,
                description = "Ensure your device has the proper permissions enabled to use the camera"
            )
        )
        LazyRow(modifier = modifier) {
            items(tipData) { tip ->
                TipCard(
                    onClick = {},
                    tipTitle = tip.title,
                    imageResourceId = tip.imageResourceId,
                    tipDescription = tip.description,
                    modifier = Modifier.width(350.dp) // Adjust the width as per your requirement
                )
                Spacer(modifier = Modifier.width(8.dp)) // Add space between TipCards
            }
        }
    }

    data class TipCardData(
        val title: String,
        @DrawableRes val imageResourceId: Int,
        val description: String
    )

    @Composable
    fun betterBar(modifier: Modifier = Modifier, onClick: () -> Unit) {
        var expanded by remember { mutableStateOf(false) }
        val dropdownItems = listOf("FAQs")

        TopAppBar(
            title = {
                ClickableText(
                    text = AnnotatedString("Global Vision"),
                    onClick = { offset ->
                        onClick()
                        val text = "Global Vision"
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)

                    },
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis ,
                    style = TextStyle(
                        color = Color.White,
                    fontSize = 20.sp
                 )
                )
            },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Visibility,
                        contentDescription = "Localized description"
                    )
                }
            },
            actions = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = {
                        expanded = !expanded
                        textToSpeech.speak("Menu Bar", TextToSpeech.QUEUE_FLUSH, null, null)
                    }) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                    if (expanded) {
                        DropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            dropdownItems.forEach { item ->
                                DropdownMenuItem(onClick = { onItemClick(item)
                                    expanded = false }) {
                                    Text(
                                        text = item,
                                        style = TextStyle(fontWeight = FontWeight.Bold)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }

    private fun onItemClick(item: String) {
        if (item == "FAQs"){
            val intent = Intent(this@homeScreen, hello::class.java)
            startActivity(intent)
            textToSpeech.speak("F A Qs", TextToSpeech.QUEUE_FLUSH, null, null)
        }

    }


    // Step: Search bar - Modifiers
    @Composable
    fun SearchBar(
        modifier: Modifier = Modifier
    ) {
        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = MaterialTheme.colors.surface
            ),
            placeholder = {
                Text(stringResource(R.string.placeholder_search))
            },
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp)
        )
    }

    val roundedCornerShape = RoundedCornerShape(8.dp)
    // Step: Align your body - Alignment
    @Composable
    fun AlignYourBodyElement(
        @DrawableRes drawable: Int,
        @StringRes text: Int,
        modifier: Modifier = Modifier
    ) {
        Surface(
            shape = roundedCornerShape,
            color = Color.White, // Set a contrasting background color for the card
            modifier = modifier
        ) {

            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(drawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(88.dp)
                        .clip(CircleShape)
                )
                Text(
                    text = stringResource(text),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.paddingFromBaseline(
                        top = 24.dp, bottom = 8.dp
                    )
                )
            }
        }
    }

    @Composable
    fun EvenBetterAlignYourBodyElement(
        @DrawableRes drawable: Int,
        @StringRes text: Int,
        modifier: Modifier = Modifier
    ) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color.White, // Set a contrasting background color for the card
            modifier = modifier
        ) {
            Column {
                Box(
                    modifier = Modifier.fillMaxWidth()
                ) {
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
                        text = stringResource(text),
                        style = MaterialTheme.typography.h4,
                        color = Color.Black,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp)
                    )
                }
            }
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
                //val spokenText = context.getString(text)
                textToSpeech.speak("RECENT", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        ) {
            Column(modifier = Modifier.clickable {
                onClick()
                val spokenText = context.getString(text)
                textToSpeech.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null, null) }
            ) {
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


    // Step: Favorite collection card - Material Surface
    @Composable
    fun FavoriteCollectionCard(
        @DrawableRes drawable: Int,
        @StringRes text: Int,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {

        val context = LocalContext.current

        Surface(
            shape = MaterialTheme.shapes.small,
            modifier = modifier.clickable {
                onClick()
                val spokenText = context.getString(text)
                textToSpeech.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null, null)
            }
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.width(250.dp)
            ) {
                Image(
                    painter = painterResource(drawable),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(100.dp)
                )
                Text(
                    text = stringResource(text),
                    style = MaterialTheme.typography.h3,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    color = Color.Black,
                )
            }
        }
    }

    // 56 for image and 192 for row

    // Step: Align your body row - Arrangements
    @Composable
    fun BetterAlignYourBodyRow(
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier.clickable {
                onClick()
               // val spokenText = context.getString(text)
                textToSpeech.speak("Recent", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        ) {
            items(alignYourBodyData) { item ->
                FunnyEvenBetterAlignYourBodyElement(item.drawable, item.text, onClick = {})
            }
        }
    }

    // Step: Favorite collections grid - LazyGrid
    @Composable
    fun FavoriteCollectionsGrid(
        modifier: Modifier = Modifier
    ) {
        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier.height(120.dp)
        ) {
            items(favoriteCollectionsData) { item ->
                FavoriteCollectionCard(item.drawable, item.text, Modifier.height(56.dp), onClick = {})
            }
        }
    }

    // Step: Home section - Slot APIs
    @Composable
    fun HomeSection(
        @StringRes title: Int,
        modifier: Modifier = Modifier,
        onClick: () -> Unit,
        content: @Composable () -> Unit
    ) {
        Column(modifier = modifier.clickable { onClick() }) {
            Text(
                text = stringResource(title).uppercase(Locale.getDefault()),
                color = Color.Black,
                style = MaterialTheme.typography.h4,
                modifier = Modifier
                    .paddingFromBaseline(top = 40.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp)
            )
            content()
        }
    }

    // Step: Home screen - Scrolling


    @Composable
    fun HomeScreen(modifier: Modifier = Modifier) {
        Column(
            modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                //.background(Color.Red)
        ) {
            Spacer(Modifier.height(16.dp))
            betterBar(Modifier.padding(horizontal = 10.dp), onClick = {})
            Spacer(Modifier.height(8.dp))
            SearchBar(Modifier.padding(horizontal = 16.dp))
            HomeSection(title = R.string.align_your_body, onClick = {textToSpeech.speak("Recent", TextToSpeech.QUEUE_FLUSH, null, null)}) {
            //    TestAlignYourBodyRow(onClick = {})
                AlignYourBodyRow(onClick = {})

            }
            HomeSection(title = R.string.favorite_collections,  onClick = {textToSpeech.speak("Favorite Collections", TextToSpeech.QUEUE_FLUSH, null, null)}) {
                FavoriteCollectionsGrid()
            }
            //HomeSection(title = R.string.bottomOther,  onClick = {textToSpeech.speak("Other Info", TextToSpeech.QUEUE_FLUSH, null, null)}) {
            //    TipCardList(modifier = modifier)}
            HomeSection(title = R.string.bottomOther,  onClick = {textToSpeech.speak("Other Info", TextToSpeech.QUEUE_FLUSH, null, null)}) {
                TipCardList(Modifier.padding(4.dp))
                //TipCard(
                //    onClick = {},
                 //   tipTitle = "Tip : Backtracking",
               //     R.drawable.swiping,
                //    tipDescription = "To return to the previous screen, simply swipe from the left side",
                //   modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
              //  )
              //  TipCard(
               //     onClick ={},
                //    tipTitle = "Tip : Camera",
                //    R.drawable.perm,
                 //   tipDescription = "Ensure your device has the proper permissions enabled to use the camera",
                 //   modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
              //  )
            }
            HomeSection(title = R.string.staty,  onClick = {textToSpeech.speak("Stats", TextToSpeech.QUEUE_FLUSH, null, null)}) {
                statCard(
                    onClick = {},
                    tipTitle = "Your Stats : ",
                    R.drawable.stat,
                    tipDescription = "Pictures Taken: " + getImageCounter() + "\nTotal Usage Time : 5 Hours \nDate Joined : June 2, 2023 \nConsecutive Daily Log Ins : 5 Days",
                    modifier = Modifier.padding(4.dp)
                )

            }


            // Existing code...

            // Tip card

            Spacer(Modifier.height(16.dp))
        }
    }

    private fun getImageCounter(): Int {

        val Image = ImageClassificationActivity()

        val imageCount = Image.getImageCounter()// Your logic to retrieve the image count

        return imageCount
    }

    // Step: Bottom navigation - Material
    @Composable
    private fun SootheBottomNavigation(modifier: Modifier = Modifier) {
        BottomNavigation(
            backgroundColor = Color.White,
            modifier = modifier.height(64.dp)
        ) {
            BottomNavigationItem(
                icon = {
                    IconButton(onClick = {
                        val text = "Home"
                        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null)
                        val intent = Intent(this@homeScreen, homeScreen::class.java)
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
                        val intent = Intent(this@homeScreen, ImageClassificationActivity::class.java)
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
                        val intent = Intent(this@homeScreen, sideScreen::class.java)
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

    // Step: MySoothe App - Scaffold
    @Composable
    fun MySootheApp() {
        MySootheTheme {
            Scaffold(
                bottomBar = { SootheBottomNavigation() }
            ) { padding ->
                HomeScreen(Modifier.padding(padding))
            }

        }
        val rootLayout = window.decorView.rootView
        rootLayout.background = ContextCompat.getDrawable(this, R.drawable.lol6)
    }

    @Composable
    fun MenuBar() {
        var expanded by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.TopStart)
        ) {
            IconButton(onClick = { expanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Localized description")
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(onClick = { /* Handle edit! */ }) {
                    Text("Edit")
                    Icon(Icons.Default.Help, contentDescription = null)
                }
                DropdownMenuItem(onClick = { /* Handle settings! */ }) {
                    Text("Settings")
                    Icon(Icons.Outlined.Delete, contentDescription = null)
                }
                Divider()
                DropdownMenuItem(onClick = { /* Handle send feedback! */ }) {
                    Text("Send Feedback")
                    Icon(Icons.Outlined.BikeScooter, contentDescription = null)
                }
            }
        }
    }



    private val alignYourBodyData = mutableListOf(
        Pair(R.drawable.homeimage1, R.string.ab1_inversions),
        Pair(R.drawable.lol2, R.string.ab2_quick_yoga),
        Pair(R.drawable.lol3, R.string.ab3_stretching),
        Pair(R.drawable.lol4, R.string.ab4_tabata)
    ).map { DrawableStringPair(it.first, it.second) }



    private val favoriteCollectionsData = listOf(
        R.drawable.fc1_short_mantras to R.string.fc1_short_mantras,
        R.drawable.fc2_nature_meditations to R.string.fc2_nature_meditations,
        R.drawable.fc3_stress_and_anxiety to R.string.fc3_stress_and_anxiety,
        R.drawable.fc4_self_massage to R.string.fc4_self_massage,
        R.drawable.fc5_overwhelmed to R.string.fc5_overwhelmed,
        R.drawable.fc6_nightly_wind_down to R.string.fc6_nightly_wind_down
    ).map { DrawableStringPair(it.first, it.second) }





    // 56 for image and 192 for row

    // Step: Align your body row - Arrangements
    @Composable
    fun AlignYourBodyRow(
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = modifier.clickable {
                onClick()
                // val spokenText = context.getString(text)
                textToSpeech.speak("Recent", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        ) {
            items(alignYourBodyData) { item ->
                FunnyEvenBetterAlignYourBodyElement(item.drawable, item.text, onClick = {})
            }
        }

    }




// Use the recentImageDrawable wherever you would use R.drawable.recentimage1







//    @Composable
//    fun TestAlignYourBodyRow(
   //     modifier: Modifier = Modifier,
   //     onClick: () -> Unit
 //   ) {

   //     LazyRow(
    //        horizontalArrangement = Arrangement.spacedBy(8.dp),
    //        contentPadding = PaddingValues(horizontal = 16.dp),
     //       modifier = modifier.clickable {
      //          onClick()
      //          // val spokenText = context.getString(text)
      //          textToSpeech.speak("Recent", TextToSpeech.QUEUE_FLUSH, null, null)
      //      }
    //    ) {
     //       items(bits) { item ->
      //          BitTestFunnyEvenBetterAlignYourBodyElement(bitmap = item,  onClick = {})
     //       }
     //   }
 //   }

    @Composable
    fun DisplayImage(imageBitmap: ImageBitmap) {
        Image(
            bitmap = imageBitmap,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
    }




    @Composable
    fun BitTestFunnyEvenBetterAlignYourBodyElement(
        bitmap: Bitmap,
        modifier: Modifier = Modifier,
        onClick: () -> Unit
    ) {
        val context = LocalContext.current

        Surface(
            shape = MaterialTheme.shapes.medium,
            color = Color.White,
            modifier = modifier.clickable {
                onClick()
                textToSpeech.speak("RECENT", TextToSpeech.QUEUE_FLUSH, null, null)
            }
        ) {
            Column(modifier = Modifier.clickable {
                onClick()
                textToSpeech.speak("Hello", TextToSpeech.QUEUE_FLUSH, null, null)
            }) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AndroidView(
                        factory = { context ->
                            ImageView(context).apply {
                                scaleType = ImageView.ScaleType.CENTER_CROP
                                setImageBitmap(bitmap)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                }
                Box(modifier = Modifier.fillMaxWidth()) {
                    val imageBitmap: ImageBitmap = bitmap.asImageBitmap()
                    DisplayImage(imageBitmap)
                }

                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = context.getString(R.string.maincamera_home),
                        style = MaterialTheme.typography.h4,
                        color = Color.Black,
                        modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp)
                    )
                }
            }
        }
    }











    // Convert the capturedImageDrawable to a resource identifier



    // Create a new DrawableStringPair with the capturedImageResourceId and a string resource


// Add the capturedImagePair to the alignYourBodyData list



    // Image Code :




    data class DrawableStringPair(
        @DrawableRes val drawable: Int,
        @StringRes val text: Int
    )

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun SearchBarPreview() {
        MySootheTheme { SearchBar(Modifier.padding(8.dp)) }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun AlignYourBodyElementPreview() {
        MySootheTheme {
            AlignYourBodyElement(
                text = R.string.ab1_inversions,
                drawable = R.drawable.homeimage1,
                modifier = Modifier.padding(8.dp)
            )
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun FavoriteCollectionCardPreview() {
        MySootheTheme {
            FavoriteCollectionCard(
                text = R.string.fc2_nature_meditations,
                drawable = R.drawable.fc2_nature_meditations,
                modifier = Modifier.padding(8.dp),
                onClick = {}
            )
        }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun FavoriteCollectionsGridPreview() {
        MySootheTheme { FavoriteCollectionsGrid() }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun AlignYourBodyRowPreview() {
        MySootheTheme { AlignYourBodyRow(onClick = {}) }
    }

    //@Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
 //   @Composable
  //  fun HomeSectionPreview() {
   //     MySootheTheme {
         //   HomeSection(R.string.align_your_body, onClick{}) {
         //       AlignYourBodyRow()
         //   }
      //  }
   // }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2, heightDp = 180)
    @Composable
    fun ScreenContentPreview() {
        MySootheTheme { HomeScreen() }
    }

    @Preview(showBackground = true, backgroundColor = 0xFFF0EAE2)
    @Composable
    fun BottomNavigationPreview() {
        MySootheTheme { SootheBottomNavigation(Modifier.padding(top = 24.dp)) }
    }

    @Preview(widthDp = 360, heightDp = 640)
    @Composable
    fun MySoothePreview() {
        MySootheApp()
    }
}