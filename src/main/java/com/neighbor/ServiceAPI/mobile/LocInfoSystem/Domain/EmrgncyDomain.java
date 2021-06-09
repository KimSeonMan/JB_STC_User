package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain;

import org.apache.ibatis.type.Alias;

@Alias("emrgncyDomain")
public class EmrgncyDomain {

	private String mber_id;
	private String lon;
	private String lat;
	private String cnter_cd;
	private String mber_name;
	private String cnter_nm;
	private String mber_se_nm;
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
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
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public String getMber_name() {
		return mber_name;
	}
	public void setMber_name(String mber_name) {
		this.mber_name = mber_name;
	}
	public String getCnter_nm() {
		return cnter_nm;
	}
	public void setCnter_nm(String cnter_nm) {
		this.cnter_nm = cnter_nm;
	}
	public String getMber_se_nm() {
		return mber_se_nm;
	}
	public void setMber_se_nm(String mber_se_nm) {
		this.mber_se_nm = mber_se_nm;
	}
	@Override
	public String toString() {
		return "EmrgncyDomain [mber_id=" + mber_id + ", lon=" + lon + ", lat=" + lat + ", cnter_cd=" + cnter_cd
				+ ", mber_name=" + mber_name + ", cnter_nm=" + cnter_nm + ", mber_se_nm=" + mber_se_nm + "]";
	}
	
}
