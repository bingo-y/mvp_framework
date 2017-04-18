package com.bingo.mvp_framework.io.db.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;

import java.util.Date;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by tuyx on 2017/4/18.
 * Description :
 */
@Entity
public class Note {
    @Id(autoincrement = true)
    Long id;

    String text;
    Date date;
    public Note(String text, Date date) {
        this.text = text;
        this.date = date;
    }
    @Generated(hash = 2071910743)
    public Note(Long id, String text, Date date) {
        this.id = id;
        this.text = text;
        this.date = date;
    }
    @Generated(hash = 1272611929)
    public Note() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getText() {
        return this.text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Date getDate() {
        return this.date;
    }
    public void setDate(Date date) {
        this.date = date;
    }

}
