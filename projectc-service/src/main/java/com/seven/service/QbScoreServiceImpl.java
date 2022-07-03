package com.seven.service;

import com.seven.dao.QbScoreMapper;
import com.seven.pojo.QbBank;
import com.seven.pojo.QbC;
import com.seven.pojo.QbChapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

/**
 * 获取分数服务层的实现类
 */
public class QbScoreServiceImpl implements QbScoreService {

    @Autowired
    @Qualifier("qbScoreMapper")
    private QbScoreMapper qbScoreMapper;

    public void setQbScoreMapper(QbScoreMapper qbScoreMapper){
        this.qbScoreMapper = qbScoreMapper;
    }

    @Override
    public String getIndepropoScore(int id) {
        return qbScoreMapper.getIndepropoScore(id);
    }

    @Override
    public boolean scoreStoreC(int sId, String cString, String storeString) {
        return qbScoreMapper.scoreStoreC(sId,cString,storeString);
    }

    @Override
    public boolean scoreStoreBank(int sId, String bankString, String storeString) {
        return qbScoreMapper.scoreStoreBank(sId,bankString,storeString);
    }

    @Override
    public boolean scoreStoreChapter(int sId, String chapterIndexString, String storeString) {
        return qbScoreMapper.scoreStoreChapter(sId,chapterIndexString,storeString);
    }

    @Override
    public boolean scoreStoreIndepropo(int sId, String indepropoString, String storeString) {
        return qbScoreMapper.scoreStoreIndepropo(sId,indepropoString,storeString);
    }

    @Override
    public QbC getCScore(Integer id) {
        return qbScoreMapper.getCScore(id);
    }

    @Override
    public QbBank getBankScore(Integer id) {
        System.out.println(qbScoreMapper.getBankScore(id));
        return qbScoreMapper.getBankScore(id);
    }

    @Override
    public QbChapter getChapterScore(Integer id) {
        return qbScoreMapper.getChapterScore(id);
    }
}
