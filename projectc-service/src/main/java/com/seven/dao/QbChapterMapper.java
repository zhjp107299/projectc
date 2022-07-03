package com.seven.dao;

import com.seven.pojo.Choice;
import com.seven.pojo.GapFilling;
import com.seven.pojo.Programme;
import com.seven.pojo.Judge;

import java.util.List;

/**
 * 关于一张测试卷的题型安排(章节测试)
 */

public interface QbChapterMapper {
    //获取12道选择题
    List<Choice> getQbChoices12(int index);
    //获取8道填空题
    List<GapFilling> getQbGapFillings8(int index);
    //获取12道判断题
    List<Judge> getQbJudges12(int index);
    //获取3道编程题
    List<Programme> getQbProgrammes3(int index);

}
