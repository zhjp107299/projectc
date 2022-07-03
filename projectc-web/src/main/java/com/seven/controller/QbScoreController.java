package com.seven.controller;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.seven.pojo.*;
import com.seven.service.QbScoreService;
import com.seven.utils.AnalysisTextToMap;
import com.seven.utils.InfoMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@ResponseBody
public class QbScoreController {

    @Autowired
    @Qualifier("qbScoreServiceImpl")
    private QbScoreService qbScoreService;

    public void setQbScoreService(QbScoreService qbScoreService) {
        this.qbScoreService = qbScoreService;
    }


    /**
     * 获取自主命题的分数
     */
    @RequestMapping(value = "/score_indepropo", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getIndepropoScore(String id){
        com.seven.utils.ResponseBody<Map<String,Map<String,Integer>>> responseBody = new com.seven.utils.ResponseBody<>();
        InfoMsg infoMsg = new InfoMsg();
        Map<String,Map<String,Integer>> map = new HashMap<>();

        if (StringUtils.isNullOrEmpty(qbScoreService.getIndepropoScore(Integer.parseInt(id)))){
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
        //获取章节成绩并转换
        String indepropoScore = qbScoreService.getIndepropoScore(Integer.parseInt(id));

        Map<String, Integer> stringIntegerMapI = AnalysisTextToMap.analysisTextToMapI(indepropoScore);

        map.put("scoreIndepropo",stringIntegerMapI);

        if (map!=null){
            infoMsg.setCode(200);
            infoMsg.setMsg("获取自主命题成绩成功");
            responseBody.setInfo(infoMsg);
            responseBody.setData(map);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }

    /**
     * 获取各章节、题库练习、模拟C的分数
     */
    @RequestMapping(value = "/score_c", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getCScore(String id){
        com.seven.utils.ResponseBody<Map<String,Map<String,Integer>>> responseBody = new com.seven.utils.ResponseBody<>();
        InfoMsg infoMsg = new InfoMsg();
        Map<String,Map<String,Integer>> map = new HashMap<>();

        if (qbScoreService.getCScore(Integer.parseInt(id))==null){
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
        //获取章节成绩并转换
        QbC cScore = qbScoreService.getCScore(Integer.parseInt(id));

        String scoreCSimple = cScore.getScoreCSimple();
        String scoreCMiddle = cScore.getScoreCMiddle();
        String scoreCHard = cScore.getScoreCHard();

        Map<String, Integer> stringIntegerMapS = AnalysisTextToMap.analysisTextToMap(scoreCSimple);
        Map<String, Integer> stringIntegerMapM = AnalysisTextToMap.analysisTextToMap(scoreCMiddle);
        Map<String, Integer> stringIntegerMapH = AnalysisTextToMap.analysisTextToMap(scoreCHard);

        map.put("scoreCSimple",stringIntegerMapS);
        map.put("scoreCMiddle",stringIntegerMapM);
        map.put("scoreCHard",stringIntegerMapH);

        if (map!=null){
            infoMsg.setCode(200);
            infoMsg.setMsg("获取C成绩成功");
            responseBody.setInfo(infoMsg);
            responseBody.setData(map);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }

    @RequestMapping(value = "/score_bank", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getBankScore(String id){
        com.seven.utils.ResponseBody<Map<String,Map<String,Integer>>> responseBody = new com.seven.utils.ResponseBody<>();
        InfoMsg infoMsg = new InfoMsg();
        Map<String,Map<String,Integer>> map = new HashMap<>();
        if (qbScoreService.getBankScore(Integer.parseInt(id))==null){
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
        //获取章节成绩并转换
        QbBank bankScore = qbScoreService.getBankScore(Integer.parseInt(id));

        String scoreChoice_10 = bankScore.getScoreChoice10();
        String scoreGapFilling_10 = bankScore.getScoreGapFilling10();
        String scoreJudge_10 = bankScore.getScoreJudge10();
        String scoreProgramme_5 = bankScore.getScoreProgramme5();
        Map<String, Integer> stringIntegerMapC_10 = AnalysisTextToMap.analysisTextToMap(scoreChoice_10);
        Map<String, Integer> stringIntegerMapG_10 = AnalysisTextToMap.analysisTextToMap(scoreGapFilling_10);
        Map<String, Integer> stringIntegerMapJ_10 = AnalysisTextToMap.analysisTextToMap(scoreJudge_10);
        Map<String, Integer> stringIntegerMapP_5 = AnalysisTextToMap.analysisTextToMap(scoreProgramme_5);
        map.put("scoreBankChoice10",stringIntegerMapC_10);
        map.put("scoreBankGapFilling10",stringIntegerMapG_10);
        map.put("scoreBankJudge10",stringIntegerMapJ_10);
        map.put("scoreBankProgramme5",stringIntegerMapP_5);


        String scoreChoice_25 = bankScore.getScoreChoice25();
        String scoreGapFilling_25 = bankScore.getScoreGapFilling25();
        String scoreJudge_25 = bankScore.getScoreJudge25();
        String scoreProgramme_10 = bankScore.getScoreProgramme5();
        Map<String, Integer> stringIntegerMapC_25 = AnalysisTextToMap.analysisTextToMap(scoreChoice_25);
        Map<String, Integer> stringIntegerMapG_25 = AnalysisTextToMap.analysisTextToMap(scoreGapFilling_25);
        Map<String, Integer> stringIntegerMapJ_25 = AnalysisTextToMap.analysisTextToMap(scoreJudge_25);
        Map<String, Integer> stringIntegerMapP_10 = AnalysisTextToMap.analysisTextToMap(scoreProgramme_10);
        map.put("scoreBankChoice25",stringIntegerMapC_25);
        map.put("scoreBankGapFilling25",stringIntegerMapG_25);
        map.put("scoreBankJudge25",stringIntegerMapJ_25);
        map.put("scoreBankProgramme10",stringIntegerMapP_10);


        String scoreChoice_50 = bankScore.getScoreChoice50();
        String scoreGapFilling_50 = bankScore.getScoreGapFilling50();
        String scoreJudge_50 = bankScore.getScoreJudge50();
        String scoreProgramme_20 = bankScore.getScoreProgramme20();
        Map<String, Integer> stringIntegerMapC_50 = AnalysisTextToMap.analysisTextToMap(scoreChoice_50);
        Map<String, Integer> stringIntegerMapG_50 = AnalysisTextToMap.analysisTextToMap(scoreGapFilling_50);
        Map<String, Integer> stringIntegerMapJ_50 = AnalysisTextToMap.analysisTextToMap(scoreJudge_50);
        Map<String, Integer> stringIntegerMapP_20 = AnalysisTextToMap.analysisTextToMap(scoreProgramme_20);
        map.put("scoreBankChoice50",stringIntegerMapC_50);
        map.put("scoreBankGapFilling50",stringIntegerMapG_50);
        map.put("scoreBankJudge50",stringIntegerMapJ_50);
        map.put("scoreBankProgramme20",stringIntegerMapP_20);

        if (map!=null){
            infoMsg.setCode(200);
            infoMsg.setMsg("获取练习成绩成功");
            responseBody.setInfo(infoMsg);
            responseBody.setData(map);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }


    @RequestMapping(value = "/score_chapter", method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getChapterScore(String id){
        com.seven.utils.ResponseBody<Map<String,Map<String,Integer>>> responseBody = new com.seven.utils.ResponseBody<>();
        InfoMsg infoMsg = new InfoMsg();
        Map<String,Map<String,Integer>> map = new HashMap<>();
        if (qbScoreService.getChapterScore(Integer.parseInt(id))==null){
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
        //获取章节成绩并转换
        QbChapter chapterScore = qbScoreService.getChapterScore(Integer.parseInt(id));

        String scoreChapter1  = chapterScore.getScoreChapter1();
        String scoreChapter2 = chapterScore.getScoreChapter2();
        String scoreChapter3 = chapterScore.getScoreChapter3();
        String scoreChapter4 = chapterScore.getScoreChapter4();
        String scoreChapter5 = chapterScore.getScoreChapter5();
        String scoreChapter6 = chapterScore.getScoreChapter6();
        String scoreChapter7 = chapterScore.getScoreChapter7();
        String scoreChapter8 = chapterScore.getScoreChapter8();
        String scoreChapter9 = chapterScore.getScoreChapter9();
        Map<String, Integer> stringIntegerMap1 = AnalysisTextToMap.analysisTextToMap(scoreChapter1);
        Map<String, Integer> stringIntegerMap2 = AnalysisTextToMap.analysisTextToMap(scoreChapter2);
        Map<String, Integer> stringIntegerMap3 = AnalysisTextToMap.analysisTextToMap(scoreChapter3);
        Map<String, Integer> stringIntegerMap4 = AnalysisTextToMap.analysisTextToMap(scoreChapter4);
        Map<String, Integer> stringIntegerMap5 = AnalysisTextToMap.analysisTextToMap(scoreChapter5);
        Map<String, Integer> stringIntegerMap6 = AnalysisTextToMap.analysisTextToMap(scoreChapter6);
        Map<String, Integer> stringIntegerMap7 = AnalysisTextToMap.analysisTextToMap(scoreChapter7);
        Map<String, Integer> stringIntegerMap8 = AnalysisTextToMap.analysisTextToMap(scoreChapter8);
        Map<String, Integer> stringIntegerMap9 = AnalysisTextToMap.analysisTextToMap(scoreChapter9);
        map.put("scoreChapter1",stringIntegerMap1);
        map.put("scoreChapter2",stringIntegerMap2);
        map.put("scoreChapter3",stringIntegerMap3);
        map.put("scoreChapter4",stringIntegerMap4);
        map.put("scoreChapter5",stringIntegerMap5);
        map.put("scoreChapter6",stringIntegerMap6);
        map.put("scoreChapter7",stringIntegerMap7);
        map.put("scoreChapter8",stringIntegerMap8);
        map.put("scoreChapter9",stringIntegerMap9);

        if (map!=null){
            infoMsg.setCode(200);
            infoMsg.setMsg("获取章节成绩成功");
            responseBody.setInfo(infoMsg);
            responseBody.setData(map);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }


    /**
     * 获取章节、题库练习、C的做的次数
     */
    @RequestMapping(value = "/seq_chapter",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getChapterSeq(String id,Integer chapterIndex){
        //声明变量
        int seq = 0;
        String scoreChapterN="";
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Integer> responseBody = new com.seven.utils.ResponseBody<>();

        if (qbScoreService.getChapterScore(Integer.parseInt(id))!=null){
            System.out.println(qbScoreService.getBankScore(Integer.parseInt(id)));
            //获取此章之前的数据
            QbChapter chapterScore = qbScoreService.getChapterScore(Integer.parseInt(id));

            //选择章节
            switch (chapterIndex){
                case 1:
                    scoreChapterN  = chapterScore.getScoreChapter1();
                    break;
                case 2:
                    scoreChapterN  = chapterScore.getScoreChapter2();
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
            }

            //读取之前的数据拿到最后一个seq
            if (StringUtils.isNullOrEmpty(scoreChapterN)){
                infoMsg.setCode(200);
                infoMsg.setMsg("Null");
                responseBody.setData(seq);
                responseBody.setInfo(infoMsg);
                return JSONArray.toJSONString(responseBody);
            }
            String[] split1 = scoreChapterN.split(";");
            String[] split2 = split1[split1.length - 1].split(",");
            seq = Integer.parseInt(split2[0].substring(4));
            infoMsg.setCode(200);
            infoMsg.setMsg("NotNull");
            responseBody.setData(seq);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(200);
            infoMsg.setMsg("Null");
            responseBody.setData(seq);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }

    @RequestMapping(value = "/seq_bank",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getBankSeq(String id,String type,Integer count){
        //声明变量
        int seq = 0;
        String scoreBankTypeN="";
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Integer> responseBody = new com.seven.utils.ResponseBody<>();
        System.out.println(qbScoreService.getBankScore(Integer.parseInt(id)));
        if (qbScoreService.getBankScore(Integer.parseInt(id))!=null){
            //获取此章之前的数据
            QbBank bankScore = qbScoreService.getBankScore(Integer.parseInt(id));

            //选择章节
            switch (type){
                case "选择题":
                   switch (count){
                       case 10:
                           scoreBankTypeN = bankScore.getScoreChoice10();
                           break;
                       case 25:
                           scoreBankTypeN = bankScore.getScoreChoice25();
                           break;
                       case 50:
                           break;
                   }
                    break;
                case "填空题":
                    switch (count){
                        case 10:
                            scoreBankTypeN = bankScore.getScoreGapFilling10();
                            break;
                        case 25:
                            break;
                        case 50:
                            break;
                    }
                    break;
                case "判断题":
                    switch (count){
                        case 10:
                            scoreBankTypeN = bankScore.getScoreJudge10();
                            break;
                        case 25:
                            break;
                        case 50:
                            break;
                    }
                    break;
                case "编程题":
                    switch (count){
                        case 5:
                            scoreBankTypeN = bankScore.getScoreProgramme5();
                            break;
                        case 10:
                            break;
                        case 20:
                            break;
                    }
                    break;
            }

            //读取之前的数据拿到最后一个seq
            if (StringUtils.isNullOrEmpty(scoreBankTypeN)){
                infoMsg.setCode(200);
                infoMsg.setMsg("Null");
                responseBody.setData(seq);
                responseBody.setInfo(infoMsg);
                return JSONArray.toJSONString(responseBody);
            }
            String[] split1 = scoreBankTypeN.split(";");
            String[] split2 = split1[split1.length - 1].split(",");
            seq = Integer.parseInt(split2[0].substring(4));
            infoMsg.setCode(200);
            infoMsg.setMsg("NotNull");
            responseBody.setData(seq);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(200);
            infoMsg.setMsg("Null");
            responseBody.setData(seq);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }

    @RequestMapping(value = "/seq_c",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getCSeq(String id,String type){
        //声明变量
        int seq = 0;
        String scoreCType="";
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Integer> responseBody = new com.seven.utils.ResponseBody<>();

        if (qbScoreService.getCScore(Integer.parseInt(id))!=null){
            //获取此章之前的数据
            QbC cScore = qbScoreService.getCScore(Integer.parseInt(id));

            //选择章节
            switch (type){
                case "简单":
                    scoreCType = cScore.getScoreCSimple();
                        break;
                case "中等":
                        scoreCType = cScore.getScoreCMiddle();
                        break;
                case "困难":
                        scoreCType = cScore.getScoreCHard();
                        break;
            }

            //读取之前的数据拿到最后一个seq
            if (StringUtils.isNullOrEmpty(scoreCType)){
                infoMsg.setCode(200);
                infoMsg.setMsg("Null");
                responseBody.setData(seq);
                responseBody.setInfo(infoMsg);
                return JSONArray.toJSONString(responseBody);
            }
            String[] split1 = scoreCType.split(";");
            String[] split2 = split1[split1.length - 1].split(",");
            seq = Integer.parseInt(split2[0].substring(4));
            infoMsg.setCode(200);
            infoMsg.setMsg("NotNull");
            responseBody.setData(seq);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(200);
            infoMsg.setMsg("Null");
            responseBody.setData(seq);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }

    @RequestMapping(value = "/seq_indepropo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getIndepropoSeq(String id){
        //声明变量
        int seq = 0;

        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Integer> responseBody = new com.seven.utils.ResponseBody<>();

        if (qbScoreService.getIndepropoScore(Integer.parseInt(id))!=null){
            //获取此章之前的数据
            String indepropoScore = qbScoreService.getIndepropoScore(Integer.parseInt(id));


            //读取之前的数据拿到最后一个seq
            if (StringUtils.isNullOrEmpty(indepropoScore)){
                infoMsg.setCode(200);
                infoMsg.setMsg("Null");
                responseBody.setData(seq);
                responseBody.setInfo(infoMsg);
                return JSONArray.toJSONString(responseBody);
            }
            String[] split1 = indepropoScore.split(";");
            String[] split2 = split1[split1.length - 1].split(",");
            seq = Integer.parseInt(split2[0].substring(4));
            infoMsg.setCode(200);
            infoMsg.setMsg("NotNull");
            responseBody.setData(seq);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(200);
            infoMsg.setMsg("Null");
            responseBody.setData(seq);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }


    @RequestMapping(value = "/score_store_indepropo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String scoreStoreIndepropo(String id,String name,Integer score){
        //声明变量
        int seq = 0;

        //获取此章之前的数据
        String indepropoScore = qbScoreService.getIndepropoScore(Integer.parseInt(id));

        if (indepropoScore==null){
            seq = 1;//判空则seq从1开始
        }else {
            //读取之前的数据拿到最后一个seq
            String[] split1 = indepropoScore.split(";");
            String[] split2 = split1[split1.length - 1].split(",");
            seq = Integer.parseInt(split2[0].substring(4)) + 1;
        }

        String cString = "score_indepropo";
        String storeString = "";
        if (null==indepropoScore || "".equals(indepropoScore)){
            //拼接storeString
            storeString ="seq=" + seq + "," + "name=" + name + "," +  "score=" + score + ";";
        }else {
            //拼接storeString
            storeString = indepropoScore + "seq=" + seq + "," + "name=" + name + "," + "score=" + score + ";";
        }

        int sId = Integer.parseInt(id);
        //调用存储方法
        boolean b = qbScoreService.scoreStoreIndepropo(sId, cString, storeString);
        InfoMsg infoMsg = new InfoMsg();
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("自主命题分数存储成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("自主命题分数存储失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }

    /**
     * 存储题库的分数和次数
     */
    @RequestMapping(value = "/score_store_c",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String scoreStoreC(String id,String type,Integer score){
        //声明变量
        int seq = 0;
        String sType = "";
        String scoreCType = "";

            //获取此章之前的数据
            QbC cScore = qbScoreService.getCScore(Integer.parseInt(id));

            if (cScore==null){
                seq = 1;
                switch (type){
                    case "简单":
                        sType = "simple";
                        break;
                    case "中等":
                        sType = "middle";
                        break;
                    case "困难":
                        sType = "hard";
                        break;
                }
            }else {

                switch (type) {
                    case "简单":
                        sType = "simple";
                        if (null == cScore.getScoreCSimple() || "".equals(cScore.getScoreCSimple())) {
                            seq = 1;
                        } else {
                            scoreCType = cScore.getScoreCSimple();
                        }
                        break;
                    case "中等":
                        sType = "middle";
                        if (null == cScore.getScoreCMiddle() || "".equals(cScore.getScoreCMiddle())) {
                            seq = 1;
                        } else {
                            scoreCType = cScore.getScoreCMiddle();
                        }
                        break;
                    case "困难":
                        sType = "hard";
                        if (null == cScore.getScoreCHard() || "".equals(cScore.getScoreCHard())) {
                            seq = 1;
                        } else {
                            scoreCType = cScore.getScoreCHard();
                        }
                        break;
                }
            }
        //判空则seq从1开始
            if (null!=scoreCType && !"".equals(scoreCType)){
                //读取之前的数据拿到最后一个seq
                String[] split1 = scoreCType.split(";");
                String[] split2 = split1[split1.length - 1].split(",");
                seq = Integer.parseInt(split2[0].substring(4)) + 1;
            }

        String cString = "";
        String storeString = "";
        if (null==scoreCType || "".equals(scoreCType)){
            //拼接chapterIndexString
            cString = "score_c_" + sType;
            //拼接storeString
            storeString ="seq=" + seq + "," + "score=" + score + ";";
        }else {
            //拼接chapterIndexString
            cString = "score_c_" + sType;
            //拼接storeString
            storeString = scoreCType + "seq=" + seq + "," + "score=" + score + ";";
        }

        int sId = Integer.parseInt(id);
        //调用存储方法
        boolean b = qbScoreService.scoreStoreC(sId, cString, storeString);
        InfoMsg infoMsg = new InfoMsg();
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("C分数存储成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("C分数存储失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }


    /**
     * 存储题库的分数和次数
     */
    @RequestMapping(value = "/score_store_bank",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String scoreStoreBank(String id,String type,Integer count,Integer score){
        //声明变量
        int seq = 0;
        String sType = "";
        //拿到题数
        String typeN = "";


            //获取此章之前的数据    //   不会吧  领悟奥义  不能判断空的空
            QbBank bankScore = qbScoreService.getBankScore(Integer.parseInt(id));
            if (bankScore==null){
                seq = 1;
                switch (type){
                    case "选择题":
                        sType = "choice";
                        break;
                    case "填空题":
                        sType = "gap_filling";
                        break;
                    case "判断题":
                        sType = "judge";
                        break;
                    case "编程题":
                        sType = "programme";
                        break;

                }
            }else {
                switch (type) {
                    case "选择题":
                        sType = "choice";
                        switch (count) {
                            case 10:
                                System.out.println(bankScore.getScoreChoice10());
                                if (null == bankScore.getScoreChoice25() || "".equals(bankScore.getScoreChoice10())) {
                                    seq = 1;
                                } else {
                                    typeN = bankScore.getScoreChoice10();
                                }
                                break;
                            case 25:
                                if (null == bankScore.getScoreChoice25() || "".equals(bankScore.getScoreChoice25())) {
                                    seq = 1;
                                } else {
                                    typeN = bankScore.getScoreChoice25();
                                }

                                break;
                            case 50:
                                break;
                        }
                        break;
                    case "填空题":
                        sType = "gap_filling";
                        switch (count) {
                            case 10:
                                if (null == bankScore.getScoreGapFilling10() || "".equals(bankScore.getScoreGapFilling10())) {
                                    seq = 1;
                                } else {
                                    typeN = bankScore.getScoreGapFilling10();
                                }

                                break;
                            case 25:
                                break;
                            case 50:
                                break;
                        }
                        break;
                    case "判断题":
                        sType = "judge";
                        switch (count) {
                            case 10:
                                if (null == bankScore.getScoreJudge10() || "".equals(bankScore.getScoreJudge10())) {
                                    seq = 1;
                                } else {
                                    typeN = bankScore.getScoreJudge10();
                                }

                                break;
                            case 25:
                                break;
                            case 50:
                                break;
                        }
                        break;
                    case "编程题":
                        sType = "programme";
                        switch (count) {
                            case 5:
                                if (null == bankScore.getScoreProgramme5() || "".equals(bankScore.getScoreProgramme5())) {
                                    seq = 1;
                                } else {
                                    typeN = bankScore.getScoreProgramme5();
                                }

                                break;
                            case 10:
                                break;
                            case 20:
                                break;
                        }
                        break;
                }
            }
        //判空则seq从1开始
        if (null!=typeN && !"".equals(typeN)){
            //读取之前的数据拿到最后一个seq
            String[] split1 = typeN.split(";");
            String[] split2 = split1[split1.length - 1].split(",");
            seq = Integer.parseInt(split2[0].substring(4)) + 1;
        }
        String bankString = "";
        String storeString = "";
        if (null==typeN || "".equals(typeN)){
            //拼接chapterIndexString
            bankString = "score_" + sType + "_" + count;
            //拼接storeString
            storeString ="seq=" + seq + "," + "score=" + score + ";";
        }else {
            //拼接chapterIndexString
            bankString = "score_" + sType + "_" + count;
            //拼接storeString
            storeString = typeN + "seq=" + seq + "," + "score=" + score + ";";
        }

        int sId = Integer.parseInt(id);
        //调用存储方法
        boolean b = qbScoreService.scoreStoreBank(sId, bankString, storeString);
        InfoMsg infoMsg = new InfoMsg();
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("题库分数存储成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("题库分数存储失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }

    /**
     * 存取前端传来的的章节1分数和次数（可复用）
     */
    @RequestMapping(value = "/score_store_chapter",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String scoreStoreChapter(String id,Integer chapterIndex,Integer score){
        //声明变量
        int seq = 0;
        String scoreChapterN = "";


            //获取此章之前的数据
            QbChapter chapterScore = qbScoreService.getChapterScore(Integer.parseInt(id));
            if (chapterScore==null){
                seq=1;
            }else {

                switch (chapterIndex) {
                    case 1:
                        //判空则seq从1开始
                        if (null == chapterScore.getScoreChapter1() || "".equals(chapterScore.getScoreChapter1())) {
                            seq = 1;
                        } else {
                            scoreChapterN = chapterScore.getScoreChapter1();
                        }
                        break;
                    case 2:
                        if (null == chapterScore.getScoreChapter2() || "".equals(chapterScore.getScoreChapter2())) {
                            seq = 1;
                        } else {
                            scoreChapterN = chapterScore.getScoreChapter2();
                        }
                        break;
                    case 3:
                        break;
                    case 4:
                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    case 7:
                        break;
                    case 8:
                        break;
                    case 9:
                        break;
                }
            }
        if (null!=scoreChapterN && !"".equals(scoreChapterN)) {
            //读取之前的数据拿到最后一个seq
            String[] split1 = scoreChapterN.split(";");
            String[] split2 = split1[split1.length - 1].split(",");
            seq = Integer.parseInt(split2[0].substring(4)) + 1;
        }
        String chapterIndexString = "";
        String storeString = "";
        if (null==scoreChapterN || "".equals(scoreChapterN)) {
            //拼接chapterIndexString
            chapterIndexString = "score_chapter_" + chapterIndex;
            //拼接storeString
            storeString = "seq=" + seq + "," + "score=" + score + ";";
        }

        //拼接chapterIndexString
        chapterIndexString = "score_chapter_" + chapterIndex;
        //拼接storeString
        storeString = scoreChapterN + "seq=" + seq + "," + "score=" + score + ";";

        int sId = Integer.parseInt(id);
        //调用存储方法
        boolean b = qbScoreService.scoreStoreChapter(sId, chapterIndexString, storeString);
        InfoMsg infoMsg = new InfoMsg();
        if (b){
            infoMsg.setCode(200);
            infoMsg.setMsg("章节分数存储成功");
            return JSONArray.toJSONString(infoMsg);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("章节分数存储失败");
            return JSONArray.toJSONString(infoMsg);
        }
    }


    /**
     * 获取自主命题的试卷名
     */
    @RequestMapping(value = "/name_indepropo",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getIndepropoNames(String id){
        com.seven.utils.ResponseBody<List<String>> responseBody = new com.seven.utils.ResponseBody<>();
        InfoMsg infoMsg = new InfoMsg();
        List<String> nameList = new ArrayList<>();

        if (StringUtils.isNullOrEmpty(qbScoreService.getIndepropoScore(Integer.parseInt(id)))){
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
        //获取章节成绩并转换
        String indepropoScore = qbScoreService.getIndepropoScore(Integer.parseInt(id));

        nameList = AnalysisTextToMap.analysisTextToMapIName(indepropoScore);

        if (nameList!=null){
            infoMsg.setCode(200);
            infoMsg.setMsg("获取自主命题成绩成功");
            responseBody.setInfo(infoMsg);
            responseBody.setData(nameList);
            return JSONArray.toJSONString(responseBody);
        }else {
            infoMsg.setCode(400);
            responseBody.setInfo(infoMsg);
            return JSONArray.toJSONString(responseBody);
        }
    }


}


