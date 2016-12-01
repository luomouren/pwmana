package com.zacamy.pwmana.mapper.user;

import java.util.List;

import com.zacamy.pwmana.bean.User;

public interface UserMapper {
	void save(User user) ;
	boolean update(User user);
	boolean delete(int id);
	User findById(int id);
	List<User> findAll();
}
