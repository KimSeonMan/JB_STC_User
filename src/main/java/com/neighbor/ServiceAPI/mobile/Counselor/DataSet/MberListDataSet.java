package com.neighbor.ServiceAPI.mobile.Counselor.DataSet;

import java.util.List;

import com.neighbor.ServiceAPI.mobile.Counselor.Domain.MberListDomain;

public class MberListDataSet {

	private int pageNo;
	private int pageCount;
	private int total;
	
	private List<MberListDomain> mberlist;

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<MberListDomain> getMberlist() {
		return mberlist;
	}

	public void setMberlist(List<MberListDomain> mberlist) {
		this.mberlist = mberlist;
	}
	
	
}
