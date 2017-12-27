package com.yunwang.util.file;

import java.io.IOException;
import java.util.List;

public interface FileStatus {

	String getName();
	
	String getParent();
	
	String getPath();
	
	List<FileStatus> list() throws IOException;;
	
	void rename(String newPath) throws IOException;
	
	void mkdir() throws IOException;
	
	void mkdirs() throws IOException;
	
	void delete() throws IOException;

	void deletes() throws IOException;
	
	boolean isFile() throws IOException;
	
	boolean isDirectory() throws IOException;
	
	boolean exists() throws IOException;

//	boolean canDeepDelete();
	
}
