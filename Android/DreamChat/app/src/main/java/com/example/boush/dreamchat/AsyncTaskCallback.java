package com.example.boush.dreamchat;

import java.util.List;

/**
 * Created by Client on 16.6.2016.
 */
public interface AsyncTaskCallback {
    void onSuccess(List<Contact> result);
    void onSuccess(int result);
}
