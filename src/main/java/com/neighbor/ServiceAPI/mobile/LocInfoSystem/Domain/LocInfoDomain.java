package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain;

import org.apache.ibatis.type.Alias;

import com.neighbor.ServiceAPI.mobile.LocInfoSystem.Page.LocInfoPage;

@Alias("locInfoDomain")
public class LocInfoDomain {

	private LocInfoPage page = new LocInfoPage();
	
	private String cnter_nm;
	private String mber_nm;
	private String aloc_adres;
	private String vhcle_no;
	private String drver_sttus_cd;
	private String drver_sttus_nm;
	private String mber_id;
	private String cnter_cd;
	private String wheelchair_fixing_sttus_cd;
	private String lon;
	private String lat;
	
	private String drver_nm;
	private String handi_mber_nm;
	private String trobl;
	private String handi_cnter_nm;
	
	public void setMarkerInfo(LocDrverInfoDomain info){
		this.drver_nm = info.getDrver_nm();
		this.handi_mber_nm = info.getHandi_mber_nm();
		this.trobl = info.getTrobl();
		this.handi_cnter_nm = info.getCnter_nm();
	}
	
	public String getCnter_nm() {
		return cnter_nm;
	}
	public void setCnter_nm(String cnter_nm) {
		this.cnter_nm = cnter_nm;
	}
	public String getMber_nm() {
		return mber_nm;
	}
	public void setMber_nm(String mber_nm) {
		this.mber_nm = mber_nm;
	}
	public String getAloc_adres() {
		return aloc_adres;
	}
	public void setAloc_adres(String aloc_adres) {
		this.aloc_adres = aloc_adres;
	}
	public String getVhcle_no() {
		return vhcle_no;
	}
	public void setVhcle_no(String vhcle_no) {
		this.vhcle_no = vhcle_no;
	}
	public String getDrver_sttus_cd() {
		return drver_sttus_cd;
	}
	public void setDrver_sttus_cd(String drver_sttus_cd) {
		this.drver_sttus_cd = drver_sttus_cd;
	}
	public String getDrver_sttus_nm() {
		return drver_sttus_nm;
	}
	public void setDrver_sttus_nm(String drver_sttus_nm) {
		this.drver_sttus_nm = drver_sttus_nm;
	}
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
	public String getWheelchair_fixing_sttus_cd() {
		return wheelchair_fixing_sttus_cd;
	}
	public void setWheelchair_fixing_sttus_cd(String wheelchair_fixing_sttus_cd) {
		this.wheelchair_fixing_sttus_cd = wheelchair_fixing_sttus_cd;
	}
	public LocInfoPage getPage() {
		return page;
	}
	public void setPage(LocInfoPage page) {
		this.page = page;
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
	public String getDrver_nm() {
		return drver_nm;
	}
	public void setDrver_nm(String drver_nm) {
		this.drver_nm = drver_nm;
	}
	public String getHandi_mber_nm() {
		return handi_mber_nm;
	}
	public void setHandi_mber_nm(String handi_mber_nm) {
		this.handi_mber_nm = handi_mber_nm;
	}
	public String getTrobl() {
		return trobl;
	}
	public void setTrobl(String trobl) {
		this.trobl = trobl;
	}
	public String getHandi_cnter_nm() {
		return handi_cnter_nm;
	}
	public void setHandi_cnter_nm(String handi_cnter_nm) {
		this.handi_cnter_nm = handi_cnter_nm;
	}
	
}
