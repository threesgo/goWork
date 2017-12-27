package com.yunwang.util.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jcifs.smb.SmbFile;

public class SmbFileStatus extends BaseFileStatus{

	private SmbFile target;
	
	public SmbFileStatus(String root, String path) {
		super(root, path);
	}
	
	public SmbFileStatus connect() throws IOException{
		this.target = new SmbFile(root, path);
		this.target.connect();
		return this;
	}
	
	public SmbFileStatus connect(SmbFile file) throws IOException{
		this.target = file;
		return this;
	}
	
	@Override
	public String getName() {
		return target.getName();
	}

	@Override
	public List<FileStatus> list() throws IOException{
		SmbFile[] listFiles = this.target.listFiles();
		List<FileStatus> fileStatus = new ArrayList<FileStatus>(listFiles.length);
		for (SmbFile smbFile : listFiles) {
			fileStatus.add(new SmbFileStatus(super.root, super.path + FileService.SEPARATOR + smbFile.getName()).connect(smbFile));
		}
		return fileStatus;
	}

	@Override
	public void mkdir() throws IOException {
		if(!exists()){
			this.target.mkdir();
		}
	}

	@Override
	public void mkdirs() throws IOException {
		if(!exists()){
			this.target.mkdirs();
		}
	}

	@Override
	public boolean isFile() throws IOException {
		return this.target.isFile();
	}

	@Override
	public boolean isDirectory() throws IOException {
		return this.target.isDirectory();
	}

	@Override
	public void delete() throws IOException{
		this.target.delete();
	}

	@Override
	public void deletes() throws IOException {
		delete();
	}

	@Override
	public boolean exists() throws IOException{
		return this.target.exists();
	}

	@Override
	public void rename(String newPath) throws IOException {
		this.target.renameTo(new SmbFile(this.root + newPath));
		
	}

	

}
