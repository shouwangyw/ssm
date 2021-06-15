package com.yw.mybatis.example.mapper.annotation;

import com.yw.mybatis.example.po.User;
import org.apache.ibatis.annotations.Select;

public interface AnnotationUserMapper {
	@Select("select * from user where id = #{id}")
	User findUserById(int id);
}