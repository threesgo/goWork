package com.yunwang.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.stereotype.Controller;

import com.yunwang.util.action.BaseAction;
import com.yunwang.util.string.MyStringUtil;

@Controller
@Action(value = "ywzsAction")
public class ywzsAction extends BaseAction{

	/*
	 * @date 2017-11-28
	 * @author YBF
	 * TODO 公众号验证
	 */
	private static final long serialVersionUID = -3308014754516507403L;

    //APPID:wx9c8b5ea3abd36512
	//AppSecret:47be6fe2dc5e35672f331dfbddb39838
	
	//自定义的TOken(YWZS)
    public static final String Token = "ywzs";
    
    /**
     *  Function:微信验证方法
     *  @author JLC
     *  @param signature 微信加密签名
     *  @param timestamp 时间戳
     *  @param nonce     随机数
     *  @param echostr   随机字符串
     *  @return
     */
	private String  checkAuthentication(String signature,String timestamp,String nonce,String echostr) {
        String result ="";
        // 将获取到的参数放入数组
        String[] ArrTmp = { Token, timestamp, nonce };
        // 按微信提供的方法，对数据内容进行排序
        Arrays.sort(ArrTmp);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < ArrTmp.length; i++) {
            sb.append(ArrTmp[i]);
        }
        // 对排序后的字符串进行SHA-1加密
        String pwd = Encrypt(sb.toString());
        if (pwd.equals(signature)) { 
            try {
                System.out.println("微信平台签名消息验证成功！"); 
                result = echostr;
            } catch (Exception e) {
                e.printStackTrace();
            } 
        } else {
            System.out.println("微信平台签名消息验证失败！");
        }
        return result;
    }

    /**
     * 用SHA-1算法加密字符串并返回16进制串
     * 
     * @param strSrc
     * @return
     */
    private String Encrypt(String strSrc) {
        MessageDigest md = null;
        String strDes = null;
        byte[] bt = strSrc.getBytes();
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            System.out.println("错误");
            return null;
        }
        return strDes;
    }

    private String bytes2Hex(byte[] bts) {
        String des = "";
        String tmp = null;
        for (int i = 0; i < bts.length; i++) {
            tmp = (Integer.toHexString(bts[i] & 0xFF));
            if (tmp.length() == 1) {
                des += "0";
            }
            des += tmp;
        }
        return des;
    }
    
	public String execute() throws Exception {
		//验证入口
		//微信加密签名
    	String signature = getRequest().getParameter("signature");
    	//时间戳
    	String timestamp = getRequest().getParameter("timestamp");
    	//随机数
    	String nonce = getRequest().getParameter("nonce");
    	//随机字符串
    	String echostr = getRequest().getParameter("echostr") ;
    	if (MyStringUtil.isNotEmpty(echostr)) {
    		echostr = checkAuthentication(signature,timestamp,nonce,echostr); 
    		//验证通过返回随即字串
        	ajaxText(echostr);
    	}
    	return null;
	}
}
