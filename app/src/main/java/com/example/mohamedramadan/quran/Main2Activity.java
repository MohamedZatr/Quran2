package com.example.mohamedramadan.quran;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

import java.io.IOException;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    int s = 0;
    ListView suares;
    MediaPlayer mediaPlayer;
    ImageButton pause, play;
    TextView textViewRight;
    RelativeLayout relativeLayout;
    ArrayList<String> listOfSuares = new ArrayList<>();
    ArrayList<Suares> ListOfSuare;
    MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(this,"jj",Toast.LENGTH_LONG).show();
        setContentView(R.layout.activity_main2);
        suares = findViewById(R.id.list_of_suarse);
        relativeLayout = findViewById(R.id.media_player);
        pause = findViewById(R.id.image_pause);
        play = findViewById(R.id.image_play);
        textViewRight = findViewById(R.id.text_time_right);
        final Intent intent = getIntent();
        MyUrls.setId(String.valueOf(intent.getIntExtra("recitations", 0)));
        StringRequest stringRequest = new StringRequest(Request.Method.GET, MyUrls.getSuras(),

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray jsonArray = jsonObject.getJSONArray("attachments");
                            ListOfSuare = new ArrayList<>();
                            for (int pos = 0; pos < jsonArray.length(); pos++) {
                                Suares suares = new Suares();
                                suares.setName(jsonArray.getJSONObject(pos).getString("title"));
                                ListOfSuare.add(suares);
                                listOfSuares.add(jsonArray.getJSONObject(pos).getString("url"));
                            }
                             adapter = new MyAdapter(Main2Activity.this,ListOfSuare);
                            suares.setAdapter(adapter);

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
        suares.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        setDefulte();
        ListOfSuare.get(i).setState(true);
        Thread thread = new Thread(new MyThread(i));
        thread.start();
        suares.setSelection(i);
        relativeLayout.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    private void setDefulte()
    {
        for (int pos = 0;pos<ListOfSuare.size();pos++)
        {
            ListOfSuare.get(pos).setState(false);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }

    private void playmedia(int i) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(listOfSuares.get(i));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

    }

    private class MyThread implements Runnable {
        private int postion;

        public MyThread(int pos) {
            this.postion = pos;
        }

        @Override
        public void run() {
            playmedia(postion);
        }
    }

    public void pause(View view) {
        mediaPlayer.pause();
        play.setVisibility(View.VISIBLE);
        pause.setVisibility(View.GONE);
    }

    public void play(View view) {
        mediaPlayer.start();
        play.setVisibility(View.GONE);
        pause.setVisibility(View.VISIBLE);
    }

    public void previous(View view) {
        if (s == 0)
            s = 114;
        suares.setSelection(s);
        suares.performItemClick(suares.getAdapter().getView(s - 1, null, null), s - 1, suares.getAdapter().getItemId(s - 1));
    }

    public void next(View view) {
        if (s == 113)
            s = -1;
        suares.setSelection(s);
        suares.performItemClick(suares.getAdapter().getView(s + 1, null, null), s + 1, suares.getAdapter().getItemId(s + 1));

    }
}
