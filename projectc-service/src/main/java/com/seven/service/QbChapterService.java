package com.seven.service;

import com.seven.pojo.Choice;
import com.seven.pojo.GapFilling;
import com.seven.pojo.Judge;
import com.seven.pojo.Programme;

import java.util.List;

public interface QbChapterService {

    //获取题库总数
 /*   int getQbCount();*/

    //获取12道选择题
    List<Choice> getQbChoices12(int index);
    //获取8道填空题
    List<GapFilling> getQbGapFillings8(int index);
    //获取12道判断题
    List<Judge> getQbJudges12(int index);
    //获取3道编程题
    List<Programme> getQbProgrammes3(int index);

}
