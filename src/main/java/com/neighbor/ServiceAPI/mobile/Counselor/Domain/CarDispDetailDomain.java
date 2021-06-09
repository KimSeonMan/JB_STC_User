package com.neighbor.ServiceAPI.mobile.Counselor.Domain;

public class CarDispDetailDomain {
	private String rcept_se_cd;
	private String caralc_sttus_cd;
	private String cnter_cd;
	private String resve_date;
	private String resve_time;
	private String resve_dt;
	private String start_lc_crdnt_x;
	private String start_lc_crdnt_y;
	private String arvl_lc_crdnt_x;
	private String arvl_lc_crdnt_y;
	private String strtpnt_adres;			//출발지
	private String aloc_adres;				//목적지
	private String roundtrip_at;
	private String mvmn_purps_cd;			
	private String wheelchair_se_cd;
	private String brdng_nmpr;
	private String rm;
	private String mvmn_ty_cd;
	private String mber_id;
	private String chrg_cnter_cd;
	public String getRcept_se_cd() {
		return rcept_se_cd;
	}
	public void setRcept_se_cd(String rcept_se_cd) {
		this.rcept_se_cd = rcept_se_cd;
	}
	public String getCaralc_sttus_cd() {
		return caralc_sttus_cd;
	}
	public void setCaralc_sttus_cd(String caralc_sttus_cd) {
		this.caralc_sttus_cd = caralc_sttus_cd;
	}
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
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
	public String getResve_dt() {
		return resve_dt;
	}
	public void setResve_dt(String resve_dt) {
		this.resve_dt = resve_dt;
	}
	public String getStart_lc_crdnt_x() {
		return start_lc_crdnt_x;
	}
	public void setStart_lc_crdnt_x(String start_lc_crdnt_x) {
		this.start_lc_crdnt_x = start_lc_crdnt_x;
	}
	public String getStart_lc_crdnt_y() {
		return start_lc_crdnt_y;
	}
	public void setStart_lc_crdnt_y(String start_lc_crdnt_y) {
		this.start_lc_crdnt_y = start_lc_crdnt_y;
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
	public String getArvl_lc_crdnt_x() {
		return arvl_lc_crdnt_x;
	}
	public void setArvl_lc_crdnt_x(String arvl_lc_crdnt_x) {
		this.arvl_lc_crdnt_x = arvl_lc_crdnt_x;
	}
	public String getArvl_lc_crdnt_y() {
		return arvl_lc_crdnt_y;
	}
	public void setArvl_lc_crdnt_y(String arvl_lc_crdnt_y) {
		this.arvl_lc_crdnt_y = arvl_lc_crdnt_y;
	}
	public String getMvmn_ty_cd() {
		return mvmn_ty_cd;
	}
	public void setMvmn_ty_cd(String mvmn_ty_cd) {
		this.mvmn_ty_cd = mvmn_ty_cd;
	}
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
	}
	public String getChrg_cnter_cd() {
		return chrg_cnter_cd;
	}
	public void setChrg_cnter_cd(String chrg_cnter_cd) {
		this.chrg_cnter_cd = chrg_cnter_cd;
	}
	@Override
	public String toString() {
		return "CarDispDetailDomain [rcept_se_cd=" + rcept_se_cd + ", caralc_sttus_cd=" + caralc_sttus_cd
				+ ", cnter_cd=" + cnter_cd + ", resve_date=" + resve_date + ", resve_time=" + resve_time + ", resve_dt="
				+ resve_dt + ", start_lc_crdnt_x=" + start_lc_crdnt_x + ", start_lc_crdnt_y=" + start_lc_crdnt_y
				+ ", strtpnt_adres=" + strtpnt_adres + ", aloc_adres=" + aloc_adres + ", roundtrip_at=" + roundtrip_at
				+ ", mvmn_purps_cd=" + mvmn_purps_cd + ", wheelchair_se_cd=" + wheelchair_se_cd + ", brdng_nmpr="
				+ brdng_nmpr + ", rm=" + rm + "]";
	}
	
}
