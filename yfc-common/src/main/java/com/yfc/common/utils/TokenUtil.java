package com.yfc.common.utils;

import com.yfc.common.constant.Constants;
import org.apache.commons.lang.StringUtils;

import java.util.UUID;

/**
 * @author yfc
 * @date 2019-06-19
 * @description
 * @parmas
 */
public class TokenUtil {
    public static String getMemberToken(){
        return Constants.TOKEN_MEMBER+ UUID.randomUUID();
    }
}
