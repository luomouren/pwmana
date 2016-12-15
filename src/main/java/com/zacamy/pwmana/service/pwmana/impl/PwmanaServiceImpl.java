package com.zacamy.pwmana.service.pwmana.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sojson.common.dao.UUserMapper;
import com.sojson.common.model.URole;
import com.sojson.common.utils.LoggerUtils;
import com.sojson.core.mybatis.BaseMybatisDao;
import com.sojson.core.mybatis.page.Pagination;
import com.zacamy.pwmana.bean.Pwmana;
import com.zacamy.pwmana.mapper.pwmana.PwmanaMapper;
import com.zacamy.pwmana.service.pwmana.PwmanaService;

@Service
public class PwmanaServiceImpl extends BaseMybatisDao<PwmanaMapper> implements PwmanaService {
	
	@Autowired
	private PwmanaMapper pwMapper;
	
	@Override
	public int deleteByPrimaryKey(Long id) {
		return pwMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int insert(Pwmana record) {
		return pwMapper.insert(record);
	}

	@Override
	public Pwmana selectByPrimaryKey(Long id) {
		return pwMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Pwmana record) {
		return pwMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(Pwmana record) {
		return pwMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insertSelective(Pwmana record) {
		return pwMapper.insertSelective(record);
	}

	@Override
	public Map<String, Object> deleteById(String ids) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		try {
			int count=0;
			String resultMsg = "删除成功。";
			String[] idArray = new String[]{};
			if(StringUtils.contains(ids, ",")){
				idArray = ids.split(",");
			}else{
				idArray = new String[]{ids};
			}
			
			c:for (String idx : idArray) {
				Long id = new Long(idx);
				if(new Long(1).equals(id)){
					resultMsg = "操作成功，But'系统管理员不能删除。";
					continue c;
				}else{
					count+=this.deleteByPrimaryKey(id);
				}
			}
			resultMap.put("status", 200);
			resultMap.put("count", count);
			resultMap.put("resultMsg", resultMsg);
		} catch (Exception e) {
			LoggerUtils.fmtError(getClass(), e, "根据IDS删除用户出现错误，ids[%s]", ids);
			resultMap.put("status", 500);
			resultMap.put("message", "删除出现错误，请刷新后再试！");
		}
		return resultMap;
	}
	
	@Override
	public Pagination<Pwmana> findPage(Map<String, Object> resultMap,
			Integer pageNo, Integer pageSize) {
		return super.findPage(resultMap, pageNo, pageSize);
	}
}
