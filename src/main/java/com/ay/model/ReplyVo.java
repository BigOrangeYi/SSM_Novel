package com.ay.model;

import java.io.Serializable;
import java.util.Objects;

public class ReplyVo implements Serializable,Cloneable {
    private int commentid;
    private  int bookno;
    private String commenttext;
    private String commentdate;
    private String readername;
    private String headimg;
    private int replyid;
    private String replyname;
    public  Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }

    public ReplyVo(int commentid) {
        this.commentid = commentid;
    }

    public int getCommentid() {
        return commentid;
    }

    public void setCommentid(int commentid) {
        this.commentid = commentid;
    }

    public int getBookno() {
        return bookno;
    }

    public void setBookno(int bookno) {
        this.bookno = bookno;
    }

    public String getCommenttext() {
        return commenttext;
    }

    public void setCommenttext(String commenttext) {
        this.commenttext = commenttext;
    }

    public String getCommentdate() {
        return commentdate;
    }

    public void setCommentdate(String commentdate) {
        this.commentdate = commentdate;
    }

    public String getReadername() {
        return readername;
    }

    public void setReadername(String readername) {
        this.readername = readername;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public int getReplyid() {
        return replyid;
    }

    public void setReplyid(int replyid) {
        this.replyid = replyid;
    }

    public String getReplyname() {
        return replyname;
    }

    public void setReplyname(String replyname) {
        this.replyname = replyname;
    }

    @Override
    public String toString() {
        return "ReplyVo{" +
                "commentid=" + commentid +
                ", bookno=" + bookno +
                ", commenttext='" + commenttext + '\'' +
                ", commentdate='" + commentdate + '\'' +
                ", readername='" + readername + '\'' +
                ", headimg='" + headimg + '\'' +
                ", replyid=" + replyid +
                ", replyname='" + replyname + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReplyVo)) return false;
        ReplyVo replyVo = (ReplyVo) o;
        return commentid == replyVo.commentid &&
                bookno == replyVo.bookno &&
                replyid == replyVo.replyid &&
                commenttext.equals(replyVo.commenttext) &&
                commentdate.equals(replyVo.commentdate) &&
                readername.equals(replyVo.readername) &&
                headimg.equals(replyVo.headimg) &&
                replyname.equals(replyVo.replyname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentid, bookno, commenttext, commentdate, readername, headimg, replyid, replyname);
    }

    public ReplyVo() {
    }

    public ReplyVo(int commentid, int bookno, String commenttext, String commentdate, String readername, String headimg, int replyid, String replyname) {
        this.commentid = commentid;
        this.bookno = bookno;
        this.commenttext = commenttext;
        this.commentdate = commentdate;
        this.readername = readername;
        this.headimg = headimg;
        this.replyid = replyid;
        this.replyname = replyname;
    }
}
