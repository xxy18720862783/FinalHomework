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
    public int recourseId;
    public String title;
    public String author;
    public String publisher;
    public String pubdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) ImageView imageView=findViewById(R.id.imageview_cover);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView1=findViewById(R.id.textview1);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView2=findViewById(R.id.textview2);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView3=findViewById(R.id.textview3);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView textView4=findViewById(R.id.textview4);

        recourseId=this.getIntent().getIntExtra("resourceId",0);
        title=this.getIntent().getStringExtra("title");
        author=this.getIntent().getStringExtra("author");
        publisher=this.getIntent().getStringExtra("publisher");
        pubdate=this.getIntent().getStringExtra("pubdate");

        imageView.setImageResource(recourseId);
        textView1.setText("Title: "+title);
        textView2.setText("Author: "+author);
        textView3.setText("Publisher: "+publisher);
        textView4.setText("Pubdate: "+pubdate);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button=findViewById(R.id.showdetail_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(RESULT_CODE_SUCCESS);
                ShowDetailActivity.this.finish();
            }
        });
    }
}