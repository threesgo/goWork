package com.yunwang.webservice.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.yunwang.webservice.model.Cat;
import com.yunwang.webservice.util.StringCat.Entry;

//负责StringCat,Map<String,Cat>的相互转换
public class FkXmlAdapter extends XmlAdapter<StringCat,Map<String,Cat>>{

	@Override
	public Map<String, Cat> unmarshal(StringCat v) throws Exception {
		Map<String, Cat> map=new HashMap<String,Cat>();
		for(Entry e:v.getEntries()){
			map.put(e.getKey(), e.getCat());
		}
		return map;
	}

	@Override
	public StringCat marshal(Map<String, Cat> v) throws Exception {
		StringCat stringCat=new StringCat();
		for(Map.Entry<String, Cat> map:v.entrySet()){
			stringCat.getEntries().add(new Entry(map.getKey(),map.getValue()));
		}
		return stringCat;
	}
}
