package com.yfc.serviceImpl;

import com.yfc.api.service.PayInfoService;
import com.yfc.common.base.BaseController;
import com.yfc.common.base.BasePojo;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yfc
 * @date 2019-07-19
 * @description
 * @parmas
 */
@RestController
public class PayServiceImpl extends BaseController implements PayInfoService {
    @Override
    public BasePojo createToken() {
        return null;
    }

    @Override
    public BasePojo findPayToken() {
        return null;
    }
}
