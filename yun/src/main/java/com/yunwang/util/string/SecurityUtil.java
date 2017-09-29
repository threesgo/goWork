package com.yunwang.util.string;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 刘迪 
 * 字符加密处理
 * 2014-1-26  下午3:34:41
 */
public class SecurityUtil {

	private static final String UTF8 = "UTF-8";
	
	/** 
	  * getMD5() method.
	  * @author 刘迪 
	  * @date 2014-1-26  下午3:36:02
	  * <p>MD5加密</p> 
	  * @param str 需要加密的字符串
	  * @return String  加密后的32位字符串
	*/ 
	public static String getMD5(String str){
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");//可用：MD5, SHA-1
			byte[] b = md.digest(str.getBytes(UTF8));
			return byte2HexStr(b);
		} catch (NoSuchAlgorithmException e) {
			return null;
		} catch (UnsupportedEncodingException e) {
			return null;
		}
	}
	

	private static String byte2HexStr(byte[] b) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < b.length; i++) {
			String s = Integer.toHexString(b[i] & 0xFF);
			if (s.length() == 1) {
				sb.append("0");
			}
			sb.append(s.toUpperCase());
		}
		return sb.toString();
	}
}
