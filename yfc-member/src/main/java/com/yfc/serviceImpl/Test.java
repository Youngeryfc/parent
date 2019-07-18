package com.yfc.serviceImpl;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author yfc
 * @date 2019-06-13
 * @description
 * @parmas
 */
public class Test {
    public static void main(String[] args) {
        Connection connection=null;
        String driver="com.mysql.cj.jdbc.Driver";
        String url="jdbc:mysql://papt.mychong.top:3306/yfc";
        String user="root";
        String password="123456";
        try{

            //家在mysql驱动
            Class.forName(driver);
            //创建connect连接
            connection= DriverManager.getConnection(url,user,password);
            //创建查询连接
            Statement st=connection.createStatement();
            //执行sql
            ResultSet result=st.executeQuery("select * from user where id='1' ");
            while(result.next()){
                System.out.println(result.getString("username")+",id:"+result.getInt("id"));
            }
            result.close();
            connection.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
