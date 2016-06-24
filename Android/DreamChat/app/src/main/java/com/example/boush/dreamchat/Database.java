package com.example.boush.dreamchat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

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
    private static final String KEY_CONVERSATION = "idConversation";
    private static final String KEY_NAME = "name";
    private static final String KEY_LASTNAME = "lastName";

    private SQLiteDatabase database;

    public Database(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        database = db;
        String sql = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGES + " ( "
    + KEY_CONVERSATION +" INTEGER, "+ KEY_MYID + " INTEGER, "+KEY_RECID+" INTEGER, "+KEY_MESSAGE+" TEXT, "+KEY_ISME+" TEXT, "+KEY_DATE+" TEXT, "+KEY_NAME+" TEXT, "+KEY_LASTNAME+" TEXT)";
        db.execSQL(sql);
    }

    public void addMessage(Message msg, int conversationId) {

        ContentValues values = new ContentValues();

        values.put(KEY_CONVERSATION, conversationId);
        values.put(KEY_MYID, msg.getMyId());
        values.put(KEY_RECID, msg.getRecId());
        values.put(KEY_MESSAGE, msg.getMessageText());
        values.put(KEY_ISME,msg.isMe());
        values.put(KEY_DATE,msg.getDate());
        values.put(KEY_NAME,msg.getFirstName());
        values.put(KEY_LASTNAME,msg.getLastName());

        database.insert(TABLE_MESSAGES, null, values);
    }

    public List<Message> getHistory(int conversationId) {
        List<Message> msgList = new ArrayList<Message>();
        String selectQuery = "SELECT * FROM "+TABLE_MESSAGES+" WHERE idConversation LIKE "+conversationId;
        database=this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();

        while(cursor.isAfterLast()==false){

                Message message = new Message();
                message.setMyId(cursor.getInt(1));
                message.setRecId(cursor.getInt(2));
                message.setMessageText(cursor.getString(3));
                message.setMe(cursor.getInt(4) > 0);
                message.setDate(cursor.getString(5));
                message.setFirstName(cursor.getString(6));
                message.setLastName(cursor.getString(7));

                msgList.add(message);
                cursor.moveToNext();
            }
        return msgList;
    }

    public List<Conversation> getConversations(int myId){
        List<Conversation> conversations = new ArrayList<>();
        String selectQuery = "SELECT "+KEY_CONVERSATION+","+KEY_MYID+","+KEY_RECID+","+KEY_MESSAGE+","+KEY_NAME+","+KEY_LASTNAME+" FROM "+TABLE_MESSAGES+" WHERE myID LIKE "+myId;
        database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery(selectQuery, null);
        cursor.moveToFirst();


        while(cursor.isAfterLast()==false){
            Conversation conversation= new Conversation(cursor.getInt(cursor.getColumnIndex(KEY_CONVERSATION)),
                    cursor.getInt(cursor.getColumnIndex(KEY_MYID)),
                    cursor.getInt(cursor.getColumnIndex(KEY_RECID)),
                    cursor.getString(cursor.getColumnIndex(KEY_MESSAGE)),
                    cursor.getString(cursor.getColumnIndex(KEY_NAME)),
                    cursor.getString(cursor.getColumnIndex(KEY_LASTNAME))
            );

            conversations.add(conversation);
            cursor.moveToNext();

        }
        return  conversations;
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldV, int newV) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        // Create tables again
        onCreate(db);
    }
}
