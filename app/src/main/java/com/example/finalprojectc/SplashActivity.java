/*
* Project Name: Expense Tracker
Project Description: Expense Tracker is an application that allows its users to save and track their daily expenses. It takes inputs from user such as annual income, desired savings and maximum daily expense
* and notifies user by changing the respective items on the home screen. It also allows user to add their own categories to add expenses and delete if not needed by providing more flexibility to user.
*  User can also track the expenses or savings by using reports feature that generates bar graphs according to the range of dates provided. There are also few functionalities to change password,
*  to remember user login state by eliminating the need of logging in every time.
* Team members:
	Haritha Nimmagadda
	Kiran Panjam
Refereneces: The calculator functionality in this app is based on "https://technobyte.org/simple-calculator-app-in-android-studio/"

*/
package com.example.finalprojectc;

import android.content.Intent;
import android.os.Handler;import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 4000;

    Animation topAnim, bottomAnim;
    ImageView logo_image;
    TextView logo_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);

        logo_image = findViewById(R.id.logo_image_id);
        logo_text = findViewById(R.id.logo_text_id);

        logo_image.setAnimation(topAnim);
        logo_text.setAnimation(bottomAnim);

        //To make splash run for given duration(i.e SPLASH_SCREEN)
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, LoginPageActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_SCREEN);

    }
}