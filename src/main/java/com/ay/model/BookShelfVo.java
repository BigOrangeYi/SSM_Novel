package com.ay.model;

import java.io.Serializable;
import java.util.Objects;

public class BookShelfVo implements Serializable,Cloneable {
    private int shelfno;
    private int bookno;
    private String bookname;
    private String authorname;
    private String phone;
    private String bookimg;
    public  Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookShelfVo)) return false;
        BookShelfVo that = (BookShelfVo) o;
        return getShelfno() == that.getShelfno() &&
                getBookno() == that.getBookno() &&
                getBookname().equals(that.getBookname()) &&
                getAuthorname().equals(that.getAuthorname()) &&
                getPhone().equals(that.getPhone()) &&
                getBookimg().equals(that.getBookimg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getShelfno(), getBookno(), getBookname(), getAuthorname(), getPhone(), getBookimg());
    }

    @Override
    public String toString() {
        return "BookShelfVo{" +
                "shelfno=" + shelfno +
                ", bookno=" + bookno +
                ", bookname='" + bookname + '\'' +
                ", authorname='" + authorname + '\'' +
                ", phone='" + phone + '\'' +
                ", bookimg='" + bookimg + '\'' +
                '}';
    }

    public BookShelfVo(int shelfno, int bookno, String bookname, String authorname, String phone, String bookimg) {
        this.shelfno = shelfno;
        this.bookno = bookno;
        this.bookname = bookname;
        this.authorname = authorname;
        this.phone = phone;
        this.bookimg = bookimg;
    }

    public BookShelfVo() {
    }

    public int getShelfno() {
        return shelfno;
    }

    public void setShelfno(int shelfno) {
        this.shelfno = shelfno;
    }

    public int getBookno() {
        return bookno;
    }

    public void setBookno(int bookno) {
        this.bookno = bookno;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBookimg() {
        return bookimg;
    }

    public void setBookimg(String bookimg) {
        this.bookimg = bookimg;
    }
}
