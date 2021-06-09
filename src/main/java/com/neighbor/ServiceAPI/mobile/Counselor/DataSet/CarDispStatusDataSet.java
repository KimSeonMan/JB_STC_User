package com.neighbor.ServiceAPI.mobile.Counselor.DataSet;

import java.util.List;

import com.neighbor.ServiceAPI.mobile.Counselor.Domain.CarDispStatusDomain;

public class CarDispStatusDataSet {

	private int pageNo;
	private int pageCount;
	private int total;
	
	private String lon;
	private String lat;
	
	private List<CarDispStatusDomain> carDisStatusDomainList;

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

	public List<CarDispStatusDomain> getCarDisStatusDomainList() {
		return carDisStatusDomainList;
	}

	public void setCarDisStatusDomainList(List<CarDispStatusDomain> carDisStatusDomainList) {
		this.carDisStatusDomainList = carDisStatusDomainList;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}
	
	
}
