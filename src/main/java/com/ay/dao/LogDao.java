package com.ay.dao;

import com.ay.model.LogVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LogDao {
    /*日志*/
    int AddLog(LogVo logVo);/*增加*/
    int DeleteLog(LogVo logVo);/*删除*/
    List<LogVo> SelectLog(LogVo logVo);//查询


}
