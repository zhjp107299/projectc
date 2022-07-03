package com.seven.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * score_student表的数据结构
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScoreStudent {

    private Integer scoreId;
    private String scoreChapter1;
    private String scoreChapter2;
    private String scoreChapter3;
    private String scoreChapter4;
    private String scoreChapter5;
    private String scoreChapter6;
    private String scoreChapter7;
    private String scoreChapter8;
    private String scoreChapter9;

    private String scoreChoice10;
    private String scoreChoice25;
    private String scoreChoice50;

    private String scoreGapFilling10;
    private String scoreGapFilling25;
    private String scoreGapFilling50;

    private String scoreJudge10;
    private String scoreJudge25;
    private String scoreJudge50;

    private String scoreProgramme10;
    private String scoreProgramme25;
    private String scoreProgramme50;

    private String scoreCSimple;
    private String scoreCMiddle;
    private String scoreCHard;

    private String scoreIndepropo;

    public ScoreStudent(Integer scoreId) {
        this.scoreId = scoreId;
    }
}
