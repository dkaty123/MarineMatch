package com.codelab.basiclayouts

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class RecentImages : AppCompatActivity() {

    private lateinit var imageView1: ImageView
    private lateinit var imageView2: ImageView
    private lateinit var imageView3: ImageView
    private lateinit var imageView4: ImageView
    private lateinit var imageView5: ImageView
    private lateinit var imageView6: ImageView
    private lateinit var imageView7: ImageView
    private lateinit var imageView8: ImageView
    private lateinit var imageView9: ImageView
    private lateinit var imageView10: ImageView

    private lateinit var textView1: TextView
    private lateinit var textView2: TextView
    private lateinit var textView3: TextView
    private lateinit var textView4: TextView
    private lateinit var textView5: TextView
    private lateinit var textView6: TextView
    private lateinit var textView7: TextView
    private lateinit var textView8: TextView
    private lateinit var textView9: TextView
    private lateinit var textView10: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.recent_images) // Replace "your_layout" with the actual layout file name



        val imageHelperActivity = MLImageHelperActivity()



        var bits: List<Bitmap> = imageHelperActivity.bits
        // Initialize ImageViews
        imageView1 = findViewById(R.id.imageView1)
        imageView2 = findViewById(R.id.imageView2)
        imageView3 = findViewById(R.id.imageView3)
        imageView4 = findViewById(R.id.imageView4)
        imageView5 = findViewById(R.id.imageView5)
        imageView6 = findViewById(R.id.imageView6)
        imageView7 = findViewById(R.id.imageView7)
        imageView8 = findViewById(R.id.imageView8)
        imageView9 = findViewById(R.id.imageView9)
        imageView10 = findViewById(R.id.imageView10)

        // Initialize TextViews
        textView1 = findViewById(R.id.textView1)
        textView2 = findViewById(R.id.textView2)
        textView3 = findViewById(R.id.textView3)
        textView4 = findViewById(R.id.textView4)
        textView5 = findViewById(R.id.textView5)
        textView6 = findViewById(R.id.textView6)
        textView7 = findViewById(R.id.textView7)
        textView8 = findViewById(R.id.textView8)
        textView9 = findViewById(R.id.textView9)
        textView10 = findViewById(R.id.textView10)

        if (bits == null){
            Log.d("null", bits.toString())
        }

        for (capturedImage in bits) {
            Log.d("CapturedImage", capturedImage.toString())
        }

        if (bits.size > 9) {
            val lastIndex = bits.size - 1
            imageView1.setImageBitmap(bits[lastIndex - 9])
            imageView2.setImageBitmap(bits[lastIndex - 8])
            imageView3.setImageBitmap(bits[lastIndex - 7])
            imageView4.setImageBitmap(bits[lastIndex - 6])
            imageView5.setImageBitmap(bits[lastIndex - 5])
            imageView6.setImageBitmap(bits[lastIndex - 4])
            imageView7.setImageBitmap(bits[lastIndex - 3])
            imageView8.setImageBitmap(bits[lastIndex - 2])
            imageView9.setImageBitmap(bits[lastIndex - 1])
            imageView10.setImageBitmap(bits[lastIndex])
        }


        // Set image and text for each ImageView and TextView


        textView1.text = "Text 1"
        textView2.text = "Text 2"
        textView3.text = "Text 3"
        textView4.text = "Text 4"
        textView5.text = "Text 5"
        textView6.text = "Text 6"
        textView7.text = "Text 7"
        textView8.text = "Text 8"
        textView9.text = "Text 9"
        textView10.text = "Text 10"
    }
}
