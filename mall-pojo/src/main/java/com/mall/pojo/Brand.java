package com.mall.pojo;

import lombok.Data;

import java.io.Serializable;

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