package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

import com.neighbor.ServiceAPI.mobile.util.Page;

@Alias("mberInfo")
public class MberInfo {

	private String mber_id;	
	private String cnter_cd;	
	private String mber_se_cd;	
	private String mber_sttus_cd;	
	private String password;	
	private String mber_nm;	
	private String sexdetn;	
	private String nlty;	
	private String brthdy;
	private String zip;	
	private String adres;	
	private String adres_detail;	
	private String telno;	
	private String mbtlnum;	
	private String email;	
	private String sms_recptn_at;	
	private String register_id;	
	private String regist_dt;
	private String Key;
	private String cnter_name;
	private Page page = new Page();
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
	}
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public String getMber_se_cd() {
		return mber_se_cd;
	}
	public void setMber_se_cd(String mber_se_cd) {
		this.mber_se_cd = mber_se_cd;
	}
	public String getMber_sttus_cd() {
		return mber_sttus_cd;
	}
	public void setMber_sttus_cd(String mber_sttus_cd) {
		this.mber_sttus_cd = mber_sttus_cd;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getMber_nm() {
		return mber_nm;
	}
	public void setMber_nm(String mber_nm) {
		this.mber_nm = mber_nm;
	}
	public String getSexdetn() {
		return sexdetn;
	}
	public void setSexdetn(String sexdetn) {
		this.sexdetn = sexdetn;
	}
	public String getNlty() {
		return nlty;
	}
	public void setNlty(String nlty) {
		this.nlty = nlty;
	}
	public String getBrthdy() {
		return brthdy;
	}
	public void setBrthdy(String brthdy) {
		this.brthdy = brthdy;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
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
	public String getTelno() {
		return telno;
	}
	public void setTelno(String telno) {
		this.telno = telno;
	}
	public String getMbtlnum() {
		return mbtlnum;
	}
	public void setMbtlnum(String mbtlnum) {
		this.mbtlnum = mbtlnum;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSms_recptn_at() {
		return sms_recptn_at;
	}
	public void setSms_recptn_at(String sms_recptn_at) {
		this.sms_recptn_at = sms_recptn_at;
	}
	public String getRegister_id() {
		return register_id;
	}
	public void setRegister_id(String register_id) {
		this.register_id = register_id;
	}
	public String getRegist_dt() {
		return regist_dt;
	}
	public void setRegist_dt(String regist_dt) {
		this.regist_dt = regist_dt;
	}
	public String getKey() {
		return Key;
	}
	public void setKey(String key) {
		Key = key;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public String getCnter_name() {
		return cnter_name;
	}
	public void setCnter_name(String cnter_name) {
		this.cnter_name = cnter_name;
	}
	@Override
	public String toString() {
		return "MberInfo [mber_id=" + mber_id + ", cnter_cd=" + cnter_cd + ", mber_se_cd=" + mber_se_cd
				+ ", mber_sttus_cd=" + mber_sttus_cd + ", password=" + password + ", mber_nm=" + mber_nm + ", sexdetn="
				+ sexdetn + ", nlty=" + nlty + ", brthdy=" + brthdy + ", zip=" + zip + ", adres=" + adres
				+ ", adres_detail=" + adres_detail + ", telno=" + telno + ", mbtlnum=" + mbtlnum + ", email=" + email
				+ ", sms_recptn_at=" + sms_recptn_at + ", register_id=" + register_id + ", regist_dt=" + regist_dt
				+ ", Key=" + Key + "]";
	}
	
}
