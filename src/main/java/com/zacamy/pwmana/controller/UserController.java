package com.zacamy.pwmana.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zacamy.pwmana.model.User;
import com.zacamy.pwmana.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@RequestMapping(value="/login",method=RequestMethod.GET,params="form")
	public String toLogin(Model model){
		User user = new User();
		model.addAttribute(user);
		return "user/login";
	}
	
	@ResponseBody
	@RequestMapping(value = "/app/login",method=RequestMethod.POST)
	public String login(String username, String password) {
		boolean isLogin = false;
		if(null!=username && null!=password && !"".equalsIgnoreCase(password)&& !"".equalsIgnoreCase(username)){
			//登录
			isLogin = userService.login(username, password);
			if(isLogin){
				return "success";
			}
		}
		return "false";
		/*if(result.hasErrors()){
		return "user/login";
		}
		if(this.userService.login(user.getUsername(), user.getPassword())){
			return "user/success";
		}
		return "user/fail";*/
	}
	

}
