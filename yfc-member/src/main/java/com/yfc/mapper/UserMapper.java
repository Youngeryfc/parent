package com.yfc.mapper;


import com.yfc.pojo.UserPo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;


@Mapper
public interface UserMapper {
    @Select("select * from user where id=#{id}")
    UserPo getUserById(Integer id);

    @Select("select * from user where userUid=#{userUid}")
    UserPo getUserByUserUId(String userUid);

    @Insert("insert into user(id,userName,age,email,password,userUid,telephone) values(#{id},#{userName},#{age},#{email},#{password},#{userUid},${telephone})")
    int inserUser(UserPo userPo);

    @Select("select * from user where userName=#{name}")
    UserPo checkLogin(String name);

    @Select("select * from user where openId=#{openId}")
    UserPo findUserByOpenId(String openId);

    @Update("update user set openid=#{openId},memberToken=#{memberToken} where userUid=#{userUid}")
    Integer updateUserByOpenId(String openId,String memberToken,String  userUid);
}
