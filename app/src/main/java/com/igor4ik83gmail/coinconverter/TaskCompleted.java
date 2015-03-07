package com.igor4ik83gmail.coinconverter;

/**
 * Created by igor4ik2 on 29.01.2015.
 */
public interface TaskCompleted {
    // Define data you like to return from AysncTask
    public void onTaskComplete(Double result, String from);
}
