package com.example.myapp.view.motion_event;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapp.R;

import org.w3c.dom.Text;
import org.xmlpull.v1.XmlSerializer;

import java.util.ArrayList;
import java.util.List;

public class MotionEventActivity1 extends AppCompatActivity {

    private static final String TAG = "MotionEventActivity1";
    private HorizontalSrollViewEX mListContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_motion_event1);
        initView();
    }

    private void initView() {
        LayoutInflater inflater = getLayoutInflater();
        mListContainer = findViewById(R.id.container);
        final int screenWidth = 1080;
        final int screenHeight = 1920;
        for (int i = 0; i < 3; i++) {
            ViewGroup layout = (ViewGroup) inflater.inflate(R.layout.content_layout,mListContainer,false);
            layout.getLayoutParams().width = screenWidth;
            TextView textView = layout.findViewById(R.id.title);
            textView.setText("page: " + (i + 1));
            layout.setBackgroundColor(Color.rgb(255/(i+1),255/(i+1),0));
            createList(layout);
            mListContainer.addView(layout);
        }


    }

    private void createList(ViewGroup layout) {
        ListView listView = layout.findViewById(R.id.list);
        ArrayList<String> datas = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            datas.add("name: " + i);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.content_list_item,R.id.name,datas);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MotionEventActivity1.this,"click_item " + position,Toast.LENGTH_LONG).show();
            }
        });
    }
}
