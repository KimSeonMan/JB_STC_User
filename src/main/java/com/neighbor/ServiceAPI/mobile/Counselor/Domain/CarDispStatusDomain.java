package com.neighbor.ServiceAPI.mobile.Counselor.Domain;

import com.neighbor.ServiceAPI.mobile.Counselor.Page.CarDispStatusPage;

public class CarDispStatusDomain {

	private CarDispStatusPage page = new CarDispStatusPage();
	
	private String sn;
	private String mber_id;
	private String mber_nm;
	private String rcept_se_cd;
	private String rcept_se_nm;
	private String resve_date;
	private String resve_time;
	private String resve_dt;
	private String mvmn_ty_cd;
	private String mvmn_ty_nm;
	private String lon;
	private String lat;
	private String mber_name;
	private String caralcSttusCd;
	private String cnterCd;
	private String strtpnt_adres;
	private String aloc_adres;
	private String roundtrip_at;
	private String mvmn_purps_cd;
	private String wheelchair_se_cd;
	private String brdng_nmpr;
	private String rm;
	public CarDispStatusPage getPage() {
		return page;
	}
	public void setPage(CarDispStatusPage page) {
		this.page = page;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
	}
	public String getMber_nm() {
		return mber_nm;
	}
	public void setMber_nm(String mber_nm) {
		this.mber_nm = mber_nm;
	}
	public String getRcept_se_cd() {
		return rcept_se_cd;
	}
	public void setRcept_se_cd(String rcept_se_cd) {
		this.rcept_se_cd = rcept_se_cd;
	}
	public String getRcept_se_nm() {
		return rcept_se_nm;
	}
	public void setRcept_se_nm(String rcept_se_nm) {
		this.rcept_se_nm = rcept_se_nm;
	}
	public String getResve_dt() {
		return resve_dt;
	}
	public void setResve_dt(String resve_dt) {
		this.resve_dt = resve_dt;
	}
	public String getMvmn_ty_cd() {
		return mvmn_ty_cd;
	}
	public void setMvmn_ty_cd(String mvmn_ty_cd) {
		this.mvmn_ty_cd = mvmn_ty_cd;
	}
	public String getMvmn_ty_nm() {
		return mvmn_ty_nm;
	}
	public void setMvmn_ty_nm(String mvmn_ty_nm) {
		this.mvmn_ty_nm = mvmn_ty_nm;
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
	public String getResve_date() {
		return resve_date;
	}
	public void setResve_date(String resve_date) {
		this.resve_date = resve_date;
	}
	public String getResve_time() {
		return resve_time;
	}
	public void setResve_time(String resve_time) {
		this.resve_time = resve_time;
	}
	public String getMber_name() {
		return mber_name;
	}
	public void setMber_name(String mber_name) {
		this.mber_name = mber_name;
	}
	public String getCaralcSttusCd() {
		return caralcSttusCd;
	}
	public void setCaralcSttusCd(String caralcSttusCd) {
		this.caralcSttusCd = caralcSttusCd;
	}
	public String getCnterCd() {
		return cnterCd;
	}
	public void setCnterCd(String cnterCd) {
		this.cnterCd = cnterCd;
	}
	public String getStrtpnt_adres() {
		return strtpnt_adres;
	}
	public void setStrtpnt_adres(String strtpnt_adres) {
		this.strtpnt_adres = strtpnt_adres;
	}
	public String getAloc_adres() {
		return aloc_adres;
	}
	public void setAloc_adres(String aloc_adres) {
		this.aloc_adres = aloc_adres;
	}
	public String getRoundtrip_at() {
		return roundtrip_at;
	}
	public void setRoundtrip_at(String roundtrip_at) {
		this.roundtrip_at = roundtrip_at;
	}
	public String getMvmn_purps_cd() {
		return mvmn_purps_cd;
	}
	public void setMvmn_purps_cd(String mvmn_purps_cd) {
		this.mvmn_purps_cd = mvmn_purps_cd;
	}
	public String getWheelchair_se_cd() {
		return wheelchair_se_cd;
	}
	public void setWheelchair_se_cd(String wheelchair_se_cd) {
		this.wheelchair_se_cd = wheelchair_se_cd;
	}
	public String getBrdng_nmpr() {
		return brdng_nmpr;
	}
	public void setBrdng_nmpr(String brdng_nmpr) {
		this.brdng_nmpr = brdng_nmpr;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	@Override
	public String toString() {
		return "CarDispStatusDomain [page=" + page + ", sn=" + sn + ", mber_id=" + mber_id + ", mber_nm=" + mber_nm
				+ ", rcept_se_cd=" + rcept_se_cd + ", rcept_se_nm=" + rcept_se_nm + ", resve_dt=" + resve_dt
				+ ", mvmn_ty_cd=" + mvmn_ty_cd + ", mvmn_ty_nm=" + mvmn_ty_nm + ", lon=" + lon + ", lat=" + lat + "]";
	}
	
}
