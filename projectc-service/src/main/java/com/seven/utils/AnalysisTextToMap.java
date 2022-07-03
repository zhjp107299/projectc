package com.seven.utils;

import com.mysql.cj.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 将学生成绩表中的数据进行解析
 */
public class AnalysisTextToMap {
        public static Map<String,Integer> analysisTextToMap(String scoreExerciseN){
            Map<String,Integer> map = new HashMap<>();
            if (StringUtils.isNullOrEmpty(scoreExerciseN)){
                return map;
            }

            String[] split1 = scoreExerciseN.split(";");

            for (int i=0;i<split1.length;i++){
                String[] split2 = split1[i].split(",");

                String s="";
                int q = 0;
                if (split2[0].startsWith("[")){                     //这里判断的前两种情况估计是
                    s = split2[0].substring(5);                      //我存入List后打印出来看到的然后写的，
                    q = Integer.parseInt(split2[1].substring(6));    //然后遍历数组并不会有前两种情况
                }else if (split2[0].startsWith("]")){
                    s="";
                    q=0;
                }else {
                    s = split2[0].substring(4);
                    q = Integer.parseInt(split2[1].substring(6));
                }
                map.put(s,q);
            }
            return map;
        }

    public static Map<String,Integer> analysisTextToMapI(String scoreExerciseN){
        Map<String,Integer> map = new HashMap<>();
        if (StringUtils.isNullOrEmpty(scoreExerciseN)){
            return map;
        }

        String[] split1 = scoreExerciseN.split(";");

        for (int i=0;i<split1.length;i++){
            String[] split2 = split1[i].split(",");

            String s="";
            int q = 0;
            if (split2[0].startsWith("[")){                     //这里判断的前两种情况估计是
                s = split2[0].substring(5);                      //我存入List后打印出来看到的然后写的，
                q = Integer.parseInt(split2[2].substring(6));    //然后遍历数组并不会有前两种情况
            }else if (split2[0].startsWith("]")){
                s="";
                q=0;
            }else {
                s = split2[0].substring(4);
                q = Integer.parseInt(split2[2].substring(6));
            }
            map.put(s,q);
        }
        return map;
    }

    public static List<String> analysisTextToMapIName(String scoreExerciseN){
        List<String> nameList = new ArrayList<>();
        if (StringUtils.isNullOrEmpty(scoreExerciseN)){
            return nameList;
        }
        String name="";
        String[] split1 = scoreExerciseN.split(";");

        for (int i=0;i<split1.length;i++) {
            String[] split2 = split1[i].split(",");

            int index = split2[1].indexOf("-");
            name = split2[1].substring(index+1);
            nameList.add(name);
        }
        return nameList;
    }
}
