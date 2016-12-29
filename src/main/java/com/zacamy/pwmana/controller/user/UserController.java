/*package com.zacamy.pwmana.controller.user;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zacamy.pwmana.bean.User;
import com.zacamy.pwmana.service.user.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	*//**
	 * 查找所有的用户记录
	 * @param request
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/getAllUser")
	public String getAllUser(HttpServletRequest request,Model model){
		List<User> allUserList = userService.findAll();
		model.addAttribute("userList", allUserList);
		
		return "/user/allUser";
	}
	
	*//**
	 * 跳转到添加用户页面
	 * @param request
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/toAddUser")
	public String toAddUser(HttpServletRequest request,Model model){
		
		return "/user/addUser";
	}
	
	*//**
	 * 保存添加的用户并重定向
	 * @param request
	 * @param model
	 * @param user 需要保存的用户
	 * @return
	 *//*
	@RequestMapping("/addUser")
	public String addUser(HttpServletRequest request,Model model,User user){
		userService.save(user);
		return "redirect:/user/getAllUser";
	}
	
	*//**
	 * 删除用户
	 * @param id  要删除用户的id
	 * @param request
	 * @param model
	 *//*
	@RequestMapping("/delUser")
	public void delUser(int id ,HttpServletRequest request,Model model,HttpServletResponse response){
		String result = "{\"result\":\"error\"}";
		
		if(userService.delete(id)){
			result = "{\"result\":\"success\"}";
		}
		
		response.setContentType("application/json");
		
		try {
			PrintWriter out = response.getWriter();
			out.write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	*//**
	 * 根据id查询单个用户
	 * @param id
	 * @param request
	 * @param model
	 * @return
	 *//*
	@RequestMapping("/getUser")
	public String getUser(int id ,HttpServletRequest request,Model model){
		model.addAttribute("user",userService.findById(id));
		return "/user/editUser";
	}
	
	public String updateUser(User user,HttpServletRequest request){
		if(userService.update(user)){
			user = userService.findById(user.getId());
			request.setAttribute("user", user);
			return "redirect:/user/getAllUser";
		}else{
			return "/error";
		}
		
	}
	
	
}
*/