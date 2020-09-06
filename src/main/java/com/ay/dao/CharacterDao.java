package com.ay.dao;

import com.ay.model.CharacterVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CharacterDao {
    List<CharacterVo> QueryAllCharacter(CharacterVo characterVo);//查询目录
    int UpdateCharacter(CharacterVo characterVo);//修改章节
    int DeleteCharacter(CharacterVo characterVo);//删除章节
    int ADDCharacter(CharacterVo characterVo);//增加章节
    List<CharacterVo> FYQueryChapter(Map map);//分页查询章节
    int CHeckChapterno(@Param("bookno")int bookno,@Param("characterno")int characterno);//检查章节号是否重复

}
