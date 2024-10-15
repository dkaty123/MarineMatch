package com.codelab.basiclayouts

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException

class MainActivity56 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main5)

        val username = findViewById<TextView>(R.id.username)
        val password = findViewById<TextView>(R.id.password)

        val loginbtn = findViewById<Button>(R.id.loginbtn)
        val sighinbtn = findViewById<Button>(R.id.sighUp)

        loginbtn.setOnClickListener {
            Thread {
                val client = OkHttpClient()
                val json = "{\"username\":\"" + username.text.toString() + "\",\"password\":\"" + password.text.toString() + "\"}"
                val url = "http://10.0.2.2:9000/auth/create/token"

                val body = RequestBody.create("application/json".toMediaTypeOrNull(), json)

                val request = Request.Builder()
                    .url(url)
                    .post(body)
                    .build()
                val call: Call = client.newCall(request)
                try {
                    val response: Response = call.execute()
                    requireNotNull(response.body) { "Response body is null" }
                    val token: String = response.body!!.string()
                    println(response.code.toString() + "\n" + token)
                    if (response.code == 200) {
                        try {
                            Thread {
                                //val i = Intent(MainActivity.this, .class)
                                //startActivity(i)
                                println("200 ok")
                            }.start()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                } catch (e: IOException) {
                    throw RuntimeException(e)
                }
            }.start()
        }

        sighinbtn.setOnClickListener {
            val i = Intent(this@MainActivity56, MainActivity6::class.java)
            startActivity(i)
        }

        loginbtn.setOnClickListener {
            val i = Intent(this@MainActivity56, homeScreen::class.java)
            startActivity(i)
        }
    }
}