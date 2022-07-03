package com.seven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 一道题的序号+分数
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SequenceField implements Serializable {
    private int seq;
    private int score;
}
