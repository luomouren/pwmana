package com.zacamy.pwmana.controller.wechatpay;

import org.springframework.web.bind.annotation.ModelAttribute;

public class BaseControler {

	@ModelAttribute
	public void init(){
		System.out.println("");
	}
	
	
}
