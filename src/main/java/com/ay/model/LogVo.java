package com.ay.model;

import java.util.Date;
import java.util.Objects;

public class LogVo implements Cloneable{
    private int logid;
    private String aactions;
    private String logdate;
    private String tablename;
    public  Object clone() throws  CloneNotSupportedException{
        return super.clone();
    }

    @Override
    public String toString() {
        return "LogVo{" +
                "logid=" + logid +
                ", aactions='" + aactions + '\'' +
                ", logdate='" + logdate + '\'' +
                ", tablename='" + tablename + '\'' +
                '}';
    }

    public int getLogid() {
        return logid;
    }

    public void setLogid(int logid) {
        this.logid = logid;
    }

    public String getAactions() {
        return aactions;
    }

    public void setAactions(String aactions) {
        this.aactions = aactions;
    }

    public String getLogdate() {
        return logdate;
    }

    public void setLogdate(String logdate) {
        this.logdate = logdate;
    }

    public String getTablename() {
        return tablename;
    }

    public void setTablename(String tablename) {
        this.tablename = tablename;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LogVo)) return false;
        LogVo logVo = (LogVo) o;
        return logid == logVo.logid &&
                aactions.equals(logVo.aactions) &&
                logdate.equals(logVo.logdate) &&
                tablename.equals(logVo.tablename);
    }

    @Override
    public int hashCode() {
        return Objects.hash(logid, aactions, logdate, tablename);
    }

    public LogVo(int logid, String aactions, String logdate, String tablename) {
        this.logid = logid;
        this.aactions = aactions;
        this.logdate = logdate;
        this.tablename = tablename;
    }

    public LogVo() {
    }
}
