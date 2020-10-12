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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class RemoveCategory extends Fragment {
    TextView display;
    EditText enter_here;
    Button remove_category;
    Button cancel;
    private ArrayList<String> items_list;

    public RemoveCategory() {
        // Required empty public constructor
    }

    public static int userId;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        View view = inflater.inflate(R.layout.fragment_remove_category, container, false);
        items_list = databaseHelper.getAllCategoriesofUser(databaseHelper.getActiveUserId());
        display = view.findViewById(R.id.add_category_text);
        enter_here = view.findViewById(R.id.category_from_user_remove);
        remove_category = view.findViewById(R.id.remove_category);
        cancel = view.findViewById(R.id.cancel_remove);
        userId=databaseHelper.getActiveUserId();
        //System.out.println(username);

        remove_category.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String remove_category = enter_here.getText().toString();

                if(remove_category.isEmpty()){
                    enter_here.setError("Field can't be empty!");
                }
                else{

                    if(!items_list.contains(remove_category)){
                        Toast.makeText(getContext(), "Category doesn't exists!", Toast.LENGTH_LONG).show();
                    }
                    else if(databaseHelper.isCategoryInExpenses(remove_category, userId)){
                        Toast.makeText(getContext(), "Category is being used as an expense!", Toast.LENGTH_LONG).show();
                    }
                    else {
                        databaseHelper.removeCategory(remove_category);
                        Toast.makeText(getContext(), "Category removed!", Toast.LENGTH_LONG).show();
                        Fragment fragment = new CategoryLayout();
                        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                        transaction.replace(R.id.category_layout, fragment);
                        transaction.addToBackStack(null);
                        transaction.commit();
                    }
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new CategoryLayout();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.category_layout, fragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }
}
