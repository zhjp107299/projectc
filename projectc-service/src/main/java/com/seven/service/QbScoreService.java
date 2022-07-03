package com.seven.service;

import com.seven.pojo.QbBank;
import com.seven.pojo.QbC;
import com.seven.pojo.QbChapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 获取分数的服务层
 */
public interface QbScoreService {

    //获取自主命题
    String getIndepropoScore(int id);
    //存储C练习分数
    boolean scoreStoreC(int sId, String cString,String storeString);
    //存储题库练习分数
    boolean scoreStoreBank(int sId, String bankString,String storeString);
    //存储章节分数
    boolean scoreStoreChapter(int sId,String chapterIndexString,String storeString);


    boolean scoreStoreIndepropo(int sId, String indepropoString,String storeString);
    //获取各模拟C的分数
    QbC getCScore(Integer id);
    //获取各类练习的分数
    QbBank getBankScore(Integer id);
    //获取各章节的分数
    QbChapter getChapterScore(Integer id);
}
