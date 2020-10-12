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

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.TextView;

public class Dashboard extends AppCompatActivity {
    TextView txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        txt=findViewById(R.id.txtMessage);
        String message=getIntent().getStringExtra("UserName");
        txt.setText("Hello "+message.toString()+"!");
    }
}
