package com.yunwang.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

public class LocalFileService extends PathFileService {

	private String root;
	
	public LocalFileService(String root) {
		super();
		this.root = root;
	}

	@Override
	protected FileStatus getInternal(String path) throws IOException {
		return new LocalFileStatus(root,path);
	}

	@Override
	protected void writeInternal(String path, InputStream input) throws IOException {
		OutputStream output = null;
		try {
			IOUtils.copyLarge(input, (output = new FileOutputStream(new File(root,path))));
		}finally{
			IOUtils.closeQuietly(output);
		}
	}

	@Override
	protected void readInternal(String path, OutputStream output) throws IOException {
		InputStream input = null;
		try {
			IOUtils.copyLarge((input = new FileInputStream(new File(root,path))), output);
		}finally{
			IOUtils.closeQuietly(input);
		}
	}

	@Override
	public void destroy() throws IOException {
		
	}

}
