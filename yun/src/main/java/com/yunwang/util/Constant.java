package com.yunwang.util;

import java.util.HashMap;
import java.util.Map;

public class Constant {
	public static final byte STATUS_AVAILABLE=1;
	public static final byte STATUS_BLOCKED=0;
	public static final String CHAR_TYPE="UTF-8";
	public static final String SESSION_ADMIN="_ADM";
	public static final Map<String,Long> validateCodeMap=new HashMap<String,Long>(); 
	
	public static final byte STRING=1;
	public static final byte NUMBER=2;
	public static final byte PIC=3;
	public static final byte VIDEO=4;
}
