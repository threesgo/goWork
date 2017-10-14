package com.yunwang.model.page ;

import java.util.List;

public class Pager<T> {

	private int totalRows=0 ;

	private int currentPage = 1 ;
	
	private int pageSize = 10 ;

	private List<T> data ;

	private List<Integer> pages ;

	private int pageCount;
	
	public Pager () {

	}


	public Pager ( int totalRows , int currentPage , int pageCount ,int pageSize, List<T> data ) {
		super ( ) ;
		this.currentPage = currentPage ;
		this.data = data ;
		this.totalRows = totalRows ;
		this.pageCount=pageCount;
		this.pageSize=pageSize;
	}
	

	public int getCurrentPage () {
		return currentPage ;
	}


	public void setCurrentPage ( int currentPage ) {


		this.currentPage = currentPage ;
	}


	public int getPageCount () {
		return pageCount ;
	}


	public int getStart () {
		return ( currentPage - 1 ) * pageSize ;
	}


	public List<?> getData () {
		return data ;
	}


	public void setData ( List<T> data ) {
		this.data = data ;
	}


	public List<Integer> getPages () {
		return pages ;
	}


	public void setPages ( List<Integer> pages ) {
		this.pages = pages ;
	}


	public int getTotalRows () {
		return totalRows ;
	}


	public void setTotalRows ( int totalRows ) {
		this.totalRows = totalRows ;
	}


	public int getPageSize () {
		return pageSize ;
	}


	public void setPageSize ( int pageSize ) {
		this.pageSize = pageSize ;
	}

}
