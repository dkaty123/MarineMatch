package com.codelab.basiclayouts;

import android.annotation.SuppressLint;
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

public class MainActivity6 extends AppCompatActivity implements TextToSpeech.OnInitListener {

    private TextToSpeech textToSpeech;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);

        Button signinbtn = findViewById(R.id.sighupbtn);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button backToSignUp = findViewById(R.id.backToSignIn);

        TextView username = findViewById(R.id.username);
        TextView password = findViewById(R.id.password);
        TextView email = findViewById(R.id.email);
        TextView firstName = findViewById(R.id.firstName);
        TextView lastName = findViewById(R.id.lastName);


        TextView topSign = findViewById(R.id.signup);

        textToSpeech = new TextToSpeech(this, this);

        signinbtn.setOnClickListener(view -> new Thread(() -> {
            OkHttpClient client = new OkHttpClient();

            //build the json for the http request
            String json = "{\"username\":\"" + username.getText().toString() + "\",\"email\":\"" + email.getText().toString() + "\",\"first_name\":\"" + firstName.getText().toString() + "\",\"last_name\":\"" + lastName.getText().toString() + "\",\"password\":\"" + password.getText().toString() +"\" }";
            String url = "http://10.0.2.2:9000/auth/create/user";

            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"),json);

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Call call = client.newCall(request);
            try {
                Response response = call.execute();
                System.out.println(response);
                speak("User created successfully");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).start());

        backToSignUp.setOnClickListener(view -> {
            Intent i = new Intent(MainActivity6.this, MainActivity5.class);
            startActivity(i);
            speak("Back to Log In");
        });

        signinbtn.setOnClickListener(view -> {
            speak("You have Signed Up for Global Vision!");
        });

        // ONtouchListener
        View.OnTouchListener touchListener = (v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                speak(v.getContentDescription().toString());
            }
            return false;
        };
// top title
        topSign.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                speak("Sign Up");
            }
            return false;
        });

        // Rest of info boxes
        username.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                String currentUsername = username.getText().toString();
                speak("Username field: " + currentUsername);
            }
            return false;
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    String fieldValue = password.getText().toString();
                    String message = "Password field. Current value: " + fieldValue;
                    speak(message);
                }
                return false;
            }
        });

// Email field
        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    String fieldValue = email.getText().toString();
                    String message = "Email field. Current value: " + fieldValue;
                    speak(message);
                }
                return false;
            }
        });

// First name field
        firstName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    String fieldValue = firstName.getText().toString();
                    String message = "First name field. Current value: " + fieldValue;
                    speak(message);
                }
                return false;
            }
        });

// Last name field
        lastName.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    String fieldValue = lastName.getText().toString();
                    String message = "Last name field. Current value: " + fieldValue;
                    speak(message);
                }
                return false;
            }
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