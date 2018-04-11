package com.example.mohamedramadan.quran;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView myAuthors;
    int z;
    ArrayList<Integer> recitations = new ArrayList<>();
    private final String U = "http://api.islamhouse.com/v1/CNIguyG5QtlZ3UEk/quran/get-category/364794/ar/json/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myAuthors = findViewById(R.id.list_of_authors);
        final StringRequest stringRequest = new StringRequest(Request.Method.GET,MyUrls.getAuthors(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("authors");
                            String authors[] = new String[jsonArray.length()];

                            for (int pos = 0; pos < jsonArray.length(); pos++) {
                                authors[pos] = jsonArray.getJSONObject(pos).getString("title");
                                recitations.add(jsonArray.getJSONObject(pos).getJSONObject("recitations_info").getJSONArray("recitations_ids").getInt(0));
                                //recitations.add(jsonArray.getJSONObject(pos).getString("recitations_ids"));
                            }
                            Toast.makeText(MainActivity.this, "" + authors.length, Toast.LENGTH_SHORT).show();
                            ArrayAdapter adapter = new ArrayAdapter(MainActivity.this,R.layout.single_list_itemfor,R.id.text_view_authors, authors);
                            myAuthors.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
        myAuthors.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent  intent = new Intent(MainActivity.this,Main2Activity.class);
        intent.putExtra("recitations",recitations.get(i));
        startActivity(intent);
    }

}
