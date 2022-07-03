package com.seven.service;

import com.seven.dao.ExcelInputDBMapper;
import com.seven.dao.QbScoreMapper;
import com.seven.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class ExcelInputDBServiceImpl implements ExcelInputDBService{
    @Autowired
    @Qualifier("excelInputDBMapper")
    private ExcelInputDBMapper excelInputDBMapper;

    public void setExcelInputDBMapper(ExcelInputDBMapper excelInputDBMapper){
        this.excelInputDBMapper = excelInputDBMapper;
    }

    @Override
    public boolean choiceInputDB(List<Choice> choiceList) {
        return excelInputDBMapper.choiceInputDB(choiceList);
    }

    @Override
    public boolean gapFillingInputDB(List<GapFilling> gapFillingList) {
        return excelInputDBMapper.gapFillingInputDB(gapFillingList);
    }

    @Override
    public boolean judgeInputDB(List<Judge> judgeList) {
        return excelInputDBMapper.judgeInputDB(judgeList);
    }

    @Override
    public boolean programmeInputDB(List<Programme> programmeList) {
        return excelInputDBMapper.programmeInputDB(programmeList);
    }

    @Override
    public boolean indeChoiceInputDB(List<Choice> choiceList) {
        return excelInputDBMapper.indeChoiceInputDB(choiceList);
    }

    @Override
    public boolean indeGapFillingInputDB(List<GapFilling> gapFillingList) {
        return excelInputDBMapper.indeGapFillingInputDB(gapFillingList);
    }

    @Override
    public boolean indeJudgeInputDB(List<Judge> judgeList) {
        return excelInputDBMapper.indeJudgeInputDB(judgeList);
    }

    @Override
    public boolean indeProgrammeInputDB(List<Programme> programmeList) {
        return excelInputDBMapper.indeProgrammeInputDB(programmeList);
    }
}
