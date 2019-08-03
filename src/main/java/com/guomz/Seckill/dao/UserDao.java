package com.guomz.Seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.guomz.Seckill.entity.User;
@Repository
public interface UserDao {

	public User queryUserByMobile(@Param(value = "mobile")String mobile);
	
	public User queryUserByMobileAndPassword(@Param("mobile")String mobile,@Param("password")String password);
}
