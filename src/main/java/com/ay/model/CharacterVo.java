package com.ay.model;

import com.ay.AOP.ANVoFlag;

import java.io.Serializable;
import java.util.Objects;

public class CharacterVo implements Serializable,Cloneable {
    private int id;
    @ANVoFlag(name = "characterno",regular = "^[0-9]{1,10}$")
    private int characterno;
    private int bookno;
    @ANVoFlag(name = "characterdetail",regular = "^.{1,9999999999}$")
    private String characterdetail;
    @ANVoFlag(name = "charactername",regular = "^[\\u4E00-\\u9FA5A-Za-z0-9]{1,10}$")
    private String charactername;
    public  Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }

    public CharacterVo(int id, int characterno, int bookno, String characterdetail, String charactername) {
        this.id = id;
        this.characterno = characterno;
        this.bookno = bookno;
        this.characterdetail = characterdetail;
        this.charactername = charactername;
    }

    public int getCharacterno() {
        return characterno;
    }

    public void setCharacterno(int characterno) {
        this.characterno = characterno;
    }

    public int getBookno() {
        return bookno;
    }

    public void setBookno(int bookno) {
        this.bookno = bookno;
    }

    public String getCharacterdetail() {
        return characterdetail;
    }

    public void setCharacterdetail(String characterdetail) {
        this.characterdetail = characterdetail;
    }

    public String getCharactername() {
        return charactername;
    }

    public void setCharactername(String charactername) {
        this.charactername = charactername;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharacterVo)) return false;
        CharacterVo that = (CharacterVo) o;
        return getId() == that.getId() &&
                getCharacterno() == that.getCharacterno() &&
                getBookno() == that.getBookno() &&
                getCharacterdetail().equals(that.getCharacterdetail()) &&
                getCharactername().equals(that.getCharactername());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCharacterno(), getBookno(), getCharacterdetail(), getCharactername());
    }

    @Override
    public String toString() {
        return "CharacterVo{" +
                "id=" + id +
                ", characterno=" + characterno +
                ", bookno=" + bookno +
                ", characterdetail='" + characterdetail + '\'' +
                ", charactername='" + charactername + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CharacterVo() {
    }
}
