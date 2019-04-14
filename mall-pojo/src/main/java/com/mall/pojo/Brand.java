package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Jack Wen.
 * @ClassName Brand
 * @dsecription 品牌类
 * @data 2019/4/11
 * @Vserion 1.0
 */

@Data
public class Brand implements Serializable {
    private Long id;

    private String name;

    private String firstChar;

    private static final long serialVersionUID = 1L;

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar == null ? null : firstChar.trim();
    }

}