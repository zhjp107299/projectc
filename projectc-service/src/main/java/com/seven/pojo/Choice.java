package com.seven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*实体类 ：选择题*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Choice {
    private Integer id;//选择题的编号
    private String topic;//题目
    private String choiceA;//A选项
    private String choiceB;//B选项
    private String choiceC;//C选项
    private String choiceD;//D选项
    private String chapter;//所属章节
    private String choiceRight;//正确答案
    private String cLevel;
    private String indepropoName;

    public Choice(Integer id, String topic, String choiceA, String choiceB, String choiceC, String choiceD, String chapter, String choiceRight, String cLevel) {
        this.id = id;
        this.topic = topic;
        this.choiceA = choiceA;
        this.choiceB = choiceB;
        this.choiceC = choiceC;
        this.choiceD = choiceD;
        this.chapter = chapter;
        this.choiceRight = choiceRight;
        this.cLevel = cLevel;
    }

}
