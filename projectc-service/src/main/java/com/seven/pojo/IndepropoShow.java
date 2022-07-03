package com.seven.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndepropoShow {

    private String indepropoName;
    private boolean status = false;
    private String indepropoSwitch;
}
