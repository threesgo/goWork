package com.yunwang.util.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.apache.commons.lang3.StringUtils;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.ChannelSftp.LsEntry;
import com.jcraft.jsch.SftpException;

public class SFTPFileStatus extends DeletesFileStatus{

	private ChannelSftp sftp;

	private InternalEntry entry;
	
	public SFTPFileStatus(String root, String path) {
		super(root, path);
	}

	@SuppressWarnings("rawtypes")
	public SFTPFileStatus open(ChannelSftp sftp) throws IOException{
		this.sftp = sftp;
		
		String parent = super.parent;
		
		if(parent == null){
			parent = StringUtils.EMPTY;
		}
		final String filename = getName0();
		try {
			Vector entries = this.sftp.ls(this.root + parent);
			for (Object value : entries) {
				ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry)value;
				if(entry.getFilename().equals(filename)){
					this.entry = new InternalEntry(entry);
					break;
				}
			}
		} catch (SftpException e) {
			
		}
		if(this.entry == null){
			this.entry = new InternalEntry();
		}
		
		return this;
	}
	
	public SFTPFileStatus open(ChannelSftp sftp,InternalEntry entry) throws IOException{
		this.sftp = sftp;
		this.entry = entry;
		return this;
	}
	
	private String getName0(){
		int index = path.lastIndexOf(FileService.SEPARATOR);
		if(index < 0) return path;
		return path.substring(index + 1, path.length());	
	}
	
	@Override
	public String getName() {
		return this.entry.getName();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<FileStatus> list() throws IOException {
		try {
			List<FileStatus> fileStatus = new ArrayList<FileStatus>();
			Vector entries = this.sftp.ls(this.root + path);
			for (Object value : entries) {
				ChannelSftp.LsEntry entry = (ChannelSftp.LsEntry)value;
				if(entry.getFilename().equals(".") || entry.getFilename().equals("..")){
					continue;
				}
				fileStatus.add(new SFTPFileStatus(root, path + FileService.SEPARATOR + entry.getFilename()).open(sftp,new InternalEntry(entry)));
			}
			return fileStatus;
		} catch (SftpException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void mkdir() throws IOException {
		mkdir0();
	}

	private boolean mkdir0() throws IOException{
		if(exists()){
			return false;
		}
		try {
			this.sftp.mkdir(this.url);
			return true;
		} catch (SftpException e) {
			throw new IOException(e);
		}
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
		
		return (parent == null ||  new SFTPFileStatus(root, parent).open(this.sftp).mkdirs0()) && mkdir0();
	}
	
	
	@Override
	public void delete() throws IOException {
		try {
			this.sftp.rm(this.url);
		} catch (SftpException e) {
			throw new IOException(e);
		}
	}

	@Override
	protected void deleteDirectory() throws IOException {
		try {
			this.sftp.rmdir(this.url);
		} catch (SftpException e) {
			throw new IOException(e);
		}
	}
	
	@Override
	public boolean isFile() throws IOException {
		return this.entry.isFile();
	}

	@Override
	public boolean isDirectory() throws IOException {
		return this.entry.isDirectory();
	}

	@Override
	public boolean exists() throws IOException {
		return this.entry.exists();
	}

	@Override
	public void rename(String newPath) throws IOException {
		try {
			this.sftp.rename(this.url, this.root + newPath);
		} catch (SftpException e) {
			throw new IOException(e);
		}
	}
	
	private static class InternalEntry{

		private ChannelSftp.LsEntry entry;
		
		protected InternalEntry(){}
		
		public InternalEntry(LsEntry entry) {
			super();
			this.entry = entry;
		}

		public boolean isFile() throws IOException{
			return !entry.getAttrs().isDir();
		}
		
		public boolean isDirectory() throws IOException{
			return entry.getAttrs().isDir();
		}
		
		public boolean exists() throws IOException{
			return entry != null;
		}
		
		public String getName(){
			return entry.getFilename();
		}
	}



}
