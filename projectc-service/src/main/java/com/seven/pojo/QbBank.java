package com.seven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class QbBank {

    private String scoreChoice10;
    private String scoreChoice25;
    private String scoreChoice50;
    private String scoreGapFilling10;
    private String scoreGapFilling25;
    private String scoreGapFilling50;
    private String scoreJudge10;
    private String scoreJudge25;
    private String scoreJudge50;
    private String scoreProgramme5;
    private String scoreProgramme10;
    private String scoreProgramme20;
}
