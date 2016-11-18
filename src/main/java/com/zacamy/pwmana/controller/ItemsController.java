package com.zacamy.pwmana.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.zacamy.pwmana.vo.VehicleIdentificationResult;  
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
    @RequestMapping(value = "/app/loginApp", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    public String getMonery(String username,String password) {
    	System.out.println(password);
        return "success,username="+username+",password="+password;
    }
    
    
    /**
     * 手机拍照上传图片
     * @param file 图片文件
     * @return
     */
    @RequestMapping(value = "/app/upload", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String upload(@RequestParam("file") MultipartFile file) {
    	String jsonString = null;
        if (!file.isEmpty()) {
        	//1、将识别图片保存到服务器磁盘路径
        	SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
        	String time = df.format(new Date());//new Date()为获取当前系统时间
        	String imgUrl="G:\\uploadFile"+time+".jpg";
            File f = new File(imgUrl);
            try {
				file.transferTo(f);
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
            
            //2、将识别记录保存到数据库中
            
            
            //3、识别过程
            
            //4、返回识别结果
            VehicleIdentificationResult result = new VehicleIdentificationResult();
            result.setCarNo("京x3425");
            result.setCarType("别克");
            result.setColor("黑色");
            result.setNumberColor("蓝色");
            //将bean转换成json
            jsonString = JSON.toJSONString(result);
            //将json还原成bean
            //VehicleIdentificationResult vo = JSON.parseObject(jsonString, VehicleIdentificationResult.class);
            
            return jsonString;
        }
        return jsonString;
    }

    /**
     * 查询历史 拍照识别 记录
     * @param startNum 请求加载列表开始行数
     * @param endNum 请求加载列表结束行数
     * @return
     */
    @RequestMapping(value = "/app/getHistoryVIResultList", method = RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public String getHistoryVIResultList(int startNum,int endNum) {
    	String jsonString = null;
    	List<VehicleIdentificationResult> lists = new ArrayList<VehicleIdentificationResult>();
    	if(1==startNum && 10==endNum){
    		for(int i=0;i<10;i++){
    			VehicleIdentificationResult result = new VehicleIdentificationResult();
                result.setCarNo("京E111"+i);
                result.setCarType("别克");
                result.setColor("黑色");
                result.setNumberColor("蓝色");
                lists.add(result);
    		}
    		
    	}else if(11==startNum && 20==endNum){
    		for(int i=0;i<10;i++){
    			VehicleIdentificationResult result = new VehicleIdentificationResult();
                result.setCarNo("京E222"+i);
                result.setCarType("宝马");
                result.setColor("红色");
                result.setNumberColor("蓝色");
                lists.add(result);
    		}
    	}
    	//将lists转换成json
        jsonString = JSON.toJSONString(lists);
        return jsonString;
    }
    
    public static void main(String[] args) {
    	ItemsController test = new ItemsController();
    	System.out.println(test.getHistoryVIResultList(0, 9));
	}
    
    
    
    
    
}
