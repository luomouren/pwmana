package com.zacamy.pwmana.controller.pwmana;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.sojson.common.controller.BaseController;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.mybatis.page.Pagination;
import com.zacamy.pwmana.bean.Pwmana;
import com.zacamy.pwmana.service.pwmana.PwmanaService;

@Controller
@Scope(value="prototype")
@RequestMapping("pwmana")
public class PwmanaController extends BaseController {
	@Autowired
	PwmanaService pwmanaService;
	/**
	 * 账号信息列表
	 * @return
	 */
	@RequestMapping(value="index")
	public ModelAndView index(String findContent,ModelMap modelMap){
		modelMap.put("findContent", findContent);
		Pagination<Pwmana> pwmana = pwmanaService.findPage(modelMap,pageNo,pageSize);
		return new ModelAndView("pwmana/index","page",pwmana);
	}
	/**
	 * 账号信息记录添加
	 * @param role
	 * @return
	 */
	@RequestMapping(value="addPwmana",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> addPwmana(@ModelAttribute Pwmana pwmana){
		try {
			//第一次插入，将当前时间作为createTime
			Date now = new Date();
			SimpleDateFormat sdp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			pwmana.setUpdateTime(sdp.format(now));
			
			int count ;
			if(null==pwmana.getId()){
				pwmana.setCreateTime(sdp.format(now));
				count = pwmanaService.insertSelective(pwmana);
			}else{
				count = pwmanaService.updateByPrimaryKeySelective(pwmana);
			}
			
			resultMap.put("status", 200);
			resultMap.put("successCount", count);
		} catch (Exception e) {
			resultMap.put("status", 500);
			resultMap.put("message", "添加失败，请刷新后再试！");
			LoggerUtils.fmtError(getClass(), e, "添加账号信息记录报错。source[%s]",pwmana.toString());
		}
		return resultMap;
	}
	/**
	 * 删除账号信息记录，根据ID，但是删除账号信息记录的时候，需要查询是否有赋予给用户，如果有用户在使用，那么就不能删除。
	 * @param id
	 * @return
	 */
	@RequestMapping(value="deletePwmanaById",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> deletePwmanaById(String ids){
		return pwmanaService.deleteById(ids);
	}
	
	/**
	 * 根据账号信息ID修改
	 * @param id
	 * @return
	 */
	@RequestMapping(value="selectPwmanaById")
	@ResponseBody
	public Pwmana selectPwmanaById(Long id, Model model){
		Pwmana pwmana = pwmanaService.selectByPrimaryKey(id);
		model.addAttribute("result", pwmana);
		return pwmana;
	}
	
}
