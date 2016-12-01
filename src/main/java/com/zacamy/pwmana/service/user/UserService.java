package com.zacamy.pwmana.service.user;

import java.util.List;

import com.zacamy.pwmana.bean.User;

public interface UserService {
	void save(User user) ;
	boolean update(User user);
	boolean delete(int id);
	User findById(int id);
	List<User> findAll();
}
