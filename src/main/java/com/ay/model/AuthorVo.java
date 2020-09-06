package com.ay.model;

import com.ay.AOP.ANVoFlag;

import java.io.Serializable;
import java.util.Objects;

public class AuthorVo implements Serializable ,Cloneable{


    private int authorno;

    @ANVoFlag(name = "authorname",regular="^[\\u4E00-\\u9FA5A-Za-z0-9]{2,4}$")
    private  String authorname;

    private  String phone;

    public AuthorVo(int authorno) {
        this.authorno = authorno;
    }

    public  Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AuthorVo)) return false;
        AuthorVo authorVo = (AuthorVo) o;
        return getAuthorno() == authorVo.getAuthorno() &&
                getAuthorname().equals(authorVo.getAuthorname()) &&
                getPhone().equals(authorVo.getPhone());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthorno(), getAuthorname(), getPhone());
    }

    @Override
    public String toString() {
        return "AuthorVo{" +
                "authorno=" + authorno +
                ", authorname='" + authorname + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public AuthorVo(int authorno, String phone, String authorname) {
        this.authorno = authorno;
        this.phone = phone;
        this.authorname = authorname;
    }

    public AuthorVo() {
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
