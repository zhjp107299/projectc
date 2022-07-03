package com.seven.dao;

import com.seven.pojo.QbBank;
import com.seven.pojo.QbC;
import com.seven.pojo.QbChapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 获取数据库中分数表的接口
 */
public interface QbScoreMapper {

    //获取自主命题
    String getIndepropoScore(int id);
    //获取各模拟C的分数
    QbC getCScore(Integer id);
    //获取各类练习的分数
    QbBank getBankScore(Integer id);
    //获取各章节的分数
    QbChapter getChapterScore(Integer id);


    boolean scoreStoreIndepropo(@Param("sId") int sId, @Param("indepropoString") String indepropoString, @Param("storeString") String storeString);
    //存储C练习分数
    boolean scoreStoreC(@Param("sId") int sId, @Param("cString") String cString, @Param("storeString") String storeString);
    //存储题库练习分数
    boolean scoreStoreBank(@Param("sId") int sId, @Param("bankString") String bankString, @Param("storeString") String storeString);
    //存储章节分数
    boolean scoreStoreChapter(@Param("sId") int sId, @Param("chapterIndexString") String chapterIndexString, @Param("storeString") String storeString);
}
