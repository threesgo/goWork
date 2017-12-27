package com.yunwang.util.file;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


/**
 * @author 				shenjunjun
 * @creatTime			2017年5月27日 下午9:22:11
 * @description		文件提供者
 */
public class FileSupport {

	private	 static FileServiceFactory factory; 

	public static final String CHARACTER_SET = "utf8";
	
	private static final String JOIN = "/";
	
	/**
	 * @author 				shenjunjun
	 * @creatTime			2017年5月27日 下午9:18:54
	 * @description		根据URI读取文件二进制流	
	 * @param uri			资源定位符
	 * @return	
	 */
	public static byte[] read(String uri) throws IOException{
		FileService fileService = null;
		try {
			fileService = factory.create();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			fileService.read(uri,outputStream);
			return outputStream.toByteArray();
		} finally{
			destroy(fileService);
		}
	}
	

	/**
	 * @author 				shenjunjun
	 * @creatTime			2017年5月27日 下午10:07:18
	 * @description		读取字符串
	 * @param uri			文件定位符
	 * @return
	 * @throws IOException	
	 */
	public static String readString(String uri) throws IOException{
		return new String(read(uri),CHARACTER_SET);
	}
	/**
	 * @author 				shenjunjun
	 * @creatTime			2017年5月27日 下午9:21:25
	 * @description		写入指定定位符
	 * @param uri			定位符
	 * @param content		内容数据　二进制数据
	 * @throws IOException	
	 */
	public static void write(String uri,byte[] content) throws IOException{
		FileService fileService = null;
		try {
			fileService = factory.create();
			fileService.write(uri,new ByteArrayInputStream(content));
		}finally{
			destroy(fileService);
		}
	}
	
	
	public static void fastWrite(FileSupport.Accept accept) throws IOException{
		batch(accept);
	}
	
	public static void batch(FileSupport.Accept accept) throws IOException{
		FileService fileService = null;
		try {
			fileService = factory.create();
			accept.accept(fileService);
		}finally{
			destroy(fileService);
		}
	}
	
	public static String join(String uri,String fileName){
		return uri + JOIN + fileName;
		
	}
	
	
	public static void copy(FileService fileService,String srcUri,String dscUri) throws IOException{
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		
		fileService.read(srcUri, outputStream);
		
		ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
		
		fileService.write(dscUri,inputStream);
		
	}
	
	/**
	 * @author 				shenjunjun
	 * @creatTime			2017年5月27日 下午11:01:52
	 * @description			以输入流写入文件描述符
	 * @param uri			文件描述符
	 * @param content		输入流
	 * @throws IOException	
	 */
	public static void write(String uri,InputStream content) throws IOException{
		FileService fileService = null;
		try {
			fileService = factory.create();
			fileService.write(uri,content);
		}finally{
			destroy(fileService);
		}
	}
	
	/**
	 * @author 				shenjunjun
	 * @creatTime			2017年5月27日 下午10:03:44
	 * @description		文件定位符是否存在
	 * @param uri			文件定位符
	 * @return	
	 */
	public static boolean exists(String uri) throws IOException{
		FileService fileService = null;
		try {
			fileService = factory.create();
			return fileService.get(uri).exists();
		}finally{
			destroy(fileService);
		}
	}
	
	
	public static void mkdir(String uri) throws IOException{
		FileService fileService = null;
		try {
			fileService = factory.create();
			fileService.get(uri).mkdir();
		}finally{
			destroy(fileService);
		}
	}
	
	
	public static void mkdirs(String uri) throws IOException{
		FileService fileService = null;
		try {
			fileService = factory.create();
			fileService.get(uri).mkdirs();
		}finally{
			destroy(fileService);
		}
	}
	

	public static void mkdirs(FileService fileService, String uri) throws IOException {
		fileService.get(uri).mkdirs();
	}

	public static void write(FileService fileService, String uri,byte[] content) throws IOException {
		fileService.write(uri,new ByteArrayInputStream(content));
	}


	public static void write(FileService fileService, String uri,InputStream content) throws IOException {
		fileService.write(uri,content);
	}
	
	
	
	
	/**
	 * @author 					shenjunjun
	 * @creatTime				2017年5月27日 下午9:22:48
	 * @description			删除指定文件描述
	 * @param uri				文件描述
	 * @throws IOException	
	 */
	public static void delete(FileService fileService,String uri) throws IOException{
		FileStatus fileStatus = fileService.get(uri);
		if(fileStatus.exists()){
			fileStatus.delete();
		}
	}
	
	
	/**
	 * @author 					shenjunjun
	 * @creatTime				2017年5月27日 下午9:22:48
	 * @description			删除指定文件描述
	 * @param uri				文件描述
	 * @throws IOException	
	 */
	public static void delete(String uri) throws IOException{
		FileService fileService = null;
		try {
			fileService = factory.create();
			FileStatus fileStatus = fileService.get(uri);
			if(fileStatus.exists()){
				fileStatus.delete();
			}
		}finally{
			destroy(fileService);
		}
	}
	
	private static void destroy(FileService fileService) throws IOException {
		if(fileService != null){
			fileService.destroy();
		}
	}

	public void setFactory(FileServiceFactory factory) {
		FileSupport.factory = factory;
	}

	public static interface Accept{
		void accept(FileService fileService) throws IOException;
	}

}
