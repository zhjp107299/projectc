package com.seven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*实体类：填空题*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GapFilling {
    private Integer id;//填空题的编号
    private String topic;//题目
    private String answerRight1;//正确答案
    private String answerRight2;//正确答案
    private String answerRight3;//正确答案
    private String answerRight4;//正确答案
    private String chapter;//所属章节
    private String cLevel;
    private String indepropoName;

    public GapFilling(Integer id, String topic, String answerRight1, String answerRight2, String answerRight3, String answerRight4, String chapter, String cLevel) {
        this.id = id;
        this.topic = topic;
        this.answerRight1 = answerRight1;
        this.answerRight2 = answerRight2;
        this.answerRight3 = answerRight3;
        this.answerRight4 = answerRight4;
        this.chapter = chapter;
        this.cLevel = cLevel;
    }

}
