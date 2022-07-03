package com.seven.service;

import com.seven.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IndePropoService {

    /**
     * 获取indepropo的文件名
     */
    List<IndepropoShow> getIndepropoShowList(String teacher);
    List<IndepropoShow> getIndepropoNameList(String teacher);
    String getIndePropoNameStatus(String indeName);

    //获取12道选择题
    List<Choice> getIndeChoices12(String indepropoName);
    //获取8道填空题
    List<GapFilling> getIndeGapFillings8(String indepropoName);
    //获取12道判断题
    List<Judge> getIndeJudges12(String indepropoName);
    //获取3道编程题
    List<Programme> getIndeProgrammes3(String indepropoName);

    //改变自主命题的状态
    boolean changeIndePropoStatus( String indeType, String newStatus,String indeName);
}
