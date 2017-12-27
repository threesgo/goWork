package com.yunwang.util.file;

import java.io.IOException;
import java.util.List;


public abstract class DeletesFileStatus extends BaseFileStatus {

	public DeletesFileStatus(String root, String path) {
		super(root, path);
	}

	@Override
	public void deletes() throws IOException {
		deleltes0();
	}

	@SuppressWarnings("unchecked")
	private boolean deleltes0() throws IOException{
		if(isFile()){
			delete();
			return true;
		}else{
			return deletes0((List<? extends DeletesFileStatus>) list()) && deleteDirectory0();
		}
	}
	
	private boolean deletes0(List<? extends DeletesFileStatus> fileStatuses) throws IOException{
		boolean point = true;
		for (DeletesFileStatus fileStatus : fileStatuses) {
			point = point && fileStatus.deleltes0();
		}
		return point;
	}
	
	private boolean deleteDirectory0() throws IOException{
		deleteDirectory();
		return true;
	}
	
	protected abstract void deleteDirectory() throws IOException;
}
