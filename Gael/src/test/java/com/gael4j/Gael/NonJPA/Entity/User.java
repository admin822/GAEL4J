package com.gael4j.Gael.NonJPA.Entity;

import com.gael4j.Gael.Annotations.columnName;
import com.gael4j.Gael.Annotations.userdata;

@userdata(schema = "shopizer",table = "user")
public class User {
	private Integer userID;
	@columnName(name = "userName")
	private String userName;
}
 