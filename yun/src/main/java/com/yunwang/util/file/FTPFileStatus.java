package com.yunwang.util.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPFileFilter;

public class FTPFileStatus extends DeletesFileStatus{
	
	private FTPClient ftp;
	
	private FTPFile ftpFile;
	
	public FTPFileStatus(String root, String path) {
		super(root, path);
	}
	
	public FTPFileStatus open(FTPClient ftp) throws IOException{
		this.ftp = ftp;
		
		String parent = super.parent;
		
		if(parent == null){
			parent = StringUtils.EMPTY;
		}
		
		final String filename = getName0();
		
		FTPFile[] ftpFiles = this.ftp.listFiles(this.root + parent, new FTPFileFilter() {
			@Override
			public boolean accept(FTPFile file) {
				return file.getName().equals(filename);
			}
		});
		this.ftpFile = ftpFiles.length == 0 ? null : ftpFiles[0];
		
		return this;
	}
	
	public FTPFileStatus open(FTPClient ftp,FTPFile ftpFile) throws IOException{
		this.ftp = ftp;
		this.ftpFile = ftpFile;
		return this;
	}
	
	private String getName0(){
	   int index = path.lastIndexOf(FileService.SEPARATOR);
       if(index < 0) return path;
       return path.substring(index + 1, path.length());	
	}
	
	@Override
	public String getName() {
		 return ftpFile.getName();	
	}

	@Override
	public String getParent() {
	   int index = path.lastIndexOf(FileService.SEPARATOR);
       if(index <= 0) return null;
       return path.substring(0, index);	
	}

	@Override
	public String getPath() {
		return path;
	}

	@Override
	public List<FileStatus> list() throws IOException {
		FTPFile[] listFiles = this.ftp.listFiles(this.root + path);
		
		List<FileStatus> fileStatus = new ArrayList<FileStatus>(listFiles.length);

		for (FTPFile ftpFile : listFiles) {
			fileStatus.add(new FTPFileStatus(root, path + FileService.SEPARATOR + ftpFile.getName()).open(ftp,ftpFile));
		}
		return fileStatus;
	}

	@Override
	public void mkdir() throws IOException {
		mkdir0();
	}

	private boolean mkdir0() throws IOException{
		if(exists()){
			return false;
		}
		this.ftp.makeDirectory(this.url);
		return true;
	}
	
	@Override
	public void mkdirs() throws IOException {
		mkdirs0();
	}

	
	private boolean mkdirs0() throws IOException{
		if(exists()){
			return true;
		}
		String parent = getParent();
		return (parent == null || new FTPFileStatus(root, parent).open(ftp).mkdirs0()) && mkdir0();
	}
	
	
	@Override
	public void delete() throws IOException {
		this.ftp.deleteFile(this.url);
	}

	@Override
	public boolean isFile() throws IOException {
		return this.ftpFile.isFile();
	}

	@Override
	public boolean isDirectory() throws IOException {
		return this.ftpFile.isDirectory();
	}

	@Override
	public boolean exists() throws IOException {
		return this.ftpFile != null;
	}

	@Override
	protected void deleteDirectory() throws IOException {
		this.ftp.removeDirectory(this.url);
	}

	@Override
	public void rename(String newPath) throws IOException {
		this.ftp.rename(this.url, this.root + newPath);
	}
}
