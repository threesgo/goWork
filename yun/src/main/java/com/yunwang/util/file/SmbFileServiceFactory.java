package com.yunwang.util.file;


public class SmbFileServiceFactory implements FileServiceFactory {

	private String root;
	
	@Override
	public FileService create() {
		return new SmbFileService(root);
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
}
