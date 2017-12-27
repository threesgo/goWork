package com.yunwang.util.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;

public class SFTPFileService extends PathFileService {

	private ChannelSftp sftp;
	
	private String root;
	
	public SFTPFileService(String root){
		this.root = root;
	}
	
	public SFTPFileService connect(String host,int port,String username,String password) throws IOException{
		
		try {
			JSch jsch = new JSch();
			Session sshSession = jsch.getSession(username, host, port);  
			sshSession.setPassword(password);  
			Properties sshConfig = new Properties();  
			sshConfig.put("StrictHostKeyChecking", "no");  
			sshSession.setConfig(sshConfig);  
			sshSession.connect();  
			Channel channel = sshSession.openChannel("sftp");  
			channel.connect();  
			this.sftp = (ChannelSftp) channel;
			return this;
		
		} catch (Exception e) {
			throw new IOException(e);
		}
	}
	
	@Override
	protected FileStatus getInternal(String path) throws IOException {
		return new SFTPFileStatus(this.root, path).open(this.sftp);
	}

	@Override
	protected void writeInternal(String path, InputStream input) throws IOException {
		try {
			this.sftp.put(input, this.root + SEPARATOR + path);
		} catch (SftpException e) {
			throw new IOException(e);
		}
	}

	@Override
	protected void readInternal(String path, OutputStream output) throws IOException {
		try {
			this.sftp.get(this.root + SEPARATOR + path,output);
		} catch (SftpException e) {
			throw new IOException(e);
		}
	}
 
	@Override
	public void destroy() throws IOException {
		this.sftp.disconnect();
	}
}
