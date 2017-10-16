package com.yunwang.util.string;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 刘迪 
 * StringUtils工具类
 * 调用第三方工具类的话，便于维护
 * 2014-1-26  上午10:51:31
 */
public class MyStringUtil extends StringUtils{
	
	//以逗号隔开的数字字符串排序
	public static String sortIntString(String source){
		String[] arr=source.split(",");
		Integer[] intArr=new Integer[arr.length];
		for(int i=0;i<arr.length;i++){
			intArr[i]=Integer.parseInt(arr[i]);
		}
		Arrays.sort(intArr);
		List<Integer> list=Arrays.asList(intArr);
		return StringBufferByCollectionUtil.convertCollection(list);
	}
	
	
	public static String nvl(String source ,String target){
		if(source!=null){
			return source;
		}else{
			return target;
		}
	}
	
	public static String timeString(String source,int time){
		StringBuilder builder=new StringBuilder();
		for(int start=0;start<time;start++){
			builder.append(source);
		}
		return builder.toString();
	}
	
	public static String right(String source,int size,char point){
		if(source.length()>size){
			return source.substring(0,size);
		}else{
			StringBuilder builder=new StringBuilder(source);
			for(int index=0;index<size-source.length();index++){
				builder.append(point);
			}
			return builder.toString();
		}
	}
	
	public static String left(String source,int size,char point){
		int len=source.length();
		if(source.length()>size){
			return source.substring(len-size,len);
		}else{
			StringBuilder builder=new StringBuilder();
			for(int index=0;index<size-len;index++){
				builder.append(point);
			}
			builder.append(source);
			return builder.toString();
		}
	}
	
	public static String pop(String source){
		if(source.length()>0){
			return source.substring(0, source.length()-1);
		}else{
			return "";
		}
	}
	
	public static int versionCompare(String v1,String v2,String split){
		int c=v1.compareTo(v2);
		if(c==0){
			return 0;
		}
		String source=replaceOnce(v1,split, "+");
		String target=replaceOnce(v2,split, "+");
		String[] v1s=split(source, "+");
		String[] v2s=split(target, "+");
		c=Integer.valueOf(v1s[0]).compareTo(Integer.valueOf(v2s[0]));
		if(c!=0){
			return c;
		}else{
			v1s[0]="0";
			v2s[0]="0";
			return join(v1s, "+").compareTo(join(v2s, "+"));
		}
	}
	
	public static String maxVersion(String v1,String v2){
		if(versionCompare(v1, v2, ".")>0){
			return v1;
		}else{
			return v2;
		}
	}
	
	public static String maxVersion(Collection<String> lst_version){
		String temp_v="0";
		for (String version : lst_version) {
			temp_v=maxVersion(temp_v,version);
		}
		return temp_v;
	}
	
	public static String fileToString(String path) throws IOException{
		Reader reader=new FileReader(path);
		Writer writer=new StringWriter();
		IOUtils.copyLarge(reader, writer);
		return writer.toString();
	}
	
	public static String clearNote(String string){
		return string.replaceAll("--.*", "").replaceAll("\\s", " ");
	}
	
	public static String clearToken(String string){
		return string.replaceAll("\\s", " ");
	}
	
	public static String regEx(String str ){
		String regEx="[%_{}]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(str);
		if(m.find()){
			return "%\\"+str.toString()+"%";
		}else{
			return "%"+str.toString()+"%";
		}
	}
	
	public static String regExNo(String str ){
		String regEx="[%{}]"; 
		Pattern p = Pattern.compile(regEx); 
		Matcher m = p.matcher(str);
		if(m.find()){
			return "%\\"+str.toString()+"%";
		}else{
			return "%"+str.toString()+"%";
		}
	}
	
	public static String filter(String value)     
	{     
	    if(MyStringUtil.isNotBlank(value)){
	    	value = value.replace("'",""); 
	    	value = value.replace("&","");   
	    	value = value.replace("%20","");   
	    	value = value.replace("==","");   
	    	value = value.replace("--","");   
	    	value = value.replace("<","");   
	    	value = value.replace(">","");   
	    	value = value.replace("%","");  
	    }
	    return value;
	}
	
	
	/**
	 * @date 2017-8-4
	 * @author YBF
	 * @param s
	 * @return
	 * <p>字符串转json(son字符串中是不允许包含英文"与',可以过滤去除或者改为中文)</p>
	 */
	public static String stringToJson(String s) {    
        StringBuffer sb = new StringBuffer ();     
        for (int i=0; i<s.length(); i++) {     
            char c = s.charAt(i);     
            switch (c) {  
            //处理后也是报错，json中不能含有"字符
//            case '\"':     
//                sb.append("\\\"");     
//                break;     
//            case '\\':   //如果不处理单引号，可以释放此段代码，若结合下面的方法处理单引号就必须注释掉该段代码
//                sb.append("\\\\");     
//                break;     
            case '/':     
                sb.append("\\/");     
                break;     
            case '\b':      //退格
                sb.append("\\b");     
                break;     
            case '\f':      //走纸换页
                sb.append("\\f");     
                break;     
            case '\n':     
                sb.append("\\n"); //换行    
                break;     
            case '\r':      //回车
                sb.append("\\r");     
                break;     
            case '\t':      //横向跳格
                sb.append("\\t");     
                break;     
            default:     
                sb.append(c);    
            }}
        return sb.toString();     
	}
	
	/**
	 * @date 2017-5-8
	 * @author YBF
	 * @param seq 节点顺序
	 * @param pStrSeq  父级节点组合
	 * @return
	 * <p>获取组合值</p>
	 */
	public static String getCombineSeqStr(Integer seq,String pStrSeq){
		String cSeq=10000+seq+"";
		if(null!=pStrSeq){
			return pStrSeq+cSeq.substring(1,cSeq.length());
		}else{
			return cSeq.substring(1,cSeq.length());
		}
	}
}
