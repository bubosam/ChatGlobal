package com.example.boush.dreamchat;



import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {


    private static int RESULT_LOAD_IMAGE = 1;
    private FloatingActionButton Select;
    private ImageView imageView;
    private FloatingActionButton Upload;


    private EditText username;
    private AutoCompleteTextView email;
    private EditText password;
    private Button submit;

    private ImageButton rotatePicture;

    private EditText phone;
    private EditText firstName;
    private EditText lastName;

    float deg;

    private static final String updateUrl = "http://10.0.2.2:1337/update";
    String tag_json_obj = "json_obj_req";

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        Select = (FloatingActionButton) view.findViewById(R.id.selectPhoto);
        imageView = (ImageView) view.findViewById(R.id.ImageView);
        rotatePicture = (ImageButton) view.findViewById(R.id.imageButton);
        username = (EditText) view.findViewById(R.id.userNameUpdate);
        email = (AutoCompleteTextView) view.findViewById(R.id.emailUpdate);
        password = (EditText) view.findViewById(R.id.passwordUpadte);
        submit = (Button) view.findViewById(R.id.submitUpdate);
        Upload = (FloatingActionButton) view.findViewById(R.id.uploadPhoto);
        phone = (EditText) view.findViewById(R.id.phoneUpdate);
        firstName = (EditText) view.findViewById(R.id.firstNameUpdate);
        lastName = (EditText) view.findViewById(R.id.lastNameUpdate);

        deg = imageView.getRotation();

        Select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i, RESULT_LOAD_IMAGE);

            }
        });

        rotatePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rotate();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser();
            }
        });

        Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getActivity(), "Profile photo uploaded !",
                        Toast.LENGTH_LONG).show();
            }
        });

        return view;

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == getActivity().RESULT_OK && null != data) {
            Uri selectedImg = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getActivity().getContentResolver().query(selectedImg,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);

            imageView.setImageBitmap(cropToSquare(BitmapFactory.decodeFile(picturePath)));
            Toast.makeText(getActivity(), "Photo selected !",
                    Toast.LENGTH_LONG).show();
            cursor.close();
        }
    }


    public void rotate() {
        deg += 90F;
        imageView.animate().rotation(deg).setInterpolator(new AccelerateDecelerateInterpolator());
    }

    public void updateUser() {
        if (!username.getText().toString().isEmpty() ) {
            String usernameVal = username.getText().toString().trim();
            jSonSend("username",usernameVal);
        }
        if(!email.getText().toString().isEmpty())
        {
            String emailVal = email.getText().toString();
            jSonSend("email",emailVal);
        }

        if(!password.getText().toString().isEmpty())

        {
            String passwordVal = password.getText().toString();
            jSonSend("password",passwordVal);

        }

        if(!phone.getText().toString().isEmpty()){
            String phoneVal = phone.getText().toString();
            jSonSend("phone",phoneVal);
        }
        if(!firstName.getText().toString().isEmpty()){
            String firstNameVal = firstName.getText().toString();
            jSonSend("firstName",firstNameVal);
        }
        if(!lastName.getText().toString().isEmpty()){
            String lastNameVal = lastName.getText().toString();
            jSonSend("lastName",lastNameVal);
        }
    }

    public void jSonSend(String type,String value){
        try {
            // Simulate network access.
            Map<String, String> postParam = new HashMap<String, String>();
            postParam.put(type, value);

            Log.d("Volley JSON to send ", new JSONObject(postParam).toString());

            JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                    updateUrl, new JSONObject(postParam),
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("Volley ", response.toString());
                            String token = null;
                            int userid;
                            try {
                                token = response.getString("token");
                                userid = response.getInt("userid");
                                Log.d("Volley token", token);
                                Log.d("Volley userid", String.valueOf(userid));
                                SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                SharedPreferences.Editor editor = sharedPref.edit();
                                editor.clear();
                                editor.apply();
                                editor.putInt("userid", userid);
                                editor.putString("token", token);
                                editor.apply();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d("Volley ", "Error: " + error.getMessage());
                }
            })

            {
                /**
                 * Passing some request headers
                 */
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    return headers;
                }
            };

            // Adding request to request queue
            AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);


        } catch (Exception e) {
            VolleyLog.d("Volley ", "Error: " + e.toString());
        }
    }

    public static Bitmap cropToSquare(Bitmap bitmap){
        int width  = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = (height > width) ? width : height;
        int newHeight = (height > width)? height - ( height - width) : height;
        int cropW = (width - height) / 2;
        cropW = (cropW < 0)? 0: cropW;
        int cropH = (height - width) / 2;
        cropH = (cropH < 0)? 0: cropH;
        Bitmap cropImg = Bitmap.createBitmap(bitmap, cropW, cropH, newWidth, newHeight);

        return cropImg;
    }
}




