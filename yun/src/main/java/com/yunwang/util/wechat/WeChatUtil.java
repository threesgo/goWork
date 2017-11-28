package com.yunwang.util.wechat;

import java.awt.Menu;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yunwang.util.http.MyX509TrustManager;

/**
 * 公众平台通用接口工具类
 * 
 * @date 2013-08-09
 */
public class WeChatUtil {
	private static Logger log = LoggerFactory.getLogger(WeChatUtil.class);
	
	//睿敏
//	public static String APPID = "wxab1a851d2c482cff";//你的APPID
//	public static String APPSECRET = "fd36e174f85f7b7f137b02d2a21d30d2"; //你的APPSECRET
	
	//OEC
	public static String APPID = "wx16431ac6639c689b";//你的APPID
	public static String APPSECRET = "e5c7cf3623cdc4c7ab6fe12e1d811a0e"; //你的APPSECRET
	
	//yibinfang
	//public static String APPID = "wx16431ac6639c689b";//你的APPID
	//public static String APPSECRET = "e5c7cf3623cdc4c7ab6fe12e1d811a0e"; //你的APPSECRET
	
	public final static String get_pentId_url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
	
	// 获取access_token的接口地址（GET） 限200（次/天）
	public final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";

	
	public static String getOpenId(String appId,String secret,String code){
        String requestUrl=get_pentId_url.replace("APPID", appId).replace("SECRET", secret).replace("CODE", code);
        System.out.println(code);
        JSONObject jsonObj = httpRequest(requestUrl,"GET",null);
        String openid = jsonObj.get("openid").toString();
        return openid;
	}
	
