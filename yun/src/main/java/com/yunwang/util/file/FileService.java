package com.yunwang.util.file;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author 				shenjunjun
 * @creatTime			2017年4月7日 上午10:26:29
 * @description			AME文件系统接口			
 */
 
public interface FileService {

	String SEPARATOR = "/";
	
	FileStatus get(String path) throws IOException;
	
	void write(String path,InputStream input) throws IOException;
	
	void read(String path,OutputStream output) throws IOException;
	
	void destroy() throws IOException;
	
}
