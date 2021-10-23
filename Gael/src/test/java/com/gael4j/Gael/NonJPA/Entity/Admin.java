package com.gael4j.Gael.NonJPA.Entity;

import com.gael4j.Gael.Annotations.columnName;
import com.gael4j.Gael.Annotations.userdata;

@userdata(table = "user", schema = "shopizer")
public class Admin {
	private Integer userID;
	@columnName(name = "adminName")
	private String userName;
}