	/**
	 * 发起https请求并获取结果
	 * 
	 * @param requestUrl 请求地址
	 * @param requestMethod 请求方式（GET、POST）
	 * @param outputStr 提交的数据
	 * @return JSONObject(通过JSONObject.get(key)的方式获取json对象的属性值)
	 */
	public static JSONObject httpRequest(String requestUrl, String requestMethod, String outputStr) {
		JSONObject jsonObject = null;
		StringBuffer buffer = new StringBuffer();
		try {
			// 创建SSLContext对象，并使用我们指定的信任管理器初始化
			TrustManager[] tm = { new MyX509TrustManager() };
			
			//SSLContext 创建安全套接字实例
			SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
			
			/* 初始化此上下文。前两个参数都可以为 null，在这种情况下将搜索装入的安全提供者来寻找适当工厂的最高优先级实现。
			 * 同样，安全的 random 参数也可以为 null，在这种情况下将使用默认的实现。
			 * */
			sslContext.init(null, tm, new java.security.SecureRandom());
			
			// 从上述SSLContext对象中得到SSLSocketFactory对象
			SSLSocketFactory ssf = sslContext.getSocketFactory();

			URL url = new URL(requestUrl);
			HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
			httpUrlConn.setSSLSocketFactory(ssf);

			httpUrlConn.setDoOutput(true);
			httpUrlConn.setDoInput(true);
			httpUrlConn.setUseCaches(false);
			// 设置请求方式（GET/POST）
			httpUrlConn.setRequestMethod(requestMethod);

			if ("GET".equalsIgnoreCase(requestMethod))
				//打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）
				httpUrlConn.connect();

			// 当有数据需要提交时
			if (null != outputStr) {
				OutputStream outputStream = httpUrlConn.getOutputStream();
				// 注意编码格式，防止中文乱码
				outputStream.write(outputStr.getBytes("UTF-8"));
				outputStream.close();
			}

			// 将返回的输入流转换成字符串
			InputStream inputStream = httpUrlConn.getInputStream();
			InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

			String str = null;
			while ((str = bufferedReader.readLine()) != null) {
				buffer.append(str);
			}
			bufferedReader.close();
			inputStreamReader.close();
			// 释放资源
			inputStream.close();
			inputStream = null;
			httpUrlConn.disconnect();
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (ConnectException ce) {
			log.error("wechat server connection timed out.");
		} catch (Exception e) {
			log.error("https request error:{}", e);
		}
		return jsonObject;
	}
	
	/**
	 * 获取access_token
	 * 
	 * @param appid 凭证
	 * @param appsecret 密钥
	 * @return
	 */
	public static AccessToken getAccessToken(String appid, String appsecret) {
		AccessToken accessToken = null;

		String requestUrl = access_token_url.replace("APPID", appid).replace("APPSECRET", appsecret);
		JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
		// 如果请求成功
		if (null != jsonObject) {
			try {
				accessToken = new AccessToken();
				accessToken.setToken(jsonObject.getString("access_token"));
				accessToken.setExpiresIn(jsonObject.getInt("expires_in"));
			} catch (Exception e) {
				accessToken = null;
				// 获取token失败
				log.error("获取token失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return accessToken;
	}
	
	
	
	//获取ticket API
    //https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
    public static String getTicket(String access_token){
    	 String requestUrl="https://api.weixin.qq.com/cgi-bin/ticket/getticket?" +
	 		"access_token="+access_token+"&type=jsapi";
    	 JSONObject jsonObject = httpRequest(requestUrl, "GET", null);
         return jsonObject.getString("ticket");
    }
	
	
	// 菜单创建（POST） 限100（次/天）
	public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";
	
	//public static String menu_create_url = "https://api.weixin.qq.com/cgi-bin/get_current_selfmenu_info?access_token=ACCESS_TOKEN";

	/**
	 * 创建菜单
	 * 
	 * @param menu 菜单实例
	 * @param accessToken 有效的access_token
	 * @return 0表示成功，其他值表示失败
	 */
	public static int createMenu(Menu menu, String accessToken) {
		int result = 0;

		// 拼装创建菜单的url
		String url = menu_create_url.replace("ACCESS_TOKEN", accessToken);
		// 将菜单对象转换成json字符串
		String jsonMenu = JSONObject.fromObject(menu).toString();
		// 调用接口创建菜单
		JSONObject jsonObject = httpRequest(url, "POST", jsonMenu);

		if (null != jsonObject) {
			if (0 != jsonObject.getInt("errcode")) {
				result = jsonObject.getInt("errcode");
				log.error("创建菜单失败 errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
			}
		}
		return result;
	}
	
	public static String uploadUrl = "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token=ACCESS_TOKEN&type=image";
	
	public static JSONObject upload(String imagePath,Integer picType,String accessToken) throws Exception{
		//String filePath = "C:/Users/Administrator/Desktop/1.jpg";//本地或服务器文件路径
		String url = uploadUrl.replace("ACCESS_TOKEN", accessToken);
		JSONObject obj =httpUpload(url, imagePath,picType);
		return obj;
	}
	
	
	 /**   
	 * @Title: getPicUrl   
	 * @Description: TODO(获取远程url的inputStream)   
	 * @param: @param urlStr
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: InputStream      
	 * @throws   
	 */
	public static InputStream  getPicUrl(String urlStr) throws Exception{  
	        URL url = new URL(urlStr);    
	        HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
            //设置超时间为3秒  
	        conn.setConnectTimeout(10*1000);  
	        //防止屏蔽程序抓取而返回403错误  
	        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	        //得到输入流  
	        InputStream inputStream = conn.getInputStream();    
	       return inputStream;
    }  
	
	public static JSONObject httpUpload(String url, String filePath, Integer picType) throws Exception {
		JSONObject jsonObject = null;
		/**
		 * 第一部分
		 */
		URL urlObj = new URL(url);
		// 连接
		HttpURLConnection con = (HttpURLConnection) urlObj.openConnection();
		/**
		 * 设置关键值
		 */
		con.setRequestMethod("POST"); // 以Post方式提交表单，默认get方式
		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false); // post方式不能使用缓存
		// 设置请求头信息
		con.setRequestProperty("Connection", "Keep-Alive");
		con.setRequestProperty("Charset", "UTF-8");
		// 设置边界
		String BOUNDARY = "---------------------------" + System.currentTimeMillis();
		con.setRequestProperty("Content-Type", "multipart/form-data; boundary="
				+ BOUNDARY);
		// 请求正文信息
		
		
		InputStream in=null;
		File file=null;
		String fileName="";
		if(picType==1){
			file = new File(filePath);
			if (!file.exists() || !file.isFile()) {
				return jsonObject; 
				//throw new IOException("文件不存在");
			}else{
				//in = new DataInputStream(new FileInputStream(file));
				in = new DataInputStream(new FileInputStream(file));
				fileName=file.getName();
			}
		}else{
			in=getPicUrl(filePath);
			fileName=filePath.split("\\/")[filePath.split("\\/").length-1];
		}
		// 第一部分：
		StringBuilder sb = new StringBuilder();
		sb.append("--"); // 必须多两道线
		sb.append(BOUNDARY);
		sb.append("\r\n");
		sb.append("Content-Disposition: form-data;name=\"media\";filename=\""
				+ fileName + "\"\r\n");
		sb.append("Content-Type:application/octet-stream\r\n\r\n");
		byte[] head = sb.toString().getBytes("utf-8");
		// 获得输出流
		OutputStream out = new DataOutputStream(con.getOutputStream());
		// 输出表头
		out.write(head);
		// 文件正文部分
		// 把文件已流文件的方式 推入到url中
		int bytes = 0;
		byte[] bufferOut = new byte[1024];
		while ((bytes = in.read(bufferOut)) != -1) {
			out.write(bufferOut, 0, bytes);
		}
		in.close();
		// 结尾部分
		byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
		out.write(foot);

		out.flush();
		out.close();

		StringBuffer buffer = new StringBuffer();
		BufferedReader reader = null;
		try {
			// 定义BufferedReader输入流来读取URL的响应
			reader = new BufferedReader(new InputStreamReader(
					con.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			jsonObject = JSONObject.fromObject(buffer.toString());
		} catch (Exception e) {
			/*System.out.println("发送POST请求出现异常！" + e);
			e.printStackTrace();
			throw new Exception("数据读取异常");*/
			return jsonObject; 
		} finally {
			if (reader != null) {
				reader.close();
			}
		}
		return jsonObject;
	}
}