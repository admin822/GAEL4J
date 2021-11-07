package com.gael4j.Gael.NonJPA.Entity;

import com.gael4j.Gael.Annotations.primarykey;
import com.gael4j.Gael.Annotations.userdata;

@userdata(schema = "gael",table = "preferences")
public class Preferences {
	@primarykey
	private Integer userID;
	private String food;
	private String sports;
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getFood() {
		return food;
	}
	public void setFood(String food) {
		this.food = food;
	}
	public String getSports() {
		return sports;
	}
	public void setSports(String sports) {
		this.sports = sports;
	}
	@Override
	public String toString() {
		return "Preferences [userID=" + userID + ", food=" + food + ", sports=" + sports + "]";
	}
	
}
