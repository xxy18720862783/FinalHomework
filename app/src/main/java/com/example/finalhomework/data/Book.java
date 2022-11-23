package com.example.finalhomework.data;

import java.io.Serializable;

public class Book implements Serializable {
    public String title;
    public int resourceId;
    public String author;
    public String publisher;
    public String pubdate;

    //构造函数，封面和标题即可确定一本书
    public Book(String title, int resourceId) {
        this.title=title;
        this.resourceId=resourceId;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title1){title=title1;}

    public int getCoverResourceId(){
        return resourceId;
    }
    public void setCoverResourceId(int coverResourceId){resourceId=coverResourceId;}

    public String getAuthor(){return author;}
    public void setAuthor(String author1){author=author1;}

    public String getPublisher(){return publisher;}
    public void setPublisher(String publisher1){publisher=publisher1;}

    public String getPubdate(){return pubdate;}
    public void setPubdate(String pubdate1){pubdate=pubdate1;}
}
