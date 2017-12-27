package com.yunwang.util.file;

import java.io.IOException;

public class SFTPFileServiceFactory implements FileServiceFactory {

	private String host;
	
	private int port;
	
	private String username;
	
	private String password;
	
	private String root;
	
	@Override
	public FileService create() throws IOException {
		return new SFTPFileService(root).connect(host, port, username, password);
	}

	public void setRoot(String root) {
		this.root = root;
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
