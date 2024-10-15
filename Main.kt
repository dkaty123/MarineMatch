package com.codelab.basiclayouts

import com.codelab.basiclayouts.data.Item
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codelab.basiclayouts.ui.theme.MySootheTheme

class Main : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main)

        // Initialize the RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerView)
        adapter = MyAdapter(getItemList())

        // Set the adapter and layout manager to the RecyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun getItemList(): List<Item> {
        // Create a list of items with images and text
        val itemList = ArrayList<Item>()
        itemList.add(Item(R.drawable.image1, "Text 1"))
        itemList.add(Item(R.drawable.image2, "Text 2"))
        itemList.add(Item(R.drawable.image3, "Text 3"))
        // Add more items as needed

        return itemList
    }



}