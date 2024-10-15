package com.codelab.basiclayouts

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class stats : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        layout.gravity = Gravity.CENTER
        layout.setBackgroundColor(Color.parseColor("#F5F5F5"))
        layout.setPadding(32, 32, 32, 32)
        setContentView(layout)

        val titleTextView = TextView(this)
        titleTextView.text = "App Statistics"
        titleTextView.textSize = 24f
        titleTextView.setTextColor(Color.WHITE)
        titleTextView.setBackgroundColor(Color.parseColor("#FF4081"))
        titleTextView.setPadding(16, 12, 16, 12)
        layout.addView(titleTextView)

        createStatCard("Active Users", "1,200", "#64B5F6", layout)
        createStatCard("Downloads", "400", "#FFA726", layout)
        createStatCard("Revenue", "$15,000", "#66BB6A", layout)
    }

    private fun createStatCard(label: String, value: String, color: String, parentLayout: LinearLayout) {
        val cardLayout = LinearLayout(this)
        cardLayout.orientation = LinearLayout.HORIZONTAL
        cardLayout.gravity = Gravity.CENTER
        cardLayout.setPadding(16, 16, 16, 16)
        val layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)


        cardLayout.setBackgroundColor(Color.parseColor("#FFFFFF"))
        parentLayout.addView(cardLayout)

        val labelTextView = TextView(this)
        labelTextView.text = label
        labelTextView.textSize = 18f
        labelTextView.setTextColor(Color.BLACK)
        labelTextView.setBackgroundColor(Color.parseColor(color))
        labelTextView.setPadding(16, 12, 16, 12)
        val labelLayoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT)

        cardLayout.addView(labelTextView, labelLayoutParams)

        val valueTextView = TextView(this)
        valueTextView.text = value
        valueTextView.textSize = 18f
        valueTextView.setTextColor(Color.WHITE)
        valueTextView.setBackgroundColor(Color.parseColor(color))
        valueTextView.setPadding(16, 12, 16, 12)
        val valueLayoutParams = LayoutParams(0, LayoutParams.WRAP_CONTENT)

        cardLayout.addView(valueTextView, valueLayoutParams)
    }
}