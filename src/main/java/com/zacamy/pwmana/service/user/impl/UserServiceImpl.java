package com.zacamy.pwmana.service.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zacamy.pwmana.bean.User;
import com.zacamy.pwmana.mapper.user.UserMapper;
import com.zacamy.pwmana.service.user.UserService;

@Service
@Transactional  //此处不再进行创建SqlSession和提交事务，都已交由spring去管理了。
public class UserServiceImpl implements UserService {
	@Resource
	private UserMapper mapper;
	@Override
	public void save(User user) {
		mapper.save(user);
	}

	@Override
	public boolean update(User user) {
		return mapper.update(user);
	}

	@Override
	public boolean delete(int id) {
		return mapper.delete(id);
	}

	@Override
	public User findById(int id) {
		return mapper.findById(id);
	}

	@Override
	public List<User> findAll() {
		return mapper.findAll();
	}

}
