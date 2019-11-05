package com.example.attandance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
   EditText username,password;
   TextView textView;
   Button b1;
   SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username=findViewById(R.id.editText3);
        password=findViewById(R.id.editText4);
        textView=findViewById(R.id.textView2);
        b1=findViewById(R.id.button);
        sh=getSharedPreferences("details",MODE_PRIVATE);
        final SharedPreferences.Editor edior=sh.edit();
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                edior.putString("name", username.getText().toString());
                edior.putString("regno",password.getText().toString());
                edior.apply();
                Intent intent=new Intent(MainActivity.this,QRReader.class);
                startActivity(intent);
            }
        });
    }

    }

