package com.yunwang.model.page ;

import java.util.ArrayList;
import java.util.List;

public class Pager<T> {

	private int totalRows=0 ;

	private int currentPage = 1 ;
	
	private int pageSize = 10 ;

	private List<?> data ;

	private List<Integer> pages ;

	private int pageCount;
	
	public Pager () {

	}

	public Pager(int currentPage, int pageCount, List<?> data) {
		super();
		this.currentPage = currentPage;
		this.pageCount = pageCount;
		this.data = data;
		
		pages = new ArrayList<Integer>();
		if(pageCount < 6){
			for(int i=1;i<=pageCount;i++){
			    pages.add(i);
			}
		}else if(currentPage + 2 >= pageCount){
			for(int i=pageCount-4;i<=pageCount;i++){
			    pages.add(i);
			}
		}else{
			if(currentPage <= 2){
				int size = pageCount > 5 ? 5 : pageCount;
				for(int i=1;i<=size;i++){
				    pages.add(i);
				}
			}else{
				for(int i=currentPage-2;i<=currentPage+2;i++){
				    pages.add(i);
				}
			}
		}
	}

	public Pager ( int totalRows , int currentPage , int pageCount ,int pageSize, List<T> data ) {
		super ( ) ;
		this.currentPage = currentPage ;
		this.data = data ;
		this.totalRows = totalRows ;
		this.pageCount=pageCount;
		this.pageSize=pageSize;
	}
	
	
	public Pager(int totalRows, int currentPage, int pageCount, List<? extends Object> data) {
		this(currentPage, pageCount, data);
		this.totalRows = totalRows;
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
