package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName SpecificationOption
 * @dsecription 规格明细类
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class SpecificationOption implements Serializable {
    private Long id;

    private String optionName;

    private Long specId;

    private Integer orders;

    private static final long serialVersionUID = 1L;

    public void setOptionName(String optionName) {
        this.optionName = optionName == null ? null : optionName.trim();
    }

}