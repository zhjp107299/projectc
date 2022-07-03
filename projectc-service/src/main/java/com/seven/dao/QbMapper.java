package com.seven.dao;

import com.seven.pojo.Choice;
import com.seven.pojo.GapFilling;
import com.seven.pojo.Judge;
import com.seven.pojo.Programme;

import java.util.List;

/*关于题型的所有操作*/
public interface QbMapper {


    //获取题库总数
    int getQbCount();

    //随机获取10/25/50选择题练习
    List<Choice> getQbChoiceNList(int count);
    //随机获取10/25/50填空题练习
    List<GapFilling> getQbGapFillingNList(int count);
    //随机获取10/25/50判断题练习
    List<Judge> getQbJudgeListN(int count);
    //随机获取10/25/50编程题练习
    List<Programme> getQbProgrammeListN(int count);


    //c模块
    List<Choice> getQbChoice_50(String type);
    List<GapFilling> getQbGapFilling_20(String type);
    List<Programme> getQbProgramme_4(String type);
}
