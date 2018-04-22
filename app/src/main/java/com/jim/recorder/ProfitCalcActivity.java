package com.jim.recorder;

import android.graphics.drawable.ShapeDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class ProfitCalcActivity extends AppCompatActivity implements View.OnClickListener {

    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profit_calc);
        textView = findViewById(R.id.dev_text);

        textView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
    }
}
