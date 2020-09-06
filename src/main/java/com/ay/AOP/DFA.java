package com.ay.AOP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class  DFA {
    public static Map sensitiveMap = null;

    public List<String> DFACheck(String s){
        String sensitive  = txt2String();
        Set<String> sensitiveSet = new HashSet<>();
        Collections.addAll(sensitiveSet,sensitive.split("\\r\\n"));
        addSensitiveMap(sensitiveSet);//构造DFA的敏感词Map,用sensitiveMap表示

        return checkSensitive(s);
    }

    //检查输入的参数！
    private static List<String> checkSensitive(String testString) {

        //去除无意义字符
        testString= testString.replaceAll("[^(a-zA-Z0-9\\u4e00-\\u9fa5)+]", "");

        char[] test = testString.toCharArray();
        int start = 0;//用于截取字符串的开始位置
        int senStart = 0;//敏感字符开始
        Map nowMap = sensitiveMap;
        boolean flag = false;
        List<String> senList = new ArrayList<>();
        for (int i = 0 ; i < test.length ; i ++){
            nowMap = (Map)nowMap.get(test[i]);//获取
            if (nowMap == null){//如果没获取到该字符下的数组，直接返回，开始下一轮循环
                if (flag){
                    i = senStart;
                    flag = false;
                }
                senStart = start = i+1;
                nowMap = sensitiveMap;
                continue;
            }else{//获取到Map,说明该字符为敏感词库中的数据
                flag = true;
                if ("1".equals(nowMap.get("isEnd"))){//如果该词结束，将该敏感词填入列表中，并初始化开始数
                    senList.add(testString.substring(start,i+1));
                    start = i+1;
                    flag = false;
                    nowMap = sensitiveMap;
                }
            }
        }
        return senList;
    }

    //构造DFA算法！将库中的敏感词构造成敏感词树
    private static void addSensitiveMap(Set<String> sensitiveSet) {
        sensitiveMap = new HashMap(sensitiveSet.size());
        String key = null;//当前敏感词
        Map nowMap = null;
        Map nowWorMap = null;
        Iterator<String> ite = sensitiveSet.iterator();
        while (ite.hasNext()){//遍历敏感词库
            key = ite.next();//当前敏感词
            nowMap = sensitiveMap;
            for (int i = 0;i<key.length();i++){//遍历该词的的每个字
                char keyChar = key.charAt(i);//该词的当前字
                Object wordMap = nowMap.get(keyChar);//当前字的子Map用nowMap.get(keyChar)表示

                if (wordMap != null){
                    nowMap = (Map) wordMap;//nowMap被赋值为子Map
                }else{
                    nowWorMap = new HashMap();
                    nowWorMap.put("isEnd","0");//isEnd=0表示该词未遍历结束，还有还有子Map没有被遍历
                    nowMap.put(keyChar,nowWorMap);
                   /* System.out.println("nowMap.hashCode:"+System.identityHashCode(nowMap));*/
                    nowMap = nowWorMap;
                    /*System.out.println("nowWorMap.hashCode:"+System.identityHashCode(nowWorMap));
                    System.out.println("nowMap.hashCode:"+System.identityHashCode(nowMap));*/
                }
                if (i == key.length() - 1){//词库中的该词遍历结束，设定结束标志isEnd=1
                    nowMap.put("isEnd","1");
                }
            }
        }
      /*  System.out.println(sensitiveMap);*/
    }

    //读库
    public static String txt2String(){
        File file = new File("D:\\dictionary.txt");
        StringBuilder result = new StringBuilder();
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = "";
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
                result.append(System.lineSeparator()+s);
            }
            br.close();
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
}
