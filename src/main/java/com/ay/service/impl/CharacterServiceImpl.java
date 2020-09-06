package com.ay.service.impl;

import com.ay.AOP.AN;
import com.ay.dao.CharacterDao;
import com.ay.model.AuthorVo;
import com.ay.model.CharacterVo;
import com.ay.service.CharacterService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
public class CharacterServiceImpl implements CharacterService {
    @Resource
    private  CharacterDao characterDao;
    @Resource
    private JedisCluster jedisCluster;
    @Resource
    private ObjectMapper mapper;
    public List<CharacterVo> QueryAllCharacter(CharacterVo characterVo)throws Exception{
        List<CharacterVo> characterVos=new ArrayList<>();
        List<CharacterVo>result=new ArrayList<>();
        characterVos=mapper.readValue(jedisCluster.zrange(characterVo.getBookno()+"",0,-1).toString(),characterVos.getClass());
        characterVos=mapper.convertValue(characterVos,new TypeReference<List<CharacterVo>>(){});
        if (characterVos.size()>0){
            System.out.println("Redis中有章节信息");
            return characterVos;
        }else {
            System.out.println("Redis中无章节信息");
        }

        System.out.println("开始查询MySQL");
        result=characterDao.QueryAllCharacter(characterVo);
        if (result.size()>0){
            System.out.println("MySQL查询成功");
            for (int i = 0; i <result.size() ; i++) {
                if (result.get(i).getCharacterdetail()!=null)
                jedisCluster.zadd(characterVo.getBookno()+"",result.get(i).getCharacterno(),mapper.writeValueAsString(result.get(i)));
            }
            System.out.println("Redis注入完成");
        }
        return result;
    }//查询章节
    @AN
    public int UpdateCharacter(CharacterVo characterVo)throws Exception{
        int num=characterDao.UpdateCharacter(characterVo);
        if (num==1) {
            System.out.println("MySQL修改章节完成");
            List<CharacterVo> characterVos = new ArrayList<>();
            List<CharacterVo> result = new ArrayList<>();
            characterVos = mapper.readValue(jedisCluster.zrange(characterVo.getBookno() + "", 0, -1).toString(), characterVos.getClass());
            characterVos = mapper.convertValue(characterVos, new TypeReference<List<CharacterVo>>() {
            });
            Iterator<CharacterVo> iterator = characterVos.iterator();
            while (iterator.hasNext()) {
                CharacterVo c = iterator.next();
                if (c.getCharacterno() == characterVo.getCharacterno()) {
                    System.out.println(jedisCluster.zrem(characterVo.getBookno() + "", mapper.writeValueAsString(c)));
                    jedisCluster.zadd(characterVo.getBookno() + "", characterVo.getCharacterno(), mapper.writeValueAsString(characterVo));
                    System.out.println("Redis修改章节完成");
                }
            }
        }
        return num;
    }//修改章节
    public  int DeleteCharacter(CharacterVo characterVo)throws Exception{

        int num= characterDao.DeleteCharacter(characterVo);
        if(num==1){
            System.out.println("MySQL删除章节成功");
            List<CharacterVo> characterVos=new ArrayList<>();
            List<CharacterVo>result=new ArrayList<>();
            characterVos=mapper.readValue(jedisCluster.zrange(characterVo.getBookno()+"",0,-1).toString(),characterVos.getClass());
            characterVos=mapper.convertValue(characterVos,new TypeReference<List<CharacterVo>>(){});
            Iterator<CharacterVo> iterator = characterVos.iterator();
            while (iterator.hasNext()) {
                CharacterVo c=iterator.next();
                if (c.getCharacterno()==characterVo.getCharacterno()){
                    if (jedisCluster.zrem(characterVo.getBookno()+"",mapper.writeValueAsString(c))==1){
                        System.out.println("Redis删除章节成功");
                    }
                }
            }
        }
       return num;
    }//删除章节
    @AN
    public  int ADDCharacter(CharacterVo characterVo)throws Exception{
        int num=characterDao.ADDCharacter(characterVo);

        if (num==1){
            if (jedisCluster.zadd(characterVo.getBookno()+"",characterVo.getCharacterno(),mapper.writeValueAsString(characterVo))==1){
                System.out.println("Redis增加章节成功");
            }
            else {
                System.out.println("Redis增加章节失败");
            }
        }



        return num;
    }//增加章节
    public List<CharacterVo> FYQueryChapter(Map map)throws Exception{
        List<CharacterVo> characterVos=new ArrayList<>();
        List<CharacterVo>result=new ArrayList<>();
        if (map.get("flag")!=null&&map.get("flag")!="") {
            if (Integer.parseInt(map.get("flag").toString()) == 1) {
                characterVos = mapper.readValue(jedisCluster.zrange(map.get("bookno").toString(), 0, -1).toString(), characterVos.getClass());
                characterVos = mapper.convertValue(characterVos, new TypeReference<List<CharacterVo>>() {
                });
                for (CharacterVo c : characterVos) {
                    if (c.getCharacterno() > Integer.parseInt(map.get("characterno").toString())) {
                        result.add(c);
                        System.out.println("Redis返回下一章");
                        return result;
                    }

                }
            } else if (Integer.parseInt(map.get("flag").toString()) == 0) {
                characterVos = mapper.readValue(jedisCluster.zrevrange(map.get("bookno").toString(), 0, -1).toString(), characterVos.getClass());
                characterVos = mapper.convertValue(characterVos, new TypeReference<List<CharacterVo>>() {
                });
                for (CharacterVo c : characterVos) {
                    if (c.getCharacterno() < Integer.parseInt(map.get("characterno").toString())) {
                        result.add(c);
                        System.out.println("Redis返回上一章");
                        return result;
                    }

                }
            }
        }else {
            characterVos=mapper.readValue(jedisCluster.zrange(map.get("bookno").toString(),0,-1).toString(),characterVos.getClass());
            characterVos=mapper.convertValue(characterVos,new TypeReference<List<CharacterVo>>(){});
            for (CharacterVo c:characterVos){
                if (c.getCharacterno()==Integer.parseInt(map.get("characterno").toString()))
                {
                    result.add(c);
                    System.out.println("Redis返回指定章");
                    return result;
                }
            }

        }

        return characterDao.FYQueryChapter(map);
    }//分页查询章节
    public int CHeckChapterno(int bookno,int characterno)throws Exception{
        List<CharacterVo> characterVos=new ArrayList<>();
        List<CharacterVo>result=new ArrayList<>();
        characterVos=mapper.readValue(jedisCluster.zrange(bookno+"",0,-1).toString(),characterVos.getClass());
        characterVos=mapper.convertValue(characterVos,new TypeReference<List<CharacterVo>>(){});
        Iterator<CharacterVo>iterator=characterVos.iterator();
        while (iterator.hasNext()){
            if (iterator.next().getCharacterno()==characterno){
                System.out.println("Redis检测到章节号重复");
                return 1;
            }
        }
        System.out.println("Redis没有检测到章节号重复，开始查询MySQL");
        return characterDao.CHeckChapterno(bookno,characterno);
    }//检查章节号是否重复

}
