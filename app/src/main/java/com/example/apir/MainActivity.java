package com.example.apir;

import static java.lang.invoke.VarHandle.AccessMode.GET;

import android.os.Bundle;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import WebServices.Asynchtask;
import WebServices.WebService;

public class MainActivity extends AppCompatActivity implements Asynchtask {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });
        Map<String, String> datos = new HashMap<String, String>();
        WebService ws = new WebService(
                "https://uealecpeterson.net/turismo/lugar_turistico/json_getlistadoGridLT",
                datos, MainActivity.this, MainActivity.this);
        ws.execute("GET");


    }



    @Override
    public void processFinish(String result) throws JSONException {
        //parsear
        String lugares = "";
        JSONObject jsonObject = new JSONObject(result);

        JSONArray JSONlista =  jsonObject.getJSONArray("data");
        for(int i=0; i< JSONlista.length();i++){
            JSONObject lugar= JSONlista.getJSONObject(i);
            lugares = lugares
                    + lugar.getString("nombre_lugar") + ": "
                    + lugar.getString("subcategoria") + "\n";
        }

        EditText txtlista = findViewById(R.id.txtlista);
        txtlista.setText(lugares);

    }
}