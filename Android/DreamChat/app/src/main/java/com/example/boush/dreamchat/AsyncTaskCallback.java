package com.example.boush.dreamchat;

import java.util.List;

/**
 * Created by Client on 16.6.2016.
 */
public interface AsyncTaskCallback {
    void onTaskCompleted(List<Contact> result);
    void onTaskCompleted(Contact result);
    void onTaskCompleted(int result);
    void onTaskCompleted(String result);
}
