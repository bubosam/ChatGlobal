package com.example.boush.dreamchat;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;


public class TestActivity extends Activity {

    private static final String TAG = "WebSocket Test";
    private static final String KEY_PAYLOAD = "key_payload";

    private final WebSocketConnection mConnection = new WebSocketConnection();
    Handler handler = new Handler(Looper.getMainLooper());

    private void start() {

        final String wsuri = "ws://10.0.2.2:1337";

        try {
            mConnection.connect(wsuri, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + wsuri);
                    mConnection.sendTextMessage("Hello, world!");
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG, "Got echo: " + payload);
                    Bundle bundle = new Bundle();
                    Message message = new Message();

                    bundle.putString(KEY_PAYLOAD, payload);
                    message.setData(bundle);

                    handler.sendMessage(message);

                    try {
                        // JSON object
                        JSONObject data = new JSONObject(bundle.getString(KEY_PAYLOAD));

                        /*item = data.getJSONObject(KEY_DATA);

                        chat = new ChatMessage();
                        chat.client = item.getString(ChatMessage.KEY_CLIENT);
                        chat.red = item.getInt(ChatMessage.KEY_RED);
                        chat.green = item.getInt(ChatMessage.KEY_GREEN);
                        chat.blue = item.getInt(ChatMessage.KEY_BLUE);
                        chat.message = item.getString(ChatMessage.KEY_MESSAGE);

                        items.add(chat);*/
                    } catch(JSONException jsone) {
                        jsone.printStackTrace();
                    }

                    // Update render
                    //adapter.notifyDataSetChanged();

                    // Scroll to bottom
                    // Most recent message
                    //lstHistory.smoothScrollToPosition(adapter.getCount() - 1);
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.d(TAG, "Connection lost.");
                }
            });
        } catch (WebSocketException e) {

            Log.d(TAG, e.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        start();
    }
}