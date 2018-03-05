package com.example.mintbox.labo_applets;

import android.support.annotation.RequiresPermission;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by MintBox on 2/15/2018.
 *
 * VIEWHOLDER CLASS
 *
 * MANAGES THE DISPLAY OF THE RECYCLERVIEW ELEMENTS.
 *
 */

public class TaskViewHolder extends RecyclerView.ViewHolder {

    View mView;

    //Constructor
    public TaskViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
    }

    //Method displaying the Task Names from the model Class on the recyclerView elements.
    public void setName(String name) {

        TextView task_content = mView.findViewById(R.id.task_name);

        if (name != null) {
            task_content.setText(name);
        }
    }


    //Method displaying the time the Task was completed or nothing if the task is not completed yet.
    public void setCompletedDate(String completedDate) {

        TextView task_time = (TextView) mView.findViewById(R.id.task_completion);

        if(completedDate != null) {

            //formatting date in Java using SimpleDateFormat
            try {
                DateFormat format1 = new SimpleDateFormat("EEE LLL dd HH:mm:ss zzz YYYY", Locale.ENGLISH);
                DateFormat format2 = new SimpleDateFormat("LLL d, YYYY - h:mm aa", Locale.ENGLISH);

                Date date1 = format1.parse(completedDate);

                task_time.setText(format2.format(date1));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }


    //Method checking the checkBoxes of the recyclerView elements according to the data from the model class.
    public void setIsCompleted(boolean isCompleted){
        CheckBox chkB = mView.findViewById(R.id.check_box);

        if (isCompleted){
            chkB.setChecked(true);
        }

    }

}
