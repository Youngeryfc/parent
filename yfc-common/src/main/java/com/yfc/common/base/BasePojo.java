package com.yfc.common.base;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author yfc
 * @date 2019-06-11
 * @description
 * @parmas
 */
@Setter
@Getter
public class BasePojo implements Serializable {
    private Integer code;
    private String msg;
    private Object data;

    public BasePojo() {

    }

    public BasePojo(Integer code, String msg, Object data){
        super();
        this.code=code;
        this.msg=msg;
        this.data=data;
    }
}
