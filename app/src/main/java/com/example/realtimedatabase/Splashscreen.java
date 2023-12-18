package com.example.realtimedatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class Splashscreen extends AppCompatActivity {

    LottieAnimationView lottie;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
            lottie = findViewById(R.id.lottie);
            text=findViewById(R.id.text);

        text.animate().translationY(-1400).setDuration(2700).setStartDelay(0);// giving animation to text
        //translationY(-1400): This animates the appname view by moving it upwards (negative translation in the Y direction) by 1400 pixels.
        lottie.animate().translationX(2000).setDuration(9000).setStartDelay(5000);
        //translationX(2000): This animates the lottie view by moving it to the right (positive translation in the X direction) by 2000 pixels.
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                  Intent intent = new Intent(Splashscreen.this, Signup.class);
                    startActivity(intent);
                }
            }, 2200);
        }
    }
