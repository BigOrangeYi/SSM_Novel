package com.ay.controller;

import com.ay.model.CharacterVo;
import com.ay.service.impl.CharacterServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CharacterController {
    @Resource
    private CharacterServiceImpl characterService;

    @Resource
    private ObjectMapper mapper;

    @RequestMapping("querycharacter.do")//查询目录
    public void querycharacter(HttpServletResponse response, HttpSession session, @RequestBody Map maps) throws Exception {
        CharacterVo characterVo = new CharacterVo();
        if (maps.get("bookno") != null) {
            characterVo.setBookno(Integer.parseInt(maps.get("bookno").toString()));
            if (maps.get("chapterno") != null)
                characterVo.setCharacterno(Integer.parseInt(maps.get("chapterno").toString()));
            List<CharacterVo> characterVos = characterService.QueryAllCharacter(characterVo);

            mapper.writeValue(response.getWriter(), characterVos);
        }

    }

    @RequestMapping("FYQueryChapter.do")//分页查询章节
    public void FYQueryChapter(HttpServletResponse response, HttpSession session, @RequestBody Map maps) throws Exception {
        if (maps.get("bookno") != null && maps.get("bookno") != "" && maps.get("characterno") != null && maps.get("characterno") != "") {
            {

                int bookno = Integer.parseInt(maps.get("bookno").toString());
                int characterno = Integer.parseInt(maps.get("characterno").toString());
                Map map = new HashMap<>();
                if (maps.get("flag") != null && maps.get("flag") != "")
                    map.put("flag", Integer.parseInt(maps.get("flag").toString()));
                map.put("bookno", bookno);
                map.put("characterno", characterno);
                List<CharacterVo> list = characterService.FYQueryChapter(map);

                mapper.writeValue(response.getWriter(), list);
            }
        }
    }

    @RequestMapping("UpdateChapter.do")//修改章节
    public void UpdateChapter(HttpServletResponse response, HttpSession session, @RequestBody Map maps) throws Exception {
        CharacterVo characterVo = new CharacterVo();
        if (maps.get("characterno") != null && maps.get("characterno") != "") {
            if (maps.get("bookno") != null && maps.get("bookno") != "") {
                if (maps.get("charactername") != null && maps.get("charactername") != "") {
                    if (maps.get("characterdetail") != null && maps.get("characterdetail") != "") {
                        characterVo.setBookno(Integer.parseInt(maps.get("bookno").toString()));
                        characterVo.setCharacterno(Integer.parseInt(maps.get("characterno").toString()));
                        characterVo.setCharacterdetail(maps.get("characterdetail").toString());
                        characterVo.setCharactername(maps.get("charactername").toString());
                        try {
                            int temp = characterService.UpdateCharacter(characterVo);
                            if (temp == 1) {

                                mapper.writeValue(response.getWriter(), true);
                            } else {

                                mapper.writeValue(response.getWriter(), false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                            mapper.writeValue(response.getWriter(), false);
                        }
                    }
                }
            }
        }
    }

    @RequestMapping("CheckChapterno.do")//检查章节号是否重复
    public void CheckChapterno(HttpServletResponse response, HttpSession session, @RequestBody Map maps) throws Exception {
        if (maps.get("bookno") != null && maps.get("bookno") != "") {
            if (maps.get("characterno") != null && maps.get("characterno") != "") {
                try {
                    int result = characterService.CHeckChapterno(Integer.parseInt(maps.get("bookno").toString()), Integer.parseInt(maps.get("characterno").toString()));
                    if (result == 1) {

                        mapper.writeValue(response.getWriter(), true);
                    } else {

                        mapper.writeValue(response.getWriter(), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    mapper.writeValue(response.getWriter(), false);
                }


            }
        }


    }

    @RequestMapping("NewChapter.do")//新增章节
    public void NewChapter(HttpServletResponse response, HttpSession session, @RequestBody Map maps) throws Exception {
        CharacterVo characterVo = new CharacterVo();
        if (maps.get("characterno") != null && maps.get("characterno") != "") {
            if (maps.get("bookno") != null && maps.get("bookno") != "") {
                if (maps.get("charactername") != null && maps.get("charactername") != "") {
                    if (maps.get("characterdetail") != null && maps.get("characterdetail") != "") {
                        characterVo.setBookno(Integer.parseInt(maps.get("bookno").toString()));
                        characterVo.setCharacterno(Integer.parseInt(maps.get("characterno").toString()));
                        characterVo.setCharacterdetail(maps.get("characterdetail").toString());
                        characterVo.setCharactername(maps.get("charactername").toString());
                        try {
                            int temp = characterService.ADDCharacter(characterVo);
                            if (temp == 1) {

                                mapper.writeValue(response.getWriter(), true);
                            } else {

                                mapper.writeValue(response.getWriter(), false);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();

                            mapper.writeValue(response.getWriter(), false);
                        }
                    }
                }
            }
        }
    }

    @RequestMapping("DeleteChapter.do")//删除章节
    public void DeleteChapter(HttpServletResponse response, HttpSession session, @RequestBody Map maps) throws Exception {
        CharacterVo characterVo = new CharacterVo();
        if (maps.get("characterno") != null && maps.get("characterno") != "") {
            if (maps.get("bookno") != null && maps.get("bookno") != "") {
                characterVo.setBookno(Integer.parseInt(maps.get("bookno").toString()));
                characterVo.setCharacterno(Integer.parseInt(maps.get("characterno").toString()));
                try {
                    int temp = characterService.DeleteCharacter(characterVo);
                    if (temp == 1) {

                        mapper.writeValue(response.getWriter(), true);
                    } else {

                        mapper.writeValue(response.getWriter(), false);
                    }
                } catch (Exception e) {
                    e.printStackTrace();

                    mapper.writeValue(response.getWriter(), false);
                }
            }
        }
    }
}
