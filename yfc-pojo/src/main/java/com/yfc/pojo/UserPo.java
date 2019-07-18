package com.yfc.pojo;

import lombok.Getter;
import lombok.Setter;



/**
 * @author yfc
 * @date 2019-06-13
 * @description
 * @parmas
 */
@Setter
@Getter
public class UserPo {
    private Integer id;
    private String userName;
    private String password;
    private Integer age;
    private String email;
    private String userUid;
    private String memberToken;
    private String telephone;
    private String openId;
}
