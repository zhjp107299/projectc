package com.seven.dao;

import com.seven.pojo.*;

import java.util.List;

/**
 * 向数据库内导入excel数据
 */

public interface ExcelInputDBMapper {

    //各题型导入数据库
    boolean choiceInputDB(List<Choice> choiceList);
    boolean gapFillingInputDB(List<GapFilling> gapFillingList);
    boolean judgeInputDB(List<Judge> judgeList);
    boolean programmeInputDB(List<Programme> programmeList);

    //导入自主命题
    boolean indeChoiceInputDB(List<Choice> choiceList);
    boolean indeGapFillingInputDB(List<GapFilling> gapFillingList);
    boolean indeJudgeInputDB(List<Judge> judgeList);
    boolean indeProgrammeInputDB(List<Programme> programmeList);


}
