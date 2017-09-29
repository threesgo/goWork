package com.yunwang.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author YBF
 * @date 2017-8-3
 * <p>正则工具类</p>
 */
public class PatternUtil {
	
	private PatternUtil(){
		
	}
	
	//一个或多个汉字
	public static String HASCHIANESS_REG = "^[\u0391-\uFFE5]+$";
	//邮政编码
	public static String POSTALCODE__REG = "^[1-9]\\d{5}$";
	//QQ号码
	public static String QQNUMBER_REG = "^[1-9]\\d{4,10}$";
	//邮箱
	public static String EMAIL_REG = "[a-zA-Z_]{1,}[0-9]{0,}@(([a-zA-z0-9]-*){1,}\\.){1,3}[a-zA-z\\-]{1,}";
	//手机号码
	public static String PHONE_REG = "^1[3|4|5|8][0-9]\\d{8}$";
	//URL
	public static String URL_REG = "^((http|https)://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?$";
	//18位身份证号
	public static String IDNUMNER_REG = "^(\\d{6})(18|19|20)?(\\d{2})([01]\\d)([0123]\\d)(\\d{3})(\\d|X|x)?$";
	//特殊字符
	public static String SPECIALCHARS_REG  = "[_`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\n|\r|\t";
	//需要转义的字符
	public static char[] ESCAPE_CHARS = {'$', '(', ')', '*', '+', '.', '[', ']', '?', '\\', '^', '{', '}', '|'};
	
	/**
	 * @date 2017-8-3
	 * @author YBF
	 * @param str  字符串
	 * @param regEx 正则表达式
	 * @return
	 * <p>正则验证</p>
	 */
	public static boolean validate(String str,String regEx){
		 /*Pattern pattern = Pattern.compile(regEx);
	   	 Matcher matcher = pattern.matcher(str);
	   	 return matcher.matches();*/
		return str.matches(regEx);
	}

	/**
	 * @date 2017-8-3
	 * @author YBF
	 * @param str 字符串变量
	 * @param regEx  正则
	 * @return
	 * <p>根据正则匹配获取子字符串</p>
	 */
	public static List<String> getMatcherStr(String str,String regEx){
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        ArrayList<String> strs = new ArrayList<String>();
        while (m.find()) {
            strs.add(m.group(1));            
        } 
        return strs;
	}

	/**
	 * @date 2017-8-3
	 * @author YBF
	 * @param str 字符串变量
	 * @param regEx 正则
	 * @return
	 * <p>根据正则替换字符串</p>
	 */
	public static String replace(String str,String regEx,String replace){
		Pattern pattern = Pattern.compile(regEx); 
	    Matcher matcher = pattern.matcher(str); 
	    StringBuffer sbr = new StringBuffer(); 
	    while (matcher.find()) { 
	        matcher.appendReplacement(sbr,replace); 
	    } 
	    matcher.appendTail(sbr);
		return sbr.toString();
	}
	
	
	
	
	public static void main(String[] args) {
//		// 要验证的字符串
//	    String str = "51333";
//	    // 邮箱验证规则
//	    String regEx = "^[1-9]\\d{4,10}$";
//	    // 编译正则表达式
//	    Pattern pattern = Pattern.compile(regEx);
//	    // 忽略大小写的写法
//	    // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
//	    Matcher matcher = pattern.matcher(str);
//	    // 字符串是否与正则表达式相匹配
//	    boolean rs = matcher.matches();
//	    System.out.println(rs);
	    
	    Pattern pattern = Pattern.compile("正则表达式"); 
	    Matcher matcher = pattern.matcher("正则表达式 Hello World,正则表达式 Hello World "); 
	    StringBuffer sbr = new StringBuffer(); 
	    while (matcher.find()) { 
	        matcher.appendReplacement(sbr, "Java"); 
	    } 
	    matcher.appendTail(sbr); 
	}

//    public static void main(String[] args) {
//        getStrings(); //用正则表达式获取指定字符串内容中的指定内容
//        System.out.println("********************");
//        replace(); //用正则表达式替换字符串内容
//        System.out.println("********************");
//        strSplit(); //使用正则表达式切割字符串
//        System.out.println("********************");
//        strMatch(); //字符串匹配
//    }
//
//    private static void strMatch() {
//        String phone = "13539770000";
//        //检查phone是否是合格的手机号(标准:1开头，第二位为3,5,8，后9位为任意数字)
//        System.out.println(phone + ":" + phone.matches("1[358][0-9]{9,9}")); //true    
//        
//        String str = "abcd12345efghijklmn";
//        //检查str中间是否包含12345
//        System.out.println(str + ":" + str.matches("\\w+12345\\w+")); //true
//        System.out.println(str + ":" + str.matches("\\w+123456\\w+")); //false
//    }
//
//    private static void strSplit() {
//        String str = "asfasf.sdfsaf.sdfsdfas.asdfasfdasfd.wrqwrwqer.asfsafasf.safgfdgdsg";
//        String[] strs = str.split("\\.");
//        for (String s : strs){
//            System.out.println(s);
//        }        
//    }
//
//    private static void getStrings() {
//        String str = "rrwerqq84461376qqasfdasdfrrwerqq84461377qqasfdasdaa654645aafrrwerqq84461378qqasfdaa654646aaasdfrrwerqq84461379qqasfdasdfrrwerqq84461376qqasfdasdf";
//        Pattern p = Pattern.compile("qq(.*?)qq");
//        Matcher m = p.matcher(str);
//        ArrayList<String> strs = new ArrayList<String>();
//        while (m.find()) {
//            strs.add(m.group(1));            
//        } 
//        for (String s : strs){
//            System.out.println(s);
//        }        
//    }
//
//    private static void replace() {
//        String str = "asfas5fsaf5s4fs6af.sdaf.asf.wqre.qwr.fdsf.asf.asf.asf";
//        //将字符串中的.替换成_，因为.是特殊字符，所以要用\.表达，又因为\是特殊字符，所以要用\\.来表达.
//        str = str.replaceAll("\\.", "_");
//        System.out.println(str);        
//    }
}
