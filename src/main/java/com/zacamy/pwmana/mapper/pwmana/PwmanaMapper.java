package com.zacamy.pwmana.mapper.pwmana;

import java.util.Map;

import com.sojson.core.mybatis.page.Pagination;
import com.zacamy.pwmana.bean.Pwmana;

public interface PwmanaMapper {
	/**
	 * 根据主键删除
	 * @param id 主键
	 * @return
	 */
	int deleteByPrimaryKey(Long id);

    /**
     * 插入记录
     * @param record 账号信息
     * @return
     */
    int insert(Pwmana record);
    /**
     * 插入记录--判断属性是否为空
     * @param record 账号信息
     * @return
     */
    int insertSelective(Pwmana record);

    /**
     * 根据主键id查找记录
     * @param id 主键
     * @return
     */
    Pwmana selectByPrimaryKey(Long id);

    /**
     * 更新记录（属性校验非空，不为空才更新）
     * @param record 账号信息
     * @return
     */
    int updateByPrimaryKeySelective(Pwmana record);
    /**
     * 更新记录
     * @param record 账号信息
     * @return
     */
    int updateByPrimaryKey(Pwmana record);
    
	/**
	 * 分页查找
	 * @param resultMap 结果Map
	 * @param pageNo 页数
	 * @param pageSize 总数
	 * @return
	 */
	Pagination<Pwmana> findPage(Map<String, Object> resultMap, Integer pageNo,
			Integer pageSize);



}