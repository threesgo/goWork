package com.yunwang.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public abstract class PathFileService implements FileService{

	@Override
	public FileStatus get(String path) throws IOException {
		return getInternal(getPath(path));
	}

	@Override
	public void write(String path, InputStream input) throws IOException {
		writeInternal(getPath(path),input);
	}

	@Override
	public void read(String path, OutputStream output) throws IOException {
		readInternal(getPath(path), output);
	}

	protected abstract FileStatus getInternal(String path) throws IOException;

	protected abstract void readInternal(String path, OutputStream output) throws IOException;
	
	protected abstract void writeInternal(String path, InputStream input) throws IOException;
	
	private String getPath(String path){
		if(path.startsWith(FileService.SEPARATOR)){
			path = path.substring(FileService.SEPARATOR.length());
		}
		return path;
	}
	
}
