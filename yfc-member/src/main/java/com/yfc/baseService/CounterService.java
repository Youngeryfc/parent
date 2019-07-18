package com.yfc.baseService;

import com.yfc.baseMapper.CounterMapper;
import com.yfc.common.base.Counter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author yfc
 * @date 2019-06-20
 * @description
 * @parmas
 */
@Service
public class CounterService implements CounterMapper {
    @Autowired
    private CounterMapper counterMapper;

    @Override
    public Counter getCounter(Counter cnt) {
        return counterMapper.getCounter(cnt);
    }

    /**
     * @Author yfc
     * @Date 2019-06-20
     * @Despration 获取cntValue
     * @Param
     */
    public int getCntValue(Counter cnt){
        Counter counter=getCounter(cnt);
        if(counter==null){
            cnt.setCntValue(cnt.getFirstValue());
            addCounter(cnt);
        }else{
            cnt.setCntValue(cnt.getCntValue()+cnt.getStep());
            updateCounter(cnt);
        }
        return cnt.getCntValue();
    }

    @Transactional
    @Override
    public int addCounter(Counter cnt) {
        return counterMapper.addCounter(cnt);
    }

    @Transactional
    @Override
    public int updateCounter(Counter cnt) {
        return counterMapper.updateCounter(cnt);
    }
}
