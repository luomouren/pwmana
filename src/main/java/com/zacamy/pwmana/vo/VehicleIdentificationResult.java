package com.zacamy.pwmana.vo;

/**
 * @author Amy
 * @time 20161115
 * 车辆识别结果VO
 */
public class VehicleIdentificationResult {
	private String carNo;//车牌号
	private String color;//车身颜色
	private String numberColor;//车牌颜色
	private String carType;//车型
	public String getCarNo() {
		return carNo;
	}
	public void setCarNo(String carNo) {
		this.carNo = carNo;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getNumberColor() {
		return numberColor;
	}
	public void setNumberColor(String numberColor) {
		this.numberColor = numberColor;
	}
	public String getCarType() {
		return carType;
	}
	public void setCarType(String carType) {
		this.carType = carType;
	}
	
	
}
