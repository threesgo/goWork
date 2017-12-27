package com.yunwang.util.file;

import java.io.IOException;

/**
 * @author 				shenjunjun
 * @creatTime			2017年5月27日 下午4:38:17
 * @description			
 */
 
public interface FileServiceFactory {
	
	FileService create() throws IOException;
	
}
