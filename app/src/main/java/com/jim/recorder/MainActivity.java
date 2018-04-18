package com.jim.recorder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.jim.recorder.abslistview.CommonAdapter;
import com.jim.recorder.abslistview.ViewHolder;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lv;
    ListView lb;
    ArrayList<Data> data1 = new ArrayList<>();
    ArrayList<Data> data2 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        lv = findViewById(R.id.content_lv);
        lb = findViewById(R.id.content_label);

        data1.add(new Data("name1","content1"));
        data1.add(new Data("name2","content2"));
        data1.add(new Data("name3","content3"));
        data1.add(new Data("name4","content4"));
        data1.add(new Data("name5","content5"));
        data1.add(new Data("name6","content6"));
        data1.add(new Data("name7","content7"));
        data1.add(new Data("name8","content8"));
        data1.add(new Data("name9","content9"));
        data1.add(new Data("name10","content10"));
        data1.add(new Data("name11","content11"));
        data1.add(new Data("name12","content12"));
        data1.add(new Data("name13","content13"));
        data1.add(new Data("name14","content14"));
        data1.add(new Data("name15","content15"));
        data1.add(new Data("name16","content16"));

        lv.setAdapter(new CommonAdapter<Data>(this, R.layout.layout_main_lv, data1) {
            @Override
            protected void convert(ViewHolder viewHolder, Data item, int position) {
                viewHolder.setText(R.id.lv_title, item.getName());
                viewHolder.setText(R.id.lv_content, item.getContent());
            }
        });

        lb.setAdapter(new CommonAdapter<Data>(this,R.layout.layout_main_lv, data1) {
            @Override
            protected void convert(ViewHolder viewHolder, Data item, int position) {
                viewHolder.setText(R.id.lv_title, item.getName());
                viewHolder.setText(R.id.lv_content, item.getContent());
            }
        });
    }

    class Data {
        String name;
        String content;

        public Data(String name, String content) {
            this.name = name;
            this.content = content;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

}
