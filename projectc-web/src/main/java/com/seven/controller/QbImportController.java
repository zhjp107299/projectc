package com.seven.controller;

import com.alibaba.fastjson.JSONArray;
import com.seven.dao.ExcelInputDBMapper;
import com.seven.pojo.*;
import com.seven.service.ExcelInputDBService;
import com.seven.service.QbService;
import com.seven.service.UserService;
import com.seven.utils.FetchInfoFromExcel;
import com.seven.utils.InfoMsg;
import jxl.read.biff.BiffException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@CrossOrigin
@ResponseBody
public class QbImportController {
    @Autowired
    @Qualifier("excelInputDBService")
    private ExcelInputDBService excelInputDBService;
    public void setExcelInputDBService(ExcelInputDBService excelInputDBService){
        this.excelInputDBService = excelInputDBService;
    }

    @RequestMapping(value = "/import_choice",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String qbChoiceImport(String fileType,String uploadFilePath) throws FileNotFoundException {
        FetchInfoFromExcel fetchInfoFromExcel = new FetchInfoFromExcel();
        //拿到Excel表的数据
        List<Choice> choiceBank = null;
        FileInputStream in = new FileInputStream(new File(uploadFilePath));
        System.out.println("文件路径：" + uploadFilePath);
        try {
            choiceBank = fetchInfoFromExcel.getChoiceBankByExcel(fileType,in);
            //去重
            for (int i=0;i<choiceBank.size();i++){
                for (int j=i+1;j<choiceBank.size();j++){
                    if (choiceBank.get(i).getTopic().equals(choiceBank.get(j).getTopic())){
                        choiceBank.remove(j);
                        i--;
                    }
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        //写入数据库
        boolean b = excelInputDBService.choiceInputDB(choiceBank);
        InfoMsg infoMsg = new InfoMsg();
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("文件上传成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("文件上传失败");
            return JSONArray.toJSONString(infoMsg);
        }

    }

    @RequestMapping(value = "/import_gapfilling",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String qbGapFillingImport(String fileType,String uploadFilePath) throws FileNotFoundException {
        FetchInfoFromExcel fetchInfoFromExcel = new FetchInfoFromExcel();
        //拿到Excel表的数据
        List<GapFilling> gapFillingBank = null;

        FileInputStream in = new FileInputStream(new File(uploadFilePath));
        System.out.println("文件路径：" + uploadFilePath);
        try {
            gapFillingBank = fetchInfoFromExcel.getGapFillingBankByExcel(fileType,in);
            //去重
            for (int i=0;i<gapFillingBank.size();i++){
                for (int j=i+1;j<gapFillingBank.size();j++){
                    if (gapFillingBank.get(i).getTopic().equals(gapFillingBank.get(j).getTopic())){
                        gapFillingBank.remove(j);
                        i--;
                    }
                }
            }
            System.out.println(gapFillingBank);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //写入数据库
        boolean b = excelInputDBService.gapFillingInputDB(gapFillingBank);
        InfoMsg infoMsg = new InfoMsg();
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("文件上传成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("文件上传失败");
            return JSONArray.toJSONString(infoMsg);
        }

    }

    @RequestMapping(value = "/import_judge",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String qbJudgeImport(String fileType,String uploadFilePath) throws FileNotFoundException {
        FetchInfoFromExcel fetchInfoFromExcel = new FetchInfoFromExcel();
        //拿到Excel表的数据
        List<Judge> judgeBank = null;
        FileInputStream in = new FileInputStream(new File(uploadFilePath));
        System.out.println("文件路径：" + uploadFilePath);
        try {
            judgeBank = fetchInfoFromExcel.getJudgeBankByExcel(fileType,in);
            //去重
            for (int i=0;i<judgeBank.size();i++){
                for (int j=i+1;j<judgeBank.size();j++){
                    if (judgeBank.get(i).getTopic().equals(judgeBank.get(j).getTopic())){
                        judgeBank.remove(j);
                        i--;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //写入数据库
        boolean b = excelInputDBService.judgeInputDB(judgeBank);
        InfoMsg infoMsg = new InfoMsg();
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("文件上传成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("文件上传失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }

    @RequestMapping(value = "/import_programme",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String qbProgrammeImport(String fileType,String uploadFilePath) throws FileNotFoundException {
        FetchInfoFromExcel fetchInfoFromExcel = new FetchInfoFromExcel();
        //拿到Excel表的数据
        List<Programme> programmeBank = null;
        FileInputStream in = new FileInputStream(new File(uploadFilePath));
        System.out.println("文件路径：" + uploadFilePath);
        try {
            programmeBank = fetchInfoFromExcel.getProgrammeBankByExcel(fileType,in);
            //去重
            for (int i=0;i<programmeBank.size();i++){
                for (int j=i+1;j<programmeBank.size();j++){
                    if (programmeBank.get(i).getTopic().equals(programmeBank.get(j).getTopic())){
                        programmeBank.remove(j);
                        i--;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //写入数据库
        boolean b = excelInputDBService.programmeInputDB(programmeBank);
        InfoMsg infoMsg = new InfoMsg();
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("文件上传成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("文件上传失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }


    /**
     * 导入自主命题
     */
     @RequestMapping(value = "/import_indepropo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
   public String indePropoImport(String fileType,String uploadFilePath) throws FileNotFoundException {
        FetchInfoFromExcel fetchInfoFromExcel = new FetchInfoFromExcel();

        List<Choice> indeChoiceList = new ArrayList<>();
        List<GapFilling> indeGapFillingList = new ArrayList<>();
        List<Judge> indeJudgeList = new ArrayList<>();
        List<Programme> indeProgrammeList = new ArrayList<>();
        //拿到Excel表的数据
        IndePropo indePropo = null;
        FileInputStream in = new FileInputStream(new File(uploadFilePath));
        System.out.println("文件路径：" + uploadFilePath);
        try {
            indePropo = fetchInfoFromExcel.getIndePropoByExcel(fileType,in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取命题数据并存入list
         Set<Map.Entry<String, Choice>> entriesChoice = indePropo.getMapChoice().entrySet();
         for (Map.Entry<String, Choice> entry : entriesChoice) {
             indeChoiceList.add(entry.getValue());
         }
         Set<Map.Entry<String, GapFilling>> entriesGapFilling = indePropo.getMapGapFilling().entrySet();
         for (Map.Entry<String, GapFilling> entry : entriesGapFilling) {
             indeGapFillingList.add(entry.getValue());
         }
         Set<Map.Entry<String, Judge>> entriesJudge = indePropo.getMapJudge().entrySet();
         for (Map.Entry<String, Judge> entry : entriesJudge) {
             indeJudgeList.add(entry.getValue());
         }
         Set<Map.Entry<String, Programme>> entriesProgramme = indePropo.getMapProgramme().entrySet();
         for (Map.Entry<String, Programme> entry : entriesProgramme) {
             indeProgrammeList.add(entry.getValue());
         }


        //写入数据库
        InfoMsg infoMsg = new InfoMsg();
        if (excelInputDBService.indeChoiceInputDB(indeChoiceList) && excelInputDBService.indeGapFillingInputDB(indeGapFillingList)
                && excelInputDBService.indeJudgeInputDB(indeJudgeList) && excelInputDBService.indeProgrammeInputDB(indeProgrammeList)){
            infoMsg.setCode(200);
            infoMsg.setMsg("文件上传成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("文件上传失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }


}
