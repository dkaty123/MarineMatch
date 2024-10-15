package com.codelab.basiclayouts;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;

import java.io.IOException;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity5 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        TextView topSign = findViewById(R.id.signin);

        Button loginbtn = findViewById(R.id.loginbtn);

        Button sighinbtn = findViewById(R.id.sighUp);

        ImageView logo = findViewById(R.id.globalVisionLogo);



        textToSpeech = new TextToSpeech(this, this);

        //admin and admin

        loginbtn.setOnClickListener(view -> {
            new Thread(() -> {
                OkHttpClient client = new OkHttpClient();
                String json = "{\"username\":\"" + username.getText().toString() + "\",\"password\":\"" + password.getText().toString() + "\"}";
                String url = "http://10.0.2.2:9000/auth/create/token";

                RequestBody body = RequestBody.create(
                        MediaType.parse("application/json"), json);

                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();
                Call call = client.newCall(request);
                try {
                    Response response = call.execute();
                    assert response.body() != null;
                    String token = response.body().string();
                    System.out.println(response.code() + "\n" + token);
                    if (response.code() == 200) {
                        try {
                            new Thread(() ->{
                                speak("Login successful."); // Speak the message

                                System.out.println("200 ok");
                            }).start();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        });

        sighinbtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity5.this, MainActivity6.class);
            startActivity(i);
            speak("To Sign Up");
        });

        loginbtn.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity5.this, homeScreen.class);
         startActivity(i);
          speak("Log In");
       });


        View.OnTouchListener touchListener = (v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                speak(v.getContentDescription().toString());
            }
            return false;
        };

        username.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                String currentUsername = username.getText().toString();
                speak("Username field: " + currentUsername);
            }
            return false;
        });

        password.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                String currentPassword = password.getText().toString();
                speak("Password field: " + currentPassword);
            }
            return false;
        });
        topSign.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                speak("Log In");
            }
            return false;
        });
        logo.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                speak("Global Vision");
            }
            return false;
        });

    }


    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.getDefault());

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                System.out.println("Language not supported");
            }
        } else {
            System.out.println("Initialization failed");
        }
    }

    private void speak(String message) {
        textToSpeech.speak(message, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}