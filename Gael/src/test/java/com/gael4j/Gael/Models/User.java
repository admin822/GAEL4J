package com.gael4j.Gael.Models;

import com.gael4j.Gael.Annotations.userdata;

@userdata(schema = "user")
public class User {
	@columnName
	private Integer userIdInteger;
	@exclude
	private String userNameString;
}
 