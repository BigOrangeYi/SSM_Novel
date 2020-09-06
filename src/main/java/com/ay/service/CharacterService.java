package com.ay.service;

import com.ay.model.CharacterVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CharacterService {
    List<CharacterVo> QueryAllCharacter(CharacterVo characterVo)throws Exception;//查询章节根据书号
    int UpdateCharacter(CharacterVo characterVo)throws Exception;//修改章节
    int DeleteCharacter(CharacterVo characterVo)throws Exception;//删除章节
    int ADDCharacter(CharacterVo characterVo)throws Exception;//增加章节
    List<CharacterVo> FYQueryChapter(Map map)throws Exception;//分页查询章节
    int CHeckChapterno(int bookno, int characterno)throws Exception;//检查章节号是否重复
}
