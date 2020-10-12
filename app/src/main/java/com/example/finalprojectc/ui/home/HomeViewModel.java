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
package com.example.finalprojectc.ui.home;


import android.app.Application;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.finalprojectc.DatabaseHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;

public class HomeViewModel extends AndroidViewModel {

    private MutableLiveData<HashMap<String,String>> homePageData;
    HashMap<String,String> userGoalsNdailyExpenses;
    DatabaseHelper databaseHelper;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        homePageData = new MutableLiveData<>();
        userGoalsNdailyExpenses=new HashMap<>();
        userGoalsNdailyExpenses.put("header1","Annual Goals");
        databaseHelper=new DatabaseHelper(application.getApplicationContext());
         Cursor goals=databaseHelper.getUserGoals(databaseHelper.getActiveUserId());
        String AnnualIncome=null;
        String DailyIncome = null;
        String max_daily_expense=null;
        String max_yearly_expense_limit=null;
        String DesiredAnnualSavings = null;
        String TodaysExpenses=null;
        String TodaysSavings=null;
        String ThisyearSavings=null;
         if(goals.getCount()>0)
         {
             goals.moveToFirst();
              AnnualIncome=goals.getString(goals.getColumnIndex("annual_income"));
              DailyIncome=String.valueOf(Double.parseDouble(AnnualIncome)/365);
              max_daily_expense=goals.getString(goals.getColumnIndex("max_daily_expense"));
              max_yearly_expense_limit=String.valueOf(Double.parseDouble(max_daily_expense)*365);
              DesiredAnnualSavings=goals.getString(goals.getColumnIndex("desired_savings_for_year"));

             userGoalsNdailyExpenses.put("AnnualIncome",AnnualIncome);
             userGoalsNdailyExpenses.put("DailyIncome",DailyIncome);
             userGoalsNdailyExpenses.put("DesiredAnnualSavings",DesiredAnnualSavings);
             userGoalsNdailyExpenses.put("DailyExpenseLimit",max_daily_expense);
             userGoalsNdailyExpenses.put("ExpectedAnnualMaxExpense",max_yearly_expense_limit);
             goals.close();

         }
        HashMap<String,String> DailyStatus=new HashMap<>();
         DailyStatus=databaseHelper.getTodayStatus(databaseHelper.getActiveUserId());

         if(DailyStatus.size()>0)
         {
             TodaysExpenses=DailyStatus.get("TodayExpenses");
             TodaysSavings =DailyStatus.get("TodaySavings");
             ThisyearSavings =DailyStatus.get("ThisyearSavings");
             userGoalsNdailyExpenses.put("TodaysExpenses",TodaysExpenses);
             userGoalsNdailyExpenses.put("TodaysSavings",TodaysSavings);
             userGoalsNdailyExpenses.put("ThisyearSavings",ThisyearSavings);
             if(ThisyearSavings!=null && max_daily_expense!=null )
             {
                 if(Double.parseDouble(ThisyearSavings)<1)
                 {
                     Calendar cal = Calendar.getInstance();
                     double dayOfYear = cal.get(Calendar.DAY_OF_YEAR);
                     double total_days_remaining = 365-dayOfYear;
                   double NewDailyExpenseLimit=  ((Double.parseDouble(DailyIncome)*(total_days_remaining))-Double.parseDouble(DesiredAnnualSavings))/  total_days_remaining;
                   databaseHelper.UpdateMaxDailyExpense(NewDailyExpenseLimit,databaseHelper.getActiveUserId());
                   if(userGoalsNdailyExpenses.containsKey("DailyExpenseLimit")) {
                       userGoalsNdailyExpenses.replace("DailyExpenseLimit", String.valueOf(NewDailyExpenseLimit));
                   }
                   else
                   {
                       userGoalsNdailyExpenses.put("DailyExpenseLimit", String.valueOf(NewDailyExpenseLimit));
                   }
                     Toast.makeText(getApplication().getApplicationContext(),"Savings Nil.Hence, new Expense Limit of $"+NewDailyExpenseLimit +" is Set.", Toast.LENGTH_LONG);
                 }
             }
         }

            // homePageData.setValue("Annual Goals");
            homePageData.setValue(userGoalsNdailyExpenses);
            //Annual Status

    }






    public LiveData<HashMap<String,String>> getText() {
        return homePageData;
    }


}