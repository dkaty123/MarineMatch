package com.codelab.basiclayouts;

import static androidx.core.graphics.drawable.DrawableKt.toBitmap;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageDecoder;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.view.MotionEvent;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.exifinterface.media.ExifInterface;

import org.tensorflow.lite.Tensor;
import org.tensorflow.lite.support.image.TensorImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class MLImageHelperActivity extends AppCompatActivity  implements TextToSpeech.OnInitListener {

    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private ImageView imageView6;
    private ImageView imageView7;
    private ImageView imageView8;
    private ImageView imageView9;
    private ImageView imageView10;

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private TextView textView7;
    private TextView textView8;
    private TextView textView9;
    private TextView textView10;

    private Button recentImages;


    private List<Bitmap> capturedImages;
    private List<String> imageNames;

    private static final String SHARED_PREF_KEY = "captured_images";



    Drawable tempDrawable;



    public Drawable capturedImageDrawable;
    private TextToSpeech textToSpeech;
    private static final int CAMERA_PERMISSION_REQUEST_CODE = 1001;
    public final String LOG_TAG = "MLImageHelper";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public final static int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 1064;
    public final static int REQUEST_READ_EXTERNAL_STORAGE = 2031;
    File photoFile;

    private ImageView inputImageView;
    private TextView outputTextView;

    private TextView titleTextView;

    private ImageView camView;


    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_helper);
        recentImages = findViewById(R.id.FinalBetterButtonPickPhoto);
        recentImages.setVisibility(View.INVISIBLE);



        capturedImages = getStoredImages();
        imageNames = retrieveImageNames();












        textToSpeech = new TextToSpeech(this, this);

        inputImageView = findViewById(R.id.imageView);
        outputTextView = findViewById(R.id.textView);
        titleTextView = findViewById(R.id.funnyText);
        camView = findViewById(R.id.camView);

        View.OnTouchListener touchListener = (v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                speak(v.getContentDescription().toString());
            }
            return false;
        };

        titleTextView.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                speak("Choose a Photo Using These Buttons");
            }
            return false;
        });


        recentImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setContentView(R.layout.recent_images);
                int lastIndex = capturedImages.size() - 1;
                imageView1 = findViewById(R.id.imageView1);
                imageView2 = findViewById(R.id.imageView2);
                imageView3 = findViewById(R.id.imageView3);
                imageView4 = findViewById(R.id.imageView4);
                imageView5 = findViewById(R.id.imageView5);
                imageView6 = findViewById(R.id.imageView6);
                imageView7 = findViewById(R.id.imageView7);
                imageView8 = findViewById(R.id.imageView8);
                imageView9 = findViewById(R.id.imageView9);
                imageView10 = findViewById(R.id.imageView10);
                imageView1.setImageBitmap(capturedImages.get(lastIndex));
                imageView2.setImageBitmap(capturedImages.get(lastIndex - 1));
                imageView3.setImageBitmap(capturedImages.get(lastIndex - 2));
                imageView4.setImageBitmap(capturedImages.get(lastIndex - 3));
                imageView5.setImageBitmap(capturedImages.get(lastIndex - 4));
                imageView6.setImageBitmap(capturedImages.get(lastIndex - 5));
                imageView7.setImageBitmap(capturedImages.get(lastIndex - 6));
                imageView8.setImageBitmap(capturedImages.get(lastIndex - 7));
                imageView9.setImageBitmap(capturedImages.get(lastIndex - 8));
                imageView10.setImageBitmap(capturedImages.get(lastIndex - 9));

                textView1 = findViewById(R.id.textView1);
                textView2 = findViewById(R.id.textView2);
                textView3 = findViewById(R.id.textView3);
                textView4 = findViewById(R.id.textView4);
                textView5 = findViewById(R.id.textView5);
                textView6 = findViewById(R.id.textView6);
                textView7 = findViewById(R.id.textView7);
                textView8 = findViewById(R.id.textView8);
                textView9 = findViewById(R.id.textView9);
                textView10 = findViewById(R.id.textView10);

                int betterIndex = imageNames.size() - 1;

                for (int i = 0; i < imageNames.size(); i++) {
                    String name = imageNames.get(i);
                    if (name == null || name == ""){
                        imageNames.set(i, "No Name");
                    }
                }

                textView1.setText(imageNames.get(betterIndex));
                textView2.setText(imageNames.get(betterIndex - 1));
                textView3.setText(imageNames.get(betterIndex - 2));
                textView4.setText(imageNames.get(betterIndex - 3));
                textView5.setText(imageNames.get(betterIndex - 4));
                textView6.setText(imageNames.get(betterIndex - 5));
                textView7.setText(imageNames.get(betterIndex - 6));
                textView8.setText(imageNames.get(betterIndex - 7));
                textView9.setText(imageNames.get(betterIndex - 8));
                textView10.setText(imageNames.get(betterIndex - 9));
            }
        });



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_READ_EXTERNAL_STORAGE);
            }
        }
    }

    public void onTakeImage(View view) {
        speak("Take Photo");
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference for future access
        photoFile = getPhotoFileUri(new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".jpg");

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(this, "com.iago.fileprovider1", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }



    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), LOG_TAG);

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(LOG_TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);

        return file;
    }

    /**
     * getCapturedImage():
     *     Decodes and crops the captured image from camera.
     */
    public Bitmap getCapturedImage() {
        // Get the dimensions of the View
        int targetW = inputImageView.getWidth();
        int targetH = inputImageView.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoFile.getAbsolutePath());
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH /targetH));

        bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inMutable = true;
        return BitmapFactory.decodeFile(photoFile.getAbsolutePath(), bmOptions);
    }


    /**
     * Rotate the given bitmap.
     */
    private Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(
                source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true
        );
    }

    public void onPickImage(View view) {
        speak("Pick Image from Library");
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, PICK_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }



    protected TextView getOutputTextView() {
        return outputTextView;
    }

    protected ImageView getCamView() {
        return camView;
    }

    protected TextView getTitleTextView() {
        return titleTextView;
    }

    protected ImageView getInputImageView() {
        return inputImageView;
    }

    protected void runDetection(Bitmap bitmap) {

    }

    protected Bitmap loadFromUri(Uri photoUri) {
        Bitmap image = null;

        try {
            if (Build.VERSION.SDK_INT > 27) {
                ImageDecoder.Source source = ImageDecoder.createSource(this.getContentResolver(), photoUri);
                image = ImageDecoder.decodeBitmap(source);
            } else {
                image = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoUri);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        return image;
    }






    private void rotateIfRequired(Bitmap bitmap) {
        try {
            ExifInterface exifInterface = new ExifInterface(photoFile.getAbsolutePath());
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED
            );

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                rotateImage(bitmap, 90f);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                rotateImage(bitmap, 180f);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                rotateImage(bitmap, 270f);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {



        for (int i = 0; i < 10; i++) {
            capturedImages.add(i, BitmapFactory.decodeResource(getResources(), R.drawable.cam));;
        }
        for (int i = 0; i < 10; i++) {
            imageNames.add(i, "No Name");;
        }
        for (Bitmap image : capturedImages) {
            Log.d("Image", image.toString()); // Assuming you want to print the bitmap object itself
        }




        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap bitmap = getCapturedImage();
                rotateIfRequired(bitmap);
                inputImageView.setImageBitmap(bitmap);
                recentImages.setVisibility(View.VISIBLE);

                // ...



                // Add the bitmap to the capturedImages list
                capturedImages.add(bitmap);

                if (capturedImages.size() > 11){
                    capturedImages.remove(0);
                }

                // Store the updated capturedImages list in SharedPreferences
                storeImages(capturedImages);



                runDetection(bitmap);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Image Name");
                final EditText input = new EditText(this);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String imageName = input.getText().toString();
                        imageNames.add(imageName);




                        storeImageNames(imageNames);
                        // ...

                        // Add the bitmap to the capturedImages list

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String imageName ="No Name";
                        imageNames.add(imageName);
                        dialog.cancel();
                    }
                });
                builder.show();









                // IMAGE STUFF


                // Create ImageView

                // Set the parent layout as the content view of the activity
               // setContentView(parentLayout);



                // ...
            } else {
                Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Bitmap takenImage = loadFromUri(data.getData());
                inputImageView.setImageBitmap(takenImage);
                recentImages.setVisibility(View.VISIBLE);
                capturedImages.add(takenImage);

                storeImages(capturedImages);
                runDetection(takenImage);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Image Name");
                final EditText input = new EditText(this);
                builder.setView(input);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String imageName = input.getText().toString();
                        imageNames.add(imageName);




                        storeImageNames(imageNames);
                        // ...

                        // Add the bitmap to the capturedImages list

                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String imageName ="No Name";
                        imageNames.add(imageName);
                        dialog.cancel();
                    }
                });
                builder.show();







                // ...

                // Add the bitmap to the capturedImages list


                // ...
            } else {
                Toast.makeText(this, "Picture wasn't selected!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void storeImageNames(List<String> imageNames) {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Set<String> imageNameSet = new HashSet<>(imageNames);
        editor.putStringSet("imageNames", imageNameSet);
        editor.apply();
    }

    private List<String> retrieveImageNames() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyAppPreferences", MODE_PRIVATE);
        Set<String> imageNameSet = sharedPreferences.getStringSet("imageNames", new HashSet<>());
        return new ArrayList<>(imageNameSet);
    }





    public List<Bitmap> getBits(){
        for (Bitmap capturedImage : capturedImages) {
            Log.d("CapturedImage", capturedImage.toString());

        }
        return capturedImages;
    }

    public List<Bitmap> getReturnStored(){
        return getStoredImages();
    }







    /**
     * Draw bounding boxes around objects together with the object's name.
     */

    // FOR TTS
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

    // store images :



    private List<Bitmap> getStoredImages() {
        List<Bitmap> images = new ArrayList<>();

        // Retrieve the capturedImages list from SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        int imageCount = sharedPreferences.getInt("imageCount", 0);
        for (int i = 0; i < imageCount; i++) {
            String imageKey = "image_" + i;
            String encodedImage = sharedPreferences.getString(imageKey, null);
            if (encodedImage != null) {
                byte[] decodedBytes = Base64.decode(encodedImage, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
;
            }
        }

        return images;
    }

    private void storeImages(List<Bitmap> images) {
        // Store the capturedImages list in SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF_KEY, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("imageCount", images.size());
        for (int i = 0; i < images.size(); i++) {
            String imageKey = "image_" + i;
            Bitmap bitmap = images.get(i);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] bytes = byteArrayOutputStream.toByteArray();
            String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
            editor.putString(imageKey, encodedImage);
        }

        editor.apply();
    }


}