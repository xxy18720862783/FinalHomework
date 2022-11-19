package com.example.finalhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ShowDetailActivity extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS = 777;
    public String title;
    public int recourseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        Toast.makeText(ShowDetailActivity.this,"显示",Toast.LENGTH_SHORT).show();

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        ImageView imageView=findViewById(R.id.showdetail_imageview);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        TextView textView=findViewById(R.id.showdetail_textview);
        title=this.getIntent().getStringExtra("title");
        recourseId=this.getIntent().getIntExtra("resourceId",0);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button=findViewById(R.id.showdetail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textView.setText(title);
                imageView.setImageResource(recourseId);
                setResult(RESULT_CODE_SUCCESS);
                ShowDetailActivity.this.finish();
            }
        });
    }
}