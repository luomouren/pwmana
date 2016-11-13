package com.zacamy.pwmana.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

//@Controller
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
}
