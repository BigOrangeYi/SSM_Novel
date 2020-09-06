package com.ay.model;

import com.ay.AOP.ANVoFlag;

import java.io.Serializable;
import java.util.Objects;

public class BookVo implements Serializable,Cloneable,Comparable<BookVo> {
    private int bookno;
    private int authorno;
    private String authorname;
    @ANVoFlag(name = "bookname",regular = "^[\\u4E00-\\u9FA5A-Za-z0-9]{1,20}$")
    private String bookname;
    private String booktype;
    private String bookimg;
    @ANVoFlag(name = "bookintro",regular = "^.{1,50}$")
    private String bookintro;
    private double bookpopularity;
    private int bookpublish;
    private String bookbirthday;
    private String bookposition;
    public  Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }
    public BookVo() {
    }

    public BookVo(int bookno) {
        this.bookno = bookno;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BookVo)) return false;
        BookVo bookVo = (BookVo) o;
        return getBookno() == bookVo.getBookno() &&
                getAuthorno() == bookVo.getAuthorno() &&
                Double.compare(bookVo.getBookpopularity(), getBookpopularity()) == 0 &&
                getBookpublish() == bookVo.getBookpublish() &&
                getAuthorname().equals(bookVo.getAuthorname()) &&
                getBookname().equals(bookVo.getBookname()) &&
                getBooktype().equals(bookVo.getBooktype()) &&
                getBookimg().equals(bookVo.getBookimg()) &&
                getBookintro().equals(bookVo.getBookintro()) &&
                getBookbirthday().equals(bookVo.getBookbirthday()) &&
                getBookposition().equals(bookVo.getBookposition());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBookno(), getAuthorno(), getAuthorname(), getBookname(), getBooktype(), getBookimg(), getBookintro(), getBookpopularity(), getBookpublish(), getBookbirthday(), getBookposition());
    }

    public String getBookposition() {
        return bookposition;
    }

    public void setBookposition(String bookposition) {
        this.bookposition = bookposition;
    }

    public BookVo(int bookno, int authorno, String authorname, String bookname, String booktype, String bookimg, String bookintro, double bookpopularity, int bookpublish, String bookbirthday, String bookposition) {
        this.bookno = bookno;
        this.authorno = authorno;
        this.authorname = authorname;
        this.bookname = bookname;
        this.booktype = booktype;
        this.bookimg = bookimg;
        this.bookintro = bookintro;
        this.bookpopularity = bookpopularity;
        this.bookpublish = bookpublish;
        this.bookbirthday = bookbirthday;
        this.bookposition = bookposition;
    }

    @Override
    public String toString() {
        return "BookVo{" +
                "bookno=" + bookno +
                ", authorno=" + authorno +
                ", authorname='" + authorname + '\'' +
                ", bookname='" + bookname + '\'' +
                ", booktype='" + booktype + '\'' +
                ", bookimg='" + bookimg + '\'' +
                ", bookintro='" + bookintro + '\'' +
                ", bookpopularity=" + bookpopularity +
                ", bookpublish='" + bookpublish + '\'' +
                ", bookbirthday='" + bookbirthday + '\'' +
                ", bookposition='" + bookposition + '\'' +
                '}';
    }

    public int getBookno() {
        return bookno;
    }

    public void setBookno(int bookno) {
        this.bookno = bookno;
    }

    public int getAuthorno() {
        return authorno;
    }

    public void setAuthorno(int authorno) {
        this.authorno = authorno;
    }

    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public String getBooktype() {
        return booktype;
    }

    public void setBooktype(String booktype) {
        this.booktype = booktype;
    }

    public String getBookimg() {
        return bookimg;
    }

    public void setBookimg(String bookimg) {
        this.bookimg = bookimg;
    }

    public String getBookintro() {
        return bookintro;
    }

    public void setBookintro(String bookintro) {
        this.bookintro = bookintro;
    }

    public double getBookpopularity() {
        return bookpopularity;
    }

    public void setBookpopularity(double bookpopularity) {
        this.bookpopularity = bookpopularity;
    }

    public int getBookpublish() {
        return bookpublish;
    }

    public void setBookpublish(int bookpublish) {
        this.bookpublish = bookpublish;
    }

    public String getBookbirthday() {
        return bookbirthday;
    }

    public void setBookbirthday(String bookbirthday) {
        this.bookbirthday = bookbirthday;
    }



    @Override
    public int compareTo(BookVo o) {
        return (int) (o.getBookpopularity()-this.getBookpopularity());
    }
}
