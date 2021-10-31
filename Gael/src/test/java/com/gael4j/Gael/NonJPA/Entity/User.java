package com.gael4j.Gael.NonJPA.Entity;

import com.gael4j.Gael.Annotations.columnName;
import com.gael4j.Gael.Annotations.userdata;

@userdata(schema = "gael",table = "users")
public class User {
	private Integer userID;
	@columnName(name = "user_name")
	private String userName;
	@Override
	public String toString() {
		return "User [userID=" + userID + ", userName=" + userName + "]";
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
 