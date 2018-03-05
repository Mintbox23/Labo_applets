package com.example.mintbox.labo_applets;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Calendar;

/**
 * Created by MintBox on 2/15/2018.
 *
 * ACTIVITY THAT ALLOWS THE USER TO ADD A TASK TO THE RECYCLERVIEW.
 *
 */

public class AddTask extends AppCompatActivity {

    private EditText addedTask;                     //Task entered by the user.
    private DatabaseReference mDatabase;            //Link to the firebase database "Tasks" subsection.
    private DatabaseReference mDatabaseTC;          //Link to the firebase databse "Task count" subsection.
    private int itemCount;                          //Number of tasks added.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addtask);


        //Linking our variable to the XML view.
        addedTask = findViewById(R.id.add_task);
        //Linking our databases references to the right paths in firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks");
        mDatabaseTC = FirebaseDatabase.getInstance().getReference().child("Task-count");

        //Event listener to get the number of tasks added so far from firebase.
        mDatabaseTC.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    //The first task ID is zero. We add "1" to this number to get the number of tasks.
                    itemCount = 1 + Integer.parseInt(dataSnapshot.getValue().toString());
                }

                //No task have been added yet.
                else
                    itemCount = 0;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    //The user pressed the Add Task Button.
    public void AddTaskButtonClicked(View view) {

        //Getting the string entered by the user.
        final String taskName = addedTask.getText().toString().trim();

        //We make sure that the string is not empty before sending it in the firebase database.
        if (!TextUtils.isEmpty(taskName)) {

            //Saving the new task data into firebase and updating the Task count.
            final DatabaseReference newPost_task = mDatabase.child(Integer.toString(itemCount));

            if (!TextUtils.isEmpty(taskName)) {

                newPost_task.child("name").setValue(taskName);

                newPost_task.child("time-created").setValue(Calendar.getInstance().getTime().toString());

                newPost_task.child("isCompleted").setValue(false);

                FirebaseDatabase.getInstance().getReference().child("Task-count").setValue(Integer.toString(itemCount));

                //Now that the message is saved we can clear the editMessage space to accommodate the user.
                addedTask.setText("");
            }

        }


    }

    //Sending the user to the MainActivity activity.
    public void ATGoBackButtonClicked(View view){

        startActivity(new Intent(AddTask.this,MainActivity.class));

    }


}


