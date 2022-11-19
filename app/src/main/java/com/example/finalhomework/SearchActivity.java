package com.example.finalhomework;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.finalhomework.data.Book;


public class SearchActivity extends AppCompatActivity {
    public static final int RESULT_CODE_SUCCESS=666;

    //显示
    private final ActivityResultLauncher<Intent> showDetailLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==ShowDetailActivity.RESULT_CODE_SUCCESS){
                        Toast.makeText(SearchActivity.this,"back",Toast.LENGTH_SHORT).show();
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        EditText editText=findViewById(R.id.edit_search_title);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button1=findViewById(R.id.search_button_determine);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent();
                Bundle bundle=new Bundle();
                String title=editText.getText().toString();
                for (Book book: MainActivity.books) {
                    if(book.getTitle()==title){
                        int resourceId=book.getCoverResourceId();
                        Intent intentShow=new Intent(SearchActivity.this,ShowDetailActivity.class);
                        intentShow.putExtra("title",title);
                        intentShow.putExtra("resourceId",resourceId);
                        //startActivity(intentShow);
                        showDetailLauncher.launch(intentShow);
                        break;
                    }
                }
                bundle.putString("title",title);
                intent.putExtras(bundle);
                setResult(RESULT_CODE_SUCCESS,intent);
                SearchActivity.this.finish();
            }
        });
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button button2=findViewById(R.id.search_button_cancel);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchActivity.this.finish();
            }
        });
    }
}