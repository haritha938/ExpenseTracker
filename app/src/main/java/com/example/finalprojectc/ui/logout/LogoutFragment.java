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
package com.example.finalprojectc.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.finalprojectc.R;

public class LogoutFragment extends Fragment {

    private LogoutViewModel logoutViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        logoutViewModel =
                ViewModelProviders.of(this).get(LogoutViewModel.class);
        View root = inflater.inflate(R.layout.fragment_logout, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        logoutViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}