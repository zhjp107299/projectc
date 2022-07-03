package com.seven.service;

import com.seven.dao.ExcelInputDBMapper;
import com.seven.dao.IndePropoMapper;
import com.seven.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class IndePropoServiceImpl implements IndePropoService{

    @Autowired
    @Qualifier("indePropoMapper")
    private IndePropoMapper indePropoMapper;

    public void setIndePropoMapper(IndePropoMapper indePropoMapper){
        this.indePropoMapper = indePropoMapper;
    }


    @Override
    public List<IndepropoShow> getIndepropoShowList(String teacher) {
        return indePropoMapper.getIndepropoShowList(teacher);
    }

    @Override
    public List<IndepropoShow> getIndepropoNameList(String teacher) {
        return indePropoMapper.getIndepropoNameList(teacher);
    }

    @Override
    public String getIndePropoNameStatus(String indeName) {
        return indePropoMapper.getIndePropoNameStatus(indeName);
    }

    @Override
    public List<Choice> getIndeChoices12(String indepropoName) {
        return indePropoMapper.getIndeChoices12(indepropoName);
    }

    @Override
    public List<GapFilling> getIndeGapFillings8(String indepropoName) {
        return indePropoMapper.getIndeGapFillings8(indepropoName);
    }

    @Override
    public List<Judge> getIndeJudges12(String indepropoName) {
        return indePropoMapper.getIndeJudges12(indepropoName);
    }

    @Override
    public List<Programme> getIndeProgrammes3(String indepropoName) {
        return indePropoMapper.getIndeProgrammes3(indepropoName);
    }

    @Override
    public boolean changeIndePropoStatus(String indeType, String newStatus,String indeName) {
        return indePropoMapper.changeIndePropoStatus(indeType,newStatus,indeName);
    }
}
