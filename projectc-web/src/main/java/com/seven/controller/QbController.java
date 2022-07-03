package com.seven.controller;

import com.alibaba.fastjson.JSONArray;
import com.seven.pojo.Choice;
import com.seven.pojo.GapFilling;
import com.seven.pojo.Programme;
import com.seven.pojo.Judge;
import com.seven.service.QbService;
import com.seven.utils.InfoMsg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理题库练习和模拟C测试、自主命题
 */
@RestController
@ResponseBody
@CrossOrigin
public class QbController {

    @Autowired
    @Qualifier("qbServiceImpl")
    private QbService qbService;
    public void setQbChoiceService(QbService qbService){
        this.qbService = qbService;
    }

    /**
     * 题库模块
     */

    //10选择题
    /*50选择题*/
    /*30选择题*/
    @RequestMapping(value = "/bank_choice",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getBankChoiceNList(Integer choiceN) {
        System.out.println(choiceN);
      //声明变量
        List<Choice> choiceListN = null;
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<List<Choice>> listResponseBody = new com.seven.utils.ResponseBody<>();

        //调用获取题目数量
        choiceListN = qbService.getQbChoiceNList(choiceN);

        if (choiceListN!=null){
            //返回JSON
            infoMsg.setCode(200);
            infoMsg.setMsg("获取选择题库成功");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(choiceListN);
            return JSONArray.toJSONString(listResponseBody);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("获取选择题库失败");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(new ArrayList<Choice>());
            return JSONArray.toJSONString(listResponseBody);
        }
    }

    @RequestMapping(value = "/bank_gapfilling",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getBankGapFillingNList(Integer N) {
        //声明变量
        List<GapFilling> gapFillingListN = null;
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<List<GapFilling>> listResponseBody = new com.seven.utils.ResponseBody<>();

        //调用获取题目数量
        gapFillingListN = qbService.getQbGapFillingNList(N);
        if (gapFillingListN!=null){
            //返回JSON
            infoMsg.setCode(200);
            infoMsg.setMsg("获取选择题库成功");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(gapFillingListN);
            return JSONArray.toJSONString(listResponseBody);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("获取选择题库失败");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(new ArrayList<GapFilling>());
            return JSONArray.toJSONString(listResponseBody);
        }
    }

    @RequestMapping(value = "/bank_judge",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getBankJudgeNList(Integer N) {
        //声明变量
        List<Judge> judgeListN = null;
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<List<Judge>> listResponseBody = new com.seven.utils.ResponseBody<>();

        //调用获取题目数量
        judgeListN = qbService.getQbJudgeListN(N);
        if (judgeListN!=null){
            //返回JSON
            infoMsg.setCode(200);
            infoMsg.setMsg("获取选择题库成功");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(judgeListN);
            return JSONArray.toJSONString(listResponseBody);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("获取选择题库失败");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(new ArrayList<Judge>());
            return JSONArray.toJSONString(listResponseBody);
        }
    }

    /*30编程填空题*/
    /*20编程题*/
    /*10编程题*/
    @RequestMapping(value = "/bank_programme",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getBankProgrammeNList(Integer N) {
        //声明变量
        List<Programme> programmeListN = null;
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<List<Programme>> listResponseBody = new com.seven.utils.ResponseBody<>();

        //调用获取题目数量
        programmeListN = qbService.getQbProgrammeListN(N);
        if (programmeListN!=null){
            //返回JSON
            infoMsg.setCode(200);
            infoMsg.setMsg("获取选择题库成功");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(programmeListN);
            return JSONArray.toJSONString(listResponseBody);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("获取选择题库失败");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(new ArrayList<Programme>());
            return JSONArray.toJSONString(listResponseBody);
        }
    }

    /**
     * c模块
     */
    @RequestMapping(value = "/c_type",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getCTypeList(String type) {
        //声明变量
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<Map<String,Object>> listResponseBody = new com.seven.utils.ResponseBody<>();

        //调用获取c模块题型列表
        List<Choice> qbChoice_50 = qbService.getQbChoice_50(type);
        List<GapFilling> qbGapFilling_20 = qbService.getQbGapFilling_20(type);
        List<Programme> qbProgramme_4 = qbService.getQbProgramme_4(type);
        Map<String,Object> map = new HashMap<>();
        map.put("choice_50",qbChoice_50);
        map.put("gapFilling_20",qbGapFilling_20);
        map.put("programme_4",qbProgramme_4);
        if (qbChoice_50!=null && qbGapFilling_20!=null && qbProgramme_4!=null){
            //返回JSON
            infoMsg.setCode(200);
            infoMsg.setMsg("获取C模块成功");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(map);
            return JSONArray.toJSONString(listResponseBody);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("获取C模块失败");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(new HashMap<String,Object>());
            return JSONArray.toJSONString(listResponseBody);
        }
    }
}
