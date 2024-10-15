package com.codelab.basiclayouts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codelab.basiclayouts.data.FAQ
class FAQActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_faq)

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val faqList = getFAQList()
        val adapter = FAQAdapter(faqList) { faqItem ->
            // Handle item click here
        }
        recyclerView.adapter = adapter
    }

    private fun getFAQList(): List<FAQ> {
        val faqList = mutableListOf<FAQ>()
        faqList.add(FAQ("Question 1", "Answer 1"))
        faqList.add(FAQ("Question 2", "Answer 2"))
        faqList.add(FAQ("Question 3", "Answer 3"))
        return faqList
    }
}