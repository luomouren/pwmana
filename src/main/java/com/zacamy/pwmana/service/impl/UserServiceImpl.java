package com.zacamy.pwmana.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zacamy.pwmana.dao.UserDao;
import com.zacamy.pwmana.model.User;
import com.zacamy.pwmana.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;

	@Override
	@Transactional
	public boolean login(String username, String password) {
		boolean flag = false;
		User user = this.userDao.findByUsernameAndPassword(username, password);
		if(user != null){
			if(username.equals(user.getUsername()) && password.equals(user.getPassword())){
				flag = true;
			}
		}
		return flag;
	}

}
