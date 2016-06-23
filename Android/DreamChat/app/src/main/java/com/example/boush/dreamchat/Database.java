package com.example.boush.dreamchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Majkl on 4. 6. 2016.
 */
public class Database extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "dreamchatDb";
    // tasks table name
    private static final String TABLE_MESSAGES = "messages";
    // tasks Table Columns names
    private static final String KEY_MYID = "myId";
    private static final String KEY_RECID = "recId";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_ISME = "isme";
    private static final String KEY_DATE = "date";

    private SQLiteDatabase database;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES + " ( "
                + KEY_MYID + " INTEGER, "+KEY_RECID+" INTEGER, "+KEY_MESSAGE+" TEXT, "+KEY_ISME+" TEXT, "+KEY_DATE+" TEXT)";
        db.execSQL(sql);
    }

    public void addMessage(Message msg) {

        ContentValues values = new ContentValues();
        values.put(KEY_MYID, msg.getMyId());
        values.put(KEY_RECID, msg.getRecId());
        values.put(KEY_MESSAGE, msg.getMessageText());
        values.put(KEY_ISME,msg.isMe());
        values.put(KEY_DATE,msg.getDate());

        database.insert(TABLE_MESSAGES, null, values);
    }

    public List<Message> getHistory(int myId, int recId) {
        List<Message> msgList = new ArrayList<Message>();

        String selectQuery = "SELECT * FROM "+TABLE_MESSAGES+" WHERE myID LIKE "+myId+" AND recId LIKE "+recId;
        database=this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {

                Message message = new Message();
                message.setMyId(cursor.getInt(0));
                message.setRecId(cursor.getInt(1));
                message.setMessageText(cursor.getString(2));
                message.setMe(cursor.getInt(3) > 0);
                message.setDate(cursor.getString(4));

                msgList.add(message);
            } while (cursor.moveToNext());
        }
        return msgList;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        // Create tables again
        onCreate(db);
    }
}
