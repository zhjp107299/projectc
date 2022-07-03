package com.seven.pojo;
/*实体类：判断题*/

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Judge {
    private Integer id;
    private String topic;
    private String answerRight;
    private String chapter;
    private String indepropoName;

    public Judge(Integer id, String topic, String answerRight, String chapter) {
        this.id = id;
        this.topic = topic;
        this.answerRight = answerRight;
        this.chapter = chapter;
    }

}
