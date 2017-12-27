package com.yunwang.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;

import org.apache.commons.io.IOUtils;

public class SmbFileService extends PathFileService {
	
	private String root;
	
	public SmbFileService(String root){
		this.root = root;
	}
	
	@Override
	protected FileStatus getInternal(String path) throws IOException {
		return new SmbFileStatus(root, path).connect();
	}


	@Override
	protected void writeInternal(String path, InputStream input) throws IOException {
		OutputStream output = null;
		try {
			IOUtils.copyLarge(input, (output = new SmbFileOutputStream(new SmbFile(root, path))));
		}finally{
			IOUtils.closeQuietly(output);
		}
	}

	@Override
	protected void readInternal(String path, OutputStream output) throws IOException {
		InputStream input = null;
		try {
			IOUtils.copyLarge((input = new SmbFileInputStream(new SmbFile(root, path))), output);
		}finally{
			IOUtils.closeQuietly(input);
		}
	}

	@Override
	public void destroy() throws IOException {
		// not to do
	}

}
