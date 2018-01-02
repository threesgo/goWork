package com.yunwang.webservice.util;

import java.util.ArrayList;
import java.util.List;

import com.yunwang.webservice.model.Cat;

/**
 * @author yibinfang
 * 需要和map转换的构造类
 */
public class StringCat {
	
	static class Entry{
		private String key;
		private Cat cat;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public Cat getCat() {
			return cat;
		}
		public void setCat(Cat cat) {
			this.cat = cat;
		}
		
		public Entry(){
		}
		
		public Entry(String key, Cat cat) {
			super();
			this.key = key;
			this.cat = cat;
		}
	}
	
	private List<Entry> entries=new ArrayList<Entry>();

	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}
}
