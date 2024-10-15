package com.codelab.basiclayouts

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable


class TestListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            makeList()
        }
    }
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun makeList() {
    Column {
        ListItem(
            text = { Text("Question 1") },
            secondaryText = { Text("Answer 1") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Localized description"
                )
            },
            trailing = { Text("meta") }
        )
        Divider()
        ListItem(
            text = { Text("Question 2") },
            secondaryText = { Text("Answer 2") },
            icon = {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Localized description"
                )
            },
            trailing = { Text("meta") }
        )
        Divider()
        // Add more ListItems with FAQ questions and answers as needed
    }
}