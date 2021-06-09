package com.neighbor.ServiceAPI.mobile.LocInfoSystem.DataSet;

import java.util.ArrayList;
import java.util.List;

import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocDrverInfoDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocInfoDomain;
import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain.LocInfoLcDomain;

public class LocInfoDataSet {

	private int pageNo;
	private int pageCount;
	private int total;
	
	private String lcCrdntX;
	private String lcCrdntY;
	
	private String sel_mber_id;
	
	private List<LocInfoDomain> locInfoDomainList;
	private List<LocInfoLcDomain> locInfoLcDomainList;
	private List<LocDrverInfoDomain> locDrverList;
	
	public LocInfoDataSet(){
		this.pageNo = 1;
		this.pageCount = 0;
		this.total = 0;
		this.locInfoDomainList = new ArrayList<LocInfoDomain>();
		this.locInfoLcDomainList = new ArrayList<LocInfoLcDomain>();
		this.locDrverList = new ArrayList<LocDrverInfoDomain>();
	}
	
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

	public List<LocInfoDomain> getLocInfoDomainList() {
		return locInfoDomainList;
	}

	public void setLocInfoDomainList(List<LocInfoDomain> locInfoDomainList) {
		this.locInfoDomainList = locInfoDomainList;
	}

	public List<LocInfoLcDomain> getLocInfoLcDomainList() {
		return locInfoLcDomainList;
	}

	public void setLocInfoLcDomainList(List<LocInfoLcDomain> locInfoLcDomainList) {
		this.locInfoLcDomainList = locInfoLcDomainList;
	}

	public String getLcCrdntX() {
		return lcCrdntX;
	}

	public void setLcCrdntX(String lcCrdntX) {
		this.lcCrdntX = lcCrdntX;
	}

	public String getLcCrdntY() {
		return lcCrdntY;
	}

	public void setLcCrdntY(String lcCrdntY) {
		this.lcCrdntY = lcCrdntY;
	}

	public List<LocDrverInfoDomain> getLocDrverList() {
		return locDrverList;
	}

	public void setLocDrverList(List<LocDrverInfoDomain> locDrverList) {
		this.locDrverList = locDrverList;
	}

	public String getSel_mber_id() {
		return sel_mber_id;
	}

	public void setSel_mber_id(String sel_mber_id) {
		this.sel_mber_id = sel_mber_id;
	}

}
