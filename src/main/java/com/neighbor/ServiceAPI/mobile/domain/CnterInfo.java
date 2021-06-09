package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

@Alias("cnterInfo")
public class CnterInfo {

	private String cnter_cd;
	private String level;
	private String upper_cnter_cd;
	private String cnter_nm;
	private String domn;
	private String telno;
	private String mrdn_at;
	private String lc_crdnt_x;
	private String lc_crdnt_y;
	private String adres;
	private String adres_detail;
	private String zip_code;
	private String fax_telno;
	private String order_cnter;

	public String getOrder_cnter() {return order_cnter;}
	public void setOrder_cnter(String order_cnter) {this.order_cnter = order_cnter;}
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public String getUpper_cnter_cd() {
		return upper_cnter_cd;
	}
	public void setUpper_cnter_cd(String upper_cnter_cd) {
		this.upper_cnter_cd = upper_cnter_cd;
	}
	public String getCnter_nm() {
		return cnter_nm;
	}
	public void setCnter_nm(String cnter_nm) {
		this.cnter_nm = cnter_nm;
	}
	public String getDomn() {
		return domn;
	}
	public void setDomn(String domn) {
		this.domn = domn;
	}
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getMrdn_at() {
		return mrdn_at;
	}
	public void setMrdn_at(String mrdn_at) {
		this.mrdn_at = mrdn_at;
	}
	public String getLc_crdnt_x() {
		return lc_crdnt_x;
	}
	public void setLc_crdnt_x(String lc_crdnt_x) {
		this.lc_crdnt_x = lc_crdnt_x;
	}
	public String getLc_crdnt_y() {
		return lc_crdnt_y;
	}
	public void setLc_crdnt_y(String lc_crdnt_y) {
		this.lc_crdnt_y = lc_crdnt_y;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getAdres_detail() {
		return adres_detail;
	}
	public void setAdres_detail(String adres_detail) {
		this.adres_detail = adres_detail;
	}
	public String getZip_code() {
		return zip_code;
	}
	public void setZip_code(String zip_code) {
		this.zip_code = zip_code;
	}
	public String getFax_telno() {
		return fax_telno;
	}
	public void setFax_telno(String fax_telno) {
		this.fax_telno = fax_telno;
	}
}
