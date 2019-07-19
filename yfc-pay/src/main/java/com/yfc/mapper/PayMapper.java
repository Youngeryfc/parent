package com.yfc.mapper;

import com.yfc.pojo.PayInfoPo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface PayMapper {
    //根据id查询所有信息
    @Select("select * from payInfo where id=#{id}")
    public PayInfoPo getPayInfoById(@Param("id") int id);

    //插入一条支付信息
    public Integer insertPayInfo(PayInfoPo payInfoPo);

    //根据orderId查询当前信息
    public PayInfoPo getPayInfoByOrderId(@Param("orderId") String orderId);

    //修改当前信息
    public int updatePayInfo(PayInfoPo payInfoPo);
}
