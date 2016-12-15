package com.zacamy.pwmana.bean;

import java.io.Serializable;
import java.util.Date;
/**
 * @author Amy
 * 账号信息
 */
public class Pwmana implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long id;//主键
	private String loginName;//登录名
	private String password;//密码
	private String email;//绑定邮箱
	private String phone;//绑定手机号
	private String siteName;//网址名称
	private String siteUrl;//网址
	private String remarks;//备注
	private String registerTime;//注册日期
	private String createTime;//创建时间
	private String updateTime;//更新时间
	
	public Pwmana() {
		super();
	}
	
	public Pwmana(Long id, String loginName, String password, String email, String phone, String siteName,
			String siteUrl, String remarks, String registerTime, String createTime, String updateTime) {
		super();
		this.id = id;
		this.loginName = loginName;
		this.password = password;
		this.email = email;
		this.phone = phone;
		this.siteName = siteName;
		this.siteUrl = siteUrl;
		this.remarks = remarks;
		this.registerTime = registerTime;
		this.createTime = createTime;
		this.updateTime = updateTime;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getSiteName() {
		return siteName;
	}
	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}
	public String getSiteUrl() {
		return siteUrl;
	}
	public void setSiteUrl(String siteUrl) {
		this.siteUrl = siteUrl;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getRegisterTime() {
		return registerTime;
	}
	public void setRegisterTime(String registerTime) {
		this.registerTime = registerTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
