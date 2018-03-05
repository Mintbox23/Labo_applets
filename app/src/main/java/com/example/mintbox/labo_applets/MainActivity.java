package com.example.mintbox.labo_applets;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Calendar;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;



/**
 * Created by MintBox on 1/18/2018.
 *
 * MAIN ACTIVITY OF THE APPLICATION. MANAGES THE RECYCLERVIEW AND THE CHECKBOXES.
 *
 */


public class MainActivity extends AppCompatActivity {


    private DatabaseReference mDatabase;                //Reference to the firebase database "Tasks" subsection.
    private RecyclerView mTaskList;                     //Reference to our recyclerView.
    private LinearLayoutManager linearLayoutManager;    //LayoutManager gives access to advanced options for our recyclerView.
    private CheckBox checkBox;                          //Reference to a the checkBox child of every element of the recyclerView.
    private TextView completedDate;                     //Reference to the COMPLETEDDATE child of every element of the recyclerView.
    private int taskID;                                 //The task ID that will be sent to the detail activity when a recyclerView element is clicked.
    private int i;                                      //Variable for a forLoop in checkBox section.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstlayout);

        //Linking our reference with the XML RecyclerView.
        mTaskList = findViewById(R.id.taskRec);

        //Setting our View items with fixed parameters (width, height). Better optimization from the RV.
        mTaskList.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setStackFromEnd(false);

        //Associating the layout manager we created with our recyclerView.
        mTaskList.setLayoutManager(linearLayoutManager);

        //Linking our database reference with firebase.
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Tasks");


    }


    public void onStart() {
        super.onStart();

        //Setting up the recyclerAdapter for the recyclerView.
        final FirebaseRecyclerAdapter<Contact, TaskViewHolder> FBRA = new FirebaseRecyclerAdapter<Contact, TaskViewHolder>(

                Contact.class,                  //model class

                R.layout.singletasklayout,      //RV single element

                TaskViewHolder.class,           //viewHolder class

                mDatabase                       //firebase database
        ) {
            @Override
            protected void populateViewHolder(TaskViewHolder viewHolder, Contact model, int position) {

                //Sending the data our model class got from firebase to the viewHolder for display.
                viewHolder.setName(model.getName());
                viewHolder.setCompletedDate(model.getCompletedDate());
                viewHolder.setIsCompleted(model.getIsCompleted());

            }


        };


        //TouchListener to manage a click on a specific element of the recyclerView.
        mTaskList.addOnItemTouchListener(new RecyclerItemClickListener(this, mTaskList ,new RecyclerItemClickListener.OnItemClickListener() {

                    @Override public void onItemClick(View view, int position) {
                        taskID = position;

                        //Sending to task ID to the next Activity for it to be able to access the correct firebase data.
                        Intent intent = new Intent(getBaseContext(), detailActivity.class);
                        intent.putExtra("TASK_ID", taskID);
                        startActivity(intent);
                    }
                    @Override public void onLongItemClick(View view, int position) {

                    }

                })
        );


        mTaskList.setAdapter(FBRA); //ATTACHING THE ADAPTER TO OUR RECYCLERVIEW.


    }







    //Method that manages clicks on checkboxes. A CHECKBOX NEEDS TO BE LONGCLICKED.
    public void checkBoxClicked(View view) {

            //Loop verifying each element of the recyclerView.
            for(i=0;i<mTaskList.getChildCount();i++) {

                //Linking our references to each element checkbox and completed date.
                checkBox = mTaskList.getChildAt(i).findViewById(R.id.check_box);
                completedDate = mTaskList.getChildAt(i).findViewById(R.id.task_completion);



                if (checkBox.isChecked() && completedDate.getText().toString() == "") {
                    mDatabase.child(Integer.toString(i)).child("isCompleted").setValue(true);
                    mDatabase.child(Integer.toString(i)).child("completedDate").setValue(Calendar.getInstance().getTime().toString());
                }
            }
        }



    //Sending the user to the AddTask activity.
    public void addButtonClicked(View view){

        startActivity(new Intent(MainActivity.this,AddTask.class));

    }

}

