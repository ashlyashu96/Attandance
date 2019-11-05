package com.example.attandance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class QRReader extends AppCompatActivity {
    private IntentIntegrator qrScan;
    IntentResult result;
    SharedPreferences sh;
    String currentTime = new SimpleDateFormat("mm", Locale.getDefault()).format(new Date());
    String Time = new SimpleDateFormat("hh:mm", Locale.getDefault()).format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrreader);
        sh=getSharedPreferences("details",MODE_PRIVATE);
            qrScan = new IntentIntegrator(this);
            qrScan.initiateScan();
        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                //if qrcode has nothing in it
                if (result.getContents() == null) {
                    Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
                } else if(result.getContents().equals("inet")){
                    //if qr contains data

                    //to a toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();

                    if(Integer.parseInt(currentTime)>30)
                    {
                        SendMsg();
                    }
                    else {
                        getSystemInfo();
                    }

                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }

        }
        public  void getSystemInfo()
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://full-bottomed-cushi.000webhostapp.com/Attandance.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
//If we are getting success from server

                           Toast.makeText(QRReader.this,response,Toast.LENGTH_SHORT).show();
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject json_obj = jsonArray.getJSONObject(i);
                                   // ram=json_obj.getString("ram");

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                        }

                    }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String,String> params = new HashMap<>();
//Adding parameters t o request

                    params.put("name",sh.getString("name",null));
                    params.put("regno",sh.getString("regno",null));


//returning parameter
                    return params;
                }
            };

//Adding the string request to the queue
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    public  void SendMsg()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST,"https://full-bottomed-cushi.000webhostapp.com/AttandanceSMS.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//If we are getting success from server

                        Toast.makeText(QRReader.this,response,Toast.LENGTH_SHORT).show();
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject json_obj = jsonArray.getJSONObject(i);
                                // ram=json_obj.getString("ram");

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//You can handle error here if you want
                    }

                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
//Adding parameters t o request

                params.put("name",sh.getString("name",null));
                params.put("regno",sh.getString("regno",null));
                params.put("ctime",currentTime);
                params.put("time",currentTime);

//returning parameter
                return params;
            }
        };

//Adding the string request to the queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    }

