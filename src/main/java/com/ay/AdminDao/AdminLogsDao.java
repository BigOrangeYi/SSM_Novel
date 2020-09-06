package com.ay.AdminDao;

import com.ay.model.LogVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface AdminLogsDao {
    List<LogVo> ASelectLogs(Map map);
    int ADeleteLogs(Map map);
}
