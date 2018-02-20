package com.example.mintbox.labo_applets;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

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

            //Updating the time string to a specific format before displaying it.
            String time_short = completedDate.substring(11, 16);
            String hours_string = completedDate.substring(11, 13);
            int hours_int = Integer.parseInt(hours_string);

            if (hours_int > 12) {
                task_time.setText(completedDate.substring(4, 10) + ", " + completedDate.substring(24, 28) + " - " + (hours_int - 12) + completedDate.substring(13, 16) + " PM");
            } else if (hours_int == 12) {
                task_time.setText(completedDate.substring(4, 10) + ", " + completedDate.substring(24, 28) + " - " + time_short + " PM");
            } else if (hours_int < 12) {
                if (hours_int >= 10) {
                    task_time.setText(completedDate.substring(4, 10) + ", " + completedDate.substring(24, 28) + " - " + time_short + " AM");
                } else {
                    task_time.setText(completedDate.substring(4, 10) + ", " + completedDate.substring(24, 28) + " - " + completedDate.substring(12, 16) + " AM");
                }
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
