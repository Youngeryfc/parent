package com.yfc.baseMapper;


import com.yfc.common.base.Counter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface CounterMapper {
	@Select("select * from counter where cnt_type = #{cntType} and cnt_key = #{cntKey}")
	Counter getCounter(Counter cnt);

	@Insert("insert into counter(cnt_type,cnt_key,cnt_value)values(#{cntType},#{cntKey},#{cntValue})")
	int addCounter(Counter cnt);

	@Update("update counter set cnt_value=#{cntValue} where cnt_type = #{cntType} and cnt_key =#{cntKey}")
	int updateCounter(Counter cnt);
}
