package com.example.mohamedramadan.quran;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;

/**
 * Created by Mohamed Ramadan on 28/02/2018.
 */

public class MyAdapter extends BaseAdapter {

    private ArrayList<Suares> suares = new ArrayList<>();
    private Context mcontext;

    public MyAdapter(Context context, ArrayList<Suares> arrayList) {
        this.mcontext = context;
        this.suares = arrayList;
    }

    @Override
    public int getCount() {
        return suares.size();
    }

    @Override
    public Object getItem(int i) {
        return suares.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutView = inflater.inflate(R.layout.single_list_item, viewGroup, false);
        TextView textView = layoutView.findViewById(R.id.text_view_suares);
        textView.setText(suares.get(i).getName());
         ImageView play = layoutView.findViewById(R.id.pla);
         ImageView pause = layoutView.findViewById(R.id.pa);
        if (suares.get(i).isState()) {
            play.setVisibility(View.VISIBLE);
            pause.setVisibility(View.GONE);
        }
        return layoutView;
    }
}
