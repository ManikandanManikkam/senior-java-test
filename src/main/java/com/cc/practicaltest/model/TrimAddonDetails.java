package com.cc.practicaltest.model;

import java.sql.Date;

public class TrimAddonDetails {
	private String trim_name;
	private Date effective_date;
	private String wheel_type;
	private String infotainment_type;
	private String headlight_type;
	private String upholstery_type;
	
	
	public String getTrim_name() {
		return trim_name;
	}
	public void setTrim_name(String trim_name) {
		this.trim_name = trim_name;
	}
	public Date getEffective_date() {
		return effective_date;
	}
	public void setEffective_date(Date effective_date) {
		this.effective_date = effective_date;
	}
	public String getWheel_type() {
		return wheel_type;
	}
	public void setWheel_type(String wheel_type) {
		this.wheel_type = wheel_type;
	}
	public String getInfotainment_type() {
		return infotainment_type;
	}
	public void setInfotainment_type(String infotainment_type) {
		this.infotainment_type = infotainment_type;
	}
	public String getHeadlight_type() {
		return headlight_type;
	}
	public void setHeadlight_type(String headlight_type) {
		this.headlight_type = headlight_type;
	}
	public String getUpholstery_type() {
		return upholstery_type;
	}
	public void setUpholstery_type(String upholstery_type) {
		this.upholstery_type = upholstery_type;
	}

}
