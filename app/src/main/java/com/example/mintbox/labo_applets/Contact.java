package com.example.mintbox.labo_applets;

/**
 * Created by MintBox on 2/15/2018.
 *
 * Class that CONTACTS firebase to keep the recyclerView Data up to date.
 *
 * Those variables will be broadcasted in each element of the recyclerView.
 *
 */

public class Contact {

    private String name;
    private String completedDate;
    private boolean isCompleted;

    //Constructor #1
    public Contact(){
    }

    //Constructor #2
    public Contact(String name, String completedDate, boolean isCompleted){

        this.name = name;
        this.completedDate = completedDate;
        this.isCompleted = isCompleted;
    }

    //getters and setters
    public String getName() { return name;}

    public void setName(String name){ this.name = name;}

    public String getCompletedDate() {return completedDate;}

    public void setCompletedDate(String completedDate){ this.completedDate = completedDate;}

    public boolean getIsCompleted() {return isCompleted;}

    public void setIsCompleted(boolean isCompleted){ this.isCompleted = isCompleted;}

}
