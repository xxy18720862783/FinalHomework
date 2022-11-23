package com.example.finalhomework;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.core.view.MenuItemCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.finalhomework.data.Book;
import com.example.finalhomework.data.DataSaver;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    public static final int MENU_ID_ADD=1;
    public static final int MENU_ID_UPDATE=2;
    public static final int MENU_ID_DELETE=3;
    public static ArrayList<Book> books;//书本列表
    private MyAdapater myAdapater;
    //增加
    private final ActivityResultLauncher<Intent> addDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()== EditBookActivity.RESULT_CODE_SUCCESS)
                    {
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        String author=bundle.getString("author");
                        String publisher=bundle.getString("publisher");
                        String pubdate=bundle.getString("pubdate");
                        int position=bundle.getInt("position");
                        Book book=new Book(title,R.drawable.book_no_name);
                        book.setAuthor(author);
                        book.setPublisher(publisher);
                        book.setPubdate(pubdate);
                        books.add(book);
                        new DataSaver().Save(this,books);
                        myAdapater.notifyItemInserted(position);
                    }
                }
            });
    //修改
    private final ActivityResultLauncher<Intent> updateDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==EditBookActivity.RESULT_CODE_SUCCESS) {
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        String author=bundle.getString("author");
                        String publisher=bundle.getString("publisher");
                        String pubdate=bundle.getString("pubdate");
                        int position=bundle.getInt("position");
                        books.get(position).setTitle(title);
                        books.get(position).setAuthor(author);
                        books.get(position).setPublisher(publisher);
                        books.get(position).setPubdate(pubdate);
                        new DataSaver().Save(this,books);
                        myAdapater.notifyItemChanged(position);
                    }
                }
            });
    //显示
    private final ActivityResultLauncher<Intent> showDetailLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(null!=result){
                    Intent intent=result.getData();
                    if(result.getResultCode()==ShowDetailActivity.RESULT_CODE_SUCCESS){
                    }
                }
            });
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);//setContentView之前调用，否则报错
        setContentView(R.layout.activity_main);
        //工具栏
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        // 设置ToolBar标题
        toolbar.setTitle("ToolBar");
        // 设置ToolBar副标题
        toolbar.setSubtitle("this is toolbar");
        // 添加Toolbar
        setSupportActionBar(toolbar);
        //侧滑抽屉
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        //drawer.setDrawerListener(toggle);
        toggle.syncState();
        //recyclerView
        RecyclerView recyclerView=findViewById(R.id.recyclerview_main);
        //布局
        LinearLayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        //数据数组初始化
        books=new ArrayList<>();
        //数据载入
        DataSaver dataSaver=new DataSaver();
        books=dataSaver.Load(this);
        //构造一些数据
        Book book1=new Book("软件项目管理案例教程（第4版）", R.drawable.book_2);
        book1.setAuthor("张三");book1.setPublisher("中信出版社");book1.setPubdate("2022-1");
        Book book2=new Book("信息安全数学基础（第2版）", R.drawable.book_1);
        book2.setAuthor("张三");book2.setPublisher("中信出版社");book2.setPubdate("2022-1");
        Book book3=new Book("创新工程实践", R.drawable.book_no_name);
        book3.setAuthor("张三");book3.setPublisher("中信出版社");book3.setPubdate("2022-1");
        if(books.size()==0) {
            books.add(book1);
            books.add(book2);
            books.add(book3);
        }
        myAdapater=new MyAdapater(books);
        recyclerView.setAdapter(myAdapater);
        //悬浮加法按钮
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentAdd=new Intent(MainActivity.this,EditBookActivity.class);
                intentAdd.putExtra("position",books.size());
                addDataLauncher.launch(intentAdd);
            }
        });
    }

    public void goToShowdetail(int position){
        Intent intentShow=new Intent(MainActivity.this,ShowDetailActivity.class);
        intentShow.putExtra("resourceId",books.get(position).getCoverResourceId());
        intentShow.putExtra("title",books.get(position).getTitle());
        intentShow.putExtra("author",books.get(position).getAuthor());
        intentShow.putExtra("publisher",books.get(position).getPublisher());
        intentShow.putExtra("pubdate",books.get(position).getPubdate());
        showDetailLauncher.launch(intentShow);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu,menu);
        // 设置SearchView
        MenuItem menuItem = menu.findItem(R.id.toolbar_search);
        if (menuItem != null) {
            // 获取到SearchView（必须在xml item中设置app:actionViewClass="android.widget.SearchView"）
            SearchView searchView = (SearchView) menuItem.getActionView();
            // 在右侧添加提交按钮
            searchView.setSubmitButtonEnabled(true);
            //打开
            searchView.setOnSearchClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Toast.makeText(MainActivity.this,"打开搜索框",Toast.LENGTH_LONG).show();
                }
            });
            //文本监听
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String s) {
                    //Toast.makeText(MainActivity.this, "Submit---提交", Toast.LENGTH_SHORT).show();
                    int i=0;
                    for(;i<books.size();i++){
                        if(books.get(i).getTitle().equals(s)){
                            Toast.makeText(MainActivity.this, "已经找到对应书籍", Toast.LENGTH_SHORT).show();
                            goToShowdetail(i);
                            break;
                        }
                    }
                    if(i==books.size()){
                        Toast.makeText(MainActivity.this, "库中没有此书", Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String s) {
                    return false;
                }
            });
            //关闭
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    Toast.makeText(MainActivity.this, "关闭搜索框", Toast.LENGTH_SHORT).show();
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.toolbar_all://选择书架
                Toast.makeText(MainActivity.this,"书架",Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_unfold:
                Toast.makeText(MainActivity.this,"展开",Toast.LENGTH_SHORT).show();
                break;
            case R.id.toolbar_search://搜索
                break;
            default:
                Toast.makeText(MainActivity.this,"更多",Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_main);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    //点击"增加"或"删除"的相应函数
    @SuppressLint("ShowToast")
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case MENU_ID_ADD://增加(在相同位置增加一本一样的书)
                Book book=new Book(books.get(item.getOrder()).getTitle(),books.get(item.getOrder()).getCoverResourceId());
                book.setAuthor(books.get(item.getOrder()).getAuthor());book.setPublisher(books.get(item.getOrder()).getPublisher());book.setPubdate(books.get(item.getOrder()).getPubdate());
                books.add(book);
                new DataSaver().Save(MainActivity.this,books);
                myAdapater.notifyItemInserted(item.getOrder());
                break;
            case MENU_ID_UPDATE://修改
                Intent intentUpdate=new Intent(this,EditBookActivity.class);
                intentUpdate.putExtra("position",item.getOrder());
                intentUpdate.putExtra("title",books.get(item.getOrder()).getTitle());
                intentUpdate.putExtra("author",books.get(item.getOrder()).getAuthor());
                intentUpdate.putExtra("publisher",books.get(item.getOrder()).getPublisher());
                intentUpdate.putExtra("pubdate",books.get(item.getOrder()).getPubdate());
                updateDataLauncher.launch(intentUpdate);
                break;
            case MENU_ID_DELETE://删除
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.string_confirmation)
                        .setMessage(R.string.string_sure_to_delete)
                        .setNegativeButton(R.string.string_no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        }).setPositiveButton(R.string.string_yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                books.remove(item.getOrder());
                                new DataSaver().Save(MainActivity.this,books);
                                myAdapater.notifyItemRemoved(item.getOrder());
                            }
                        }).create();
                alertDialog.show();
                break;
            default:
                break;
        }
        return super.onContextItemSelected(item);
    }

    //适配器MyAdapater类
    public class MyAdapater extends RecyclerView.Adapter<MyAdapater.ViewHolder> {
        private ArrayList<Book> localDataset;

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
            private final ImageView imageViewCover;
            private final TextView textView1;
            private final TextView textView2;
            private final TextView textView3;

            public ViewHolder(View view){
                super(view);
                imageViewCover=view.findViewById(R.id.imageview_cover);
                textView1=view.findViewById(R.id.textview_title);
                textView2=view.findViewById(R.id.textview_author_publisher);
                textView3=view.findViewById(R.id.textview_pubdate);
                //长按事件监听者
                view.setOnCreateContextMenuListener(this);
            }

            public ImageView getImageViewCover(){return imageViewCover;}
            public TextView getTextView1(){return textView1;}
            public TextView getTextView2(){return textView2;}
            public TextView getTextView3(){return textView3;}

            //创建上下文菜单
            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                contextMenu.add(0,MENU_ID_ADD,getAdapterPosition(),"增加");
                contextMenu.add(0,MENU_ID_UPDATE,getAdapterPosition(),"修改");
                contextMenu.add(0,MENU_ID_DELETE,getAdapterPosition(),"删除");
            }
        }

        //构造函数
        public MyAdapater(ArrayList<Book> dataSet){
            localDataset=dataSet;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, @SuppressLint("RecyclerView") int position) {
            viewHolder.getImageViewCover().setImageResource(localDataset.get(position).getCoverResourceId());
            viewHolder.getTextView1().setText(localDataset.get(position).getTitle());
            viewHolder.getTextView2().setText(localDataset.get(position).getAuthor()+","+localDataset.get(position).getPublisher());
            viewHolder.getTextView3().setText(localDataset.get(position).getPubdate());

            //点击recyclerview里面的一个项目
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToShowdetail(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return localDataset.size();
        }

        //重载函数使得长按无效区无效,不弹出窗口
        @Override
        public void onViewRecycled(@NonNull ViewHolder viewHolder) {
            viewHolder.itemView.setOnLongClickListener(null);
            super.onViewRecycled(viewHolder);
        }
    }
}
