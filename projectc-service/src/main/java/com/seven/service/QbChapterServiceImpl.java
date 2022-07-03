package com.seven.service;

import com.seven.dao.QbChapterMapper;
import com.seven.pojo.Choice;
import com.seven.pojo.GapFilling;
import com.seven.pojo.Judge;
import com.seven.pojo.Programme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;


public class QbChapterServiceImpl implements QbChapterService {


    @Autowired
    @Qualifier("qbChapterMapper")
    private QbChapterMapper qbChapterMapper;
    public void setQbChapterMapper(QbChapterMapper qbChapterMapper){
        this.qbChapterMapper = qbChapterMapper;
    }

    @Override
    public List<Choice> getQbChoices12(int index) {
        return qbChapterMapper.getQbChoices12(index);
    }

    @Override
    public List<GapFilling> getQbGapFillings8(int index) {
        return qbChapterMapper.getQbGapFillings8(index);
    }

    @Override
    public List<Judge> getQbJudges12(int index) {
        return qbChapterMapper.getQbJudges12(index);
    }

    @Override
    public List<Programme> getQbProgrammes3(int index) {
        return qbChapterMapper.getQbProgrammes3(index);
    }

}
