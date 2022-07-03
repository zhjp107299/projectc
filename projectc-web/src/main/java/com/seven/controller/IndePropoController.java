package com.seven.controller;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.seven.pojo.*;
import com.seven.service.IndePropoService;
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
 * 关于自主命题的一些操作
 */


@RestController
@ResponseBody
@CrossOrigin
public class IndePropoController {
    @Autowired
    @Qualifier("indePropoServiceImpl")
    private IndePropoService indePropoService;
    public void setIndePropoService(IndePropoService indePropoService){
        this.indePropoService = indePropoService;
    }


    //教师获取自主命题开关状态
    @RequestMapping(value = "/indepropo_name_status",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getIndePropoShowList(String teacher){
        InfoMsg infoMsg = new InfoMsg();
        List<IndepropoShow> showList = new ArrayList<>();
        com.seven.utils.ResponseBody<List<IndepropoShow>> listResponseBody = new com.seven.utils.ResponseBody<>();
        infoMsg.setCode(200);
        showList = indePropoService.getIndepropoShowList(teacher);


        for (IndepropoShow indepropoShow : showList) {
            if (indepropoShow.getIndepropoSwitch().equals("on")){
                indepropoShow.setStatus(true);
            }else {
                indepropoShow.setStatus(false);
            }
        }

        if (showList!=null){
            infoMsg.setMsg("获取自主命题状态成功");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(showList);
            return JSONArray.toJSONString(listResponseBody);
        }else {
            infoMsg.setMsg("Null");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(new ArrayList<IndepropoShow>());
            return JSONArray.toJSONString(listResponseBody);
        }
    }

    @RequestMapping(value = "/indepropo_name",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getIndePropoNameList(String teacher){
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<List<IndepropoShow>> listResponseBody = new com.seven.utils.ResponseBody<>();

        List<IndepropoShow> list = new ArrayList<>();
        list = indePropoService.getIndepropoNameList(teacher);  //根据教师名字获取自主命题列表

        if (list!=null){
            infoMsg.setCode(200);
            infoMsg.setMsg("获取自主命题名字成功");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(list);
            return JSONArray.toJSONString(listResponseBody);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("Null");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(new ArrayList<IndepropoShow>());
            return JSONArray.toJSONString(listResponseBody);
        }
    }

    //学生获取自主命题状态是否开启
    @RequestMapping(value = "/indepropo_status",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getIndePropoNameStatus(String indeName){
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<String> listResponseBody = new com.seven.utils.ResponseBody<>();
        String indePropoNameStatus = indePropoService.getIndePropoNameStatus(indeName);

        if (indePropoNameStatus!=null){
            infoMsg.setCode(200);
            infoMsg.setMsg("获取自主命题文件状态成功");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData(indePropoNameStatus);
            return JSONArray.toJSONString(listResponseBody);
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("Null");
            listResponseBody.setInfo(infoMsg);
            listResponseBody.setData("");
            return JSONArray.toJSONString(listResponseBody);
        }
    }

    //查看自主命题题型
    @RequestMapping(value = "/indepropo_all",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String getIndePropoAll(String indepropoName){
        ChapterResponseBody chapterResponseBody = null;
        InfoMsg infoMsg = new InfoMsg();
        com.seven.utils.ResponseBody<ChapterResponseBody> chapterResponseBodyResponseBody = new com.seven.utils.ResponseBody<>();
        //12选择题
        List<Choice> indeChoices12 = indePropoService.getIndeChoices12(indepropoName);
        //8填空题
        List<GapFilling> indeGapFillings8 = indePropoService.getIndeGapFillings8(indepropoName);
        //12判断题
        List<Judge> indeJudges12 = indePropoService.getIndeJudges12(indepropoName);
        //3道编程题
        List<Programme> indeProgrammes3 = indePropoService.getIndeProgrammes3(indepropoName);
        chapterResponseBody = new ChapterResponseBody(indeChoices12,indeGapFillings8,indeJudges12,indeProgrammes3);
        System.out.println(chapterResponseBody);

        infoMsg.setCode(200);
        infoMsg.setMsg("获取自主命题成功");
        chapterResponseBodyResponseBody.setInfo(infoMsg);
        chapterResponseBodyResponseBody.setData(chapterResponseBody);
        return JSONArray.toJSONString(chapterResponseBodyResponseBody);

    }


    //修改自主命题状态
    @RequestMapping(value = "/switch_change",method = RequestMethod.POST,produces = "application/json;charset=utf-8")
    public String switchChange(String status,String indeName){
        InfoMsg infoMsg = new InfoMsg();

        System.out.println(status);
        String newStatus = "";
        String indeType1 = "projectc.qb_choice";
        String indeType2 = "projectc.qb_gap_filling";
        String indeType3 = "projectc.qb_judge";
        String indeType4 = "projectc.qb_programme";
        if (!StringUtils.isNullOrEmpty(status)){
            switch (status){
                case "true":
                    newStatus = "on";
                    break;
                case "false":
                    newStatus = "off";
                    break;
            }
            boolean b1 = indePropoService.changeIndePropoStatus(indeType1, newStatus,indeName);
            boolean b2 = indePropoService.changeIndePropoStatus(indeType2, newStatus,indeName);
            boolean b3 = indePropoService.changeIndePropoStatus(indeType3, newStatus,indeName);
            boolean b4 = indePropoService.changeIndePropoStatus(indeType4, newStatus,indeName);
            if (b1&&b2&&b3&&b4){
                infoMsg.setCode(200);
                infoMsg.setMsg("修改状态成功");
                return JSONArray.toJSONString(infoMsg);
            }else {
                infoMsg.setCode(400);
                infoMsg.setMsg("修改状态失败");
                return JSONArray.toJSONString(infoMsg);
            }
        }else {
            infoMsg.setCode(400);
            infoMsg.setMsg("未获取到状态");
            return JSONArray.toJSONString(infoMsg);
        }
    }



}
