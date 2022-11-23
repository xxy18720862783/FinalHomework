package com.example.finalhomework;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditBookActivity extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS=10;
    public int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_book);

        position= this.getIntent().getIntExtra("position",0);
        String title=this.getIntent().getStringExtra("title");
        String author=this.getIntent().getStringExtra("author");
        String publisher=this.getIntent().getStringExtra("publisher");
        String pubdate=this.getIntent().getStringExtra("pubdate");

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        EditText editText1=findViewById(R.id.edit_title);
        EditText editText2=findViewById(R.id.edit_author);
        EditText editText3=findViewById(R.id.edit_publisher);
        EditText editText4=findViewById(R.id.edit_pubdate);

        if(null!=title)
        {
            editText1.setText(title);
            editText2.setText(author);
            editText3.setText(publisher);
            editText4.setText(pubdate);
        }

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button buttonOk=findViewById(R.id.edit_determine);
        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                bundle.putString("title",editText1.getText().toString());
                bundle.putString("author",editText2.getText().toString());
                bundle.putString("publisher",editText3.getText().toString());
                bundle.putString("pubdate",editText4.getText().toString());
                bundle.putInt("position",position);

                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                EditBookActivity.this.finish();
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button buttonNo=findViewById(R.id.edit_cancel);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditBookActivity.this.finish();
            }
        });
    }
}