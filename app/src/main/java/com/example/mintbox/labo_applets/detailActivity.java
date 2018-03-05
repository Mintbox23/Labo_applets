package com.example.mintbox.labo_applets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by MintBox on 2/18/2018.
 *
 * This activity is accessed when a child of the recyclerView is clicked.
 * It displays the detailed information of that particular child(task).
 *
 */

public class detailActivity extends AppCompatActivity {

    //References of the displayed data.
    private TextView task_id;
    private TextView task_description;
    private TextView task_status;
    private TextView time_created;
    private TextView time_completed;

    //Reference of the firebase database.
    private DatabaseReference mDatabase;

    //The id of the task received sent from the previous activity
    private int taskID;

    //The time the task was created and completed will be saved in the string.
    private String time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.taskdetails);

        //Setting up our references with the XML file.
        task_id = findViewById(R.id.task_id);
        task_description = findViewById(R.id.task_description);
        task_status = findViewById(R.id.task_status);
        time_created = findViewById(R.id.task_time_created);
        time_completed = findViewById(R.id.task_time_completed);

        //Getting the task id from previous activity.
        taskID = getIntent().getIntExtra("TASK_ID", 0);

        //Linking the firebase databse path to our reference.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks")
                        .child(Integer.toString(taskID));

    }


    public void onStart() {
        super.onStart();

        //EventListener to get data from firebase in order to set the task detail activity correctly.
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){

                    //Displaying the task ID.
                    task_id.setText("Task ID : "+Integer.toString(taskID));

                    //Displaying the task description.
                    task_description.setText("Task : "+dataSnapshot.child("name").getValue().toString());

                    //Asking the dataSnapshot if the task is completed or not.
                    if(dataSnapshot.child("isCompleted").getValue().toString() == "true")
                        task_status.setText("Status : COMPLETED");
                    else
                        task_status.setText("Status : UNCOMPLETED");

                    //Asking the dataSnapshot when the task was created.
                    time = dataSnapshot.child("time-created").getValue().toString();

                    //Updating the time string to a specific format.
                    String time_short = time.substring(11, 16);
                    String hours_string = time.substring(11, 13);
                    int hours_int = Integer.parseInt(hours_string);

                    if (hours_int > 12) {
                        time_created.setText("Created on : "+time.substring(4, 10) + ", " + time.substring(24, 28) + " - " + (hours_int - 12) + time.substring(13, 16) + " PM");
                    } else if (hours_int == 12) {
                        time_created.setText("Created on : "+time.substring(4, 10) + ", " + time.substring(24, 28) + " - " + time_short + " PM");
                    } else if (hours_int < 12) {
                        if (hours_int >= 10) {
                            time_created.setText("Created on : "+time.substring(4, 10) + ", " + time.substring(24, 28) + " - " + time_short + " AM");
                        } else {
                            time_created.setText("Created on : "+time.substring(4, 10) + ", " + time.substring(24, 28) + " - " + time.substring(12, 16) + " AM");
                        }
                    }

                    //Asking the dataSnapshot if and when the task was completed.
                    if(dataSnapshot.child("completedDate").exists()) {
                        time = dataSnapshot.child("completedDate").getValue().toString();

                        //Updating the time string to a specific format.
                        String time_short2 = time.substring(11, 16);
                        String hours_string2 = time.substring(11, 13);
                        int hours_int2 = Integer.parseInt(hours_string2);

                        if (hours_int2 > 12) {
                            time_completed.setText("Completed on : " +time.substring(4, 10) + ", " + time.substring(24, 28) + " - " + (hours_int - 12) + time.substring(13, 16) + " PM");
                        } else if (hours_int2 == 12) {
                            time_completed.setText("Completed on : " +time.substring(4, 10) + ", " + time.substring(24, 28) + " - " + time_short2 + " PM");
                        } else if (hours_int2 < 12) {
                            if (hours_int2 >= 10) {
                                time_completed.setText("Completed on : " +time.substring(4, 10) + ", " + time.substring(24, 28) + " - " + time_short2 + " AM");
                            } else {
                                time_completed.setText("Completed on : " +time.substring(4, 10) + ", " + time.substring(24, 28) + " - " + time.substring(12, 16) + " AM");
                            }
                        }

                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });




    }

    //Sending the user to the MainActivity activity.
    public void TDGoBackButtonClicked(View view){

        startActivity(new Intent(detailActivity.this,MainActivity.class));

    }

}
