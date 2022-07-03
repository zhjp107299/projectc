package com.seven.service;
/*题型操作的业务层的实现*/
import com.seven.dao.QbMapper;
import com.seven.pojo.Choice;
import com.seven.pojo.GapFilling;
import com.seven.pojo.Programme;
import com.seven.pojo.Judge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class QbServiceImpl implements QbService {


    @Autowired
    @Qualifier("qbMapper")
    private QbMapper qbMapper;
    public void setQbMapper(QbMapper qbMapper){
        this.qbMapper = qbMapper;
    }


    @Override
    public int getQbCount() {
        return qbMapper.getQbCount();
    }

    //返回10/25/50选择题列表
    @Override
    public List<Choice> getQbChoiceNList(int count) {
        return qbMapper.getQbChoiceNList(count);
    }

    //返回10/25/50填空题列表
    @Override
    public List<GapFilling> getQbGapFillingNList(int count) {
        return qbMapper.getQbGapFillingNList(count);
    }

    //返回10/25/50判断题列表
    @Override
    public List<Judge> getQbJudgeListN(int count) {
        return qbMapper.getQbJudgeListN(count);
    }

    //返回10/25/50编程题列表
    @Override
    public List<Programme> getQbProgrammeListN(int count) {
        return qbMapper.getQbProgrammeListN(count);
    }

    @Override
    public List<Choice> getQbChoice_50(String type) {
        return qbMapper.getQbChoice_50(type);
    }

    @Override
    public List<GapFilling> getQbGapFilling_20(String type) {
        return qbMapper.getQbGapFilling_20(type);
    }

    @Override
    public List<Programme> getQbProgramme_4(String type) {
        return qbMapper.getQbProgramme_4(type);
    }
}
