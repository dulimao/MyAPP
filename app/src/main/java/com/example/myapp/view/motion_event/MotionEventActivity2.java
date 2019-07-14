package com.example.myapp.view.motion_event;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import java.util.ArrayList;

public class MotionEventActivity2 extends AppCompatActivity {

    private static final String TAG = "MotionEventActivity1";
    private HorizontalScrollViewEX1 mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event2);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = findViewById(R.id.container);
        final int screenWidth = 1080;
        final int screenHeight = 1920;
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout2,mListContainer,false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = layout.findViewById(R.id.title);
            textView.setText("内部拦截法：page: " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255/(i+6),255/(i+1),0));
            createList(layout);
            mListContainer.addView(layout);
        }


    }

    private void createList(ViewGroup layout) {
        ListViewEX listView = layout.findViewById(R.id.list);
        listView.setmHorizontalScrollViewEX1(mListContainer);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("name: " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MotionEventActivity2.this,"click_item " + position,Toast.LENGTH_LONG).show();
            }
        });
    }
}
