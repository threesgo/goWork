package com.yunwang.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTPClient;

/**
 * @author YBF
 * @date 2017-5-23
 * <p>FTP方式文件处理</p>
 */
public class FTPFileService extends PathFileService{
	
	private String root;
	
	private FTPClient ftp;
	
	public FTPFileService(String root) {
		this.ftp = new FTPClient();
		this.root = root;
	}
	
	public FTPFileService connect(String host,int port,String username,String password) throws IOException {
		this.ftp.connect(host, port);
		this.ftp.login(username, password);
		this.ftp.enterLocalPassiveMode();
		this.ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
		return this;
	}
	
	@Override
	protected FileStatus getInternal(String path) throws IOException {
		return new FTPFileStatus(this.root, path).open(ftp);
	}

	@Override
	protected void writeInternal(String path, InputStream input) throws IOException {
		this.ftp.storeFile(this.root+FileService.SEPARATOR + path, input);
	}

	@Override
	protected void readInternal(String path, OutputStream output) throws IOException {
		ftp.retrieveFile(this.root + FileService.SEPARATOR + path, output);
	}
	
	@Override
	public void destroy() throws IOException {
		ftp.logout();
		ftp.disconnect();
	}

}
