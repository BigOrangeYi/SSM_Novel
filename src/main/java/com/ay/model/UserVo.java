package com.ay.model;

import com.ay.AOP.ANVoFlag;
import com.ay.AOP.ANalterUser;

import java.io.Serializable;
import java.util.Objects;

public class UserVo implements Serializable,Cloneable {
    public  Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }
    private int id;
    @ANVoFlag(name = "phone",regular="^1[3|4|5|7|8]\\d{9}$")
    private String phone;

    @ANalterUser(name = "password",regular="^(?![a-zA-Z]{6,10}$)(?![a-z0-9]{6,10}$)(?![0-9A-Z]{6,10}$)[a-zA-Z0-9]{6,10}$")
   @ANVoFlag(name = "password",regular="^(?![a-zA-Z]{6,10}$)(?![a-z0-9]{6,10}$)(?![0-9A-Z]{6,10}$)[a-zA-Z0-9]{6,10}$")
   private String password;

    @ANalterUser(name = "readername",regular="^[\\u4E00-\\u9FA5A-Za-z0-9]{2,4}$")
    private String readername;

    private String headimg;

    public UserVo(int id) {
        this.id = id;
    }

    public UserVo(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    public UserVo(String phone, String password, String readername, String headimg) {
        this.phone = phone;
        this.password = password;
        this.readername = readername;
        this.headimg = headimg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserVo)) return false;
        UserVo userVo = (UserVo) o;
        return getId() == userVo.getId() &&
                getPhone().equals(userVo.getPhone()) &&
                getPassword().equals(userVo.getPassword()) &&
                getReadername().equals(userVo.getReadername()) &&
                getHeadimg().equals(userVo.getHeadimg());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getPhone(), getPassword(), getReadername(), getHeadimg());
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id=" + id +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", readername='" + readername + '\'' +
                ", headimg='" + headimg + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public UserVo(int id, String phone, String password, String readername, String headimg) {
        this.id = id;
        this.phone = phone;
        this.password = password;
        this.readername = readername;
        this.headimg = headimg;
    }

    public UserVo() {
    }
}
