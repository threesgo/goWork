package com.yunwang.util.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LocalFileStatus extends DeletesFileStatus{

	private File target;
	
	public LocalFileStatus(String root,String path) {
		super(root, path);
		this.target = new File(root, path);
	}

	public LocalFileStatus open(File target) {
		this.target = target;
		return this;
	}

	
	@Override
	public String getName() {
		return target.getName();
	}


	@Override
	public List<FileStatus> list() {
		File[] listFiles = target.listFiles();
		List<FileStatus> fileStatus = new ArrayList<FileStatus>(listFiles.length);
		for (File file : listFiles) {
			fileStatus.add(new LocalFileStatus(super.root,super.path + FileService.SEPARATOR+file.getName()).open(file));
		}
		return fileStatus;
	}

	@Override
	public void mkdir() {
		if(!exists()){
			target.mkdir();
		}
	}

	@Override
	public void mkdirs() {
		if(!exists()){
			target.mkdirs();
		}
	}

	@Override
	public boolean isFile() {
		return target.isFile();
	}

	@Override
	public boolean isDirectory() {
		return target.isDirectory();
	}

	@Override
	public void delete() {
		target.delete();
	}
	
	@Override
	protected void deleteDirectory() throws IOException {
		this.target.delete();
	}

	@Override
	public boolean exists() {
		return target.exists();
	}

	@Override
	public void rename(String newPath) throws IOException {
		this.target.renameTo(new File(this.root + newPath));
	}

}
