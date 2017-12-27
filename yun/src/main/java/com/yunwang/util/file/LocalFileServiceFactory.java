package com.yunwang.util.file;



public class LocalFileServiceFactory implements FileServiceFactory {

	private String root;

	@Override
	public FileService create() {
		return new LocalFileService(root);
	}

	public void setRoot(String root) {
		this.root = root;
	}
}
