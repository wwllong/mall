package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName Specification
 * @dsecription 规格类
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Specification implements Serializable {
    private Long id;

    private String specName;

    private static final long serialVersionUID = 1L;

    public void setSpecName(String specName) {
        this.specName = specName == null ? null : specName.trim();
    }

}