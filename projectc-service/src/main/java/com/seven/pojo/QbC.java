package com.seven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 模拟C练习分数的结构
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QbC {
    private String scoreCSimple;
    private String scoreCMiddle;
    private String scoreCHard;
}
