package com.yunwang.util;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.util.LocalizedTextUtil;

/**
 * @author YBF
 * @date 2016-7-6
 * <p>获取国际化文件</p>
 */
public class LocalTextUtil {
	public static String getText(String arg0){
		return LocalizedTextUtil.findDefaultText(arg0,
				ServletActionContext.getRequest().getLocale());
	}
}
