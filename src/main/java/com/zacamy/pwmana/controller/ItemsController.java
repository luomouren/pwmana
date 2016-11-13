package com.zacamy.pwmana.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value="/itemsController")
public class ItemsController {
	
	@RequestMapping(value="/queryItems")
	public  String queryItems(HttpServletRequest request,Model model){
		/*List<ItemsCustom> itemsList = new ArrayList<ItemsCustom>();
		ModelAndView modelAndView =  new ModelAndView();
		modelAndView.addObject("itemsList", null);
		modelAndView.setViewName("items/itemsList");*/
		
		model.addAttribute("username", "admin");
		return "demo";
	}
	
    @RequestMapping(value = "/index")
    public String index() {
        return "demo";
    }
    
    /**
     * 手机登录方法
     * @param username 用户名
     * @param password 密码
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/app/loginApp", method = RequestMethod.POST)
    public String getMonery(String username,String password) {
    	System.out.println(password);
        return "success,username="+username+",password="+password;
    }
    
    
    /**
     * 手机拍照上传图片
     * @param file 图片文件
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/app/upload", method = RequestMethod.POST)
    public String upload(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            File f = new File("G:\\uploadFile.jpg");
            
            try {
				file.transferTo(f);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            return "success";
        }
        return "fail";
    }
    
    
}
