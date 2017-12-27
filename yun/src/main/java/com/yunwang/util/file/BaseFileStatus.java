package com.yunwang.util.file;

import org.apache.commons.lang3.StringUtils;

public abstract class BaseFileStatus implements FileStatus{

	
	/**
	 * 根目录必须一／结尾，没有程序会自动补上
	 */
	protected String root;

	protected String path;

	protected String url;

	protected String parent;
	

	public BaseFileStatus(String root,String path) {
		super();
		
		if(StringUtils.isEmpty(root)){
			root = FileService.SEPARATOR;
		}else{
			if(!root.endsWith(FileService.SEPARATOR)){
				root = root + FileService.SEPARATOR;
			}
		}
		this.root = root;
		if(StringUtils.isEmpty(path)){
			this.url = this.root;
			this.parent = null;
			this.path = StringUtils.EMPTY;
		}else{
			if(path.startsWith(FileService.SEPARATOR)){
				path = path.substring(FileService.SEPARATOR.length());
			}
			if(path.endsWith(FileService.SEPARATOR)){
				path = path.substring(0,path.length() - FileService.SEPARATOR.length());
			}
			this.path = path;
			this.url = this.root + path;
		}
		
		if(this.path.equals(StringUtils.EMPTY)){
			this.parent = null;
		}else{
			int index = path.lastIndexOf(FileService.SEPARATOR);
			if(index <= 0){
				this.parent = null;
			}else{
	    	   	this.parent = path.substring(0, index);	
			}
		}
	}

	@Override
	public String getParent() {
		return this.parent;
	}

	@Override
	public String getPath() {
		return this.path;
	}
	
	
	
	
}
