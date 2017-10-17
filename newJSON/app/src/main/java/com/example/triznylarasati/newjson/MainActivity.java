package com.example.triznylarasati.newjson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv_name = (TextView)findViewById(R.id.tv_name_nine);
        TextView tv_city = (TextView)findViewById(R.id.tv_city_nine);
        TextView tv_province = (TextView)findViewById(R.id.tv_province_nine);

        //create new JSON Object
        //this code for declare json object
        JSONObject student_json = new JSONObject();
        JSONObject address_json = new JSONObject();
        try {
            student_json.put("name", "Laras");
            address_json.put("city", "Bandung");
            address_json.put("province", "Jawa Barat");
            student_json.put("address", address_json);

            //this for handle set value to text view
            tv_name.setText("Nama : " + student_json.getString("name"));
            JSONObject jo_address = (JSONObject) student_json.get("address");
            tv_city.setText("Kota : " + jo_address.getString("city"));
            tv_province.setText("Provinsi : " + jo_address.getString("province"));
        } catch (JSONException e) {
            Log.i("info", String.valueOf(e));
        }
    }
}
