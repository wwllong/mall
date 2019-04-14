package com.mall.pojogroup;

import com.mall.pojo.Specification;
import com.mall.pojo.SpecificationOption;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Jack Wen
 * @className SpecificationGroup
 * @dsecription 规格+规格详细组合实体类
 * @data 2019/4/12
 * @vserion 1.0
 */

@Data
public class SpecificationGroup implements Serializable{

    //规格
    private Specification specification;

    //规格详细
    private List<SpecificationOption> specificationOptionList;


}
