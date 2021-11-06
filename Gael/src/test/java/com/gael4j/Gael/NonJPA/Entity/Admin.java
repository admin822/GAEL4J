package com.gael4j.Gael.NonJPA.Entity;

import com.gael4j.Gael.Annotations.columnName;
import com.gael4j.Gael.Annotations.primarykey;
import com.gael4j.Gael.Annotations.userdata;

@userdata(table = "admins", schema = "gael")
public class Admin {
	private Integer userID;
	@columnName(name = "admin_name")
	private String userName;
	@Override
	public String toString() {
		return "Admin [userID=" + userID + ", userName=" + userName + "]";
	}
	public Integer getUserID() {
		return userID;
	}
	public void setUserID(Integer userID) {
		this.userID = userID;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
}
