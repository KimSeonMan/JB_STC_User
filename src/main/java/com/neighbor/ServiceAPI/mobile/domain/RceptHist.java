package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

@Alias("rceptHist")
public class RceptHist {

	private String resve_dt;	
	private String mber_id;	
	private String cnter_cd;	
	private String rcept_se_cd;	
	private String online_at;
	private String pbtrnsp_trnsit_at;	
	private String roundtrip_at;	
	private String mvmn_purps_cd;	
	private String wheelchair_se_cd;	
	private String mvmn_ty_cd;	
	private String brdng_nmpr;	
	private String start_lc_crdnt_x;	
	private String start_lc_crdnt_y;	
	private String arvl_lc_crdnt_x;	
	private String arvl_lc_crdnt_y;	
	private String strtpnt_adres;	
	private String aloc_adres;	
	private String chrg_cnter_cd;
	private String expect_reqre_time;	
	private String expect_mvmn_dstnc;	
	private String expect_cychg;	
	private String rm;
	private String total_count;
	private String rnum;
	private String total_mvmn_dstnc;
	private String total_cychg;
	private String year_month;
	
	public String getYear_month() {
		return year_month;
	}
	public void setYear_month(String year_month) {
		this.year_month = year_month;
	}
	public String getResve_dt() {
		return resve_dt;
	}
	public void setResve_dt(String resve_dt) {
		this.resve_dt = resve_dt;
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
	public String getRcept_se_cd() {
		return rcept_se_cd;
	}
	public void setRcept_se_cd(String rcept_se_cd) {
		this.rcept_se_cd = rcept_se_cd;
	}
	public String getOnline_at() {
		return online_at;
	}
	public void setOnline_at(String online_at) {
		this.online_at = online_at;
	}
	public String getPbtrnsp_trnsit_at() {
		return pbtrnsp_trnsit_at;
	}
	public void setPbtrnsp_trnsit_at(String pbtrnsp_trnsit_at) {
		this.pbtrnsp_trnsit_at = pbtrnsp_trnsit_at;
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
	public String getMvmn_ty_cd() {
		return mvmn_ty_cd;
	}
	public void setMvmn_ty_cd(String mvmn_ty_cd) {
		this.mvmn_ty_cd = mvmn_ty_cd;
	}
	public String getBrdng_nmpr() {
		return brdng_nmpr;
	}
	public void setBrdng_nmpr(String brdng_nmpr) {
		this.brdng_nmpr = brdng_nmpr;
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
	public String getChrg_cnter_cd() {
		return chrg_cnter_cd;
	}
	public void setChrg_cnter_cd(String chrg_cnter_cd) {
		this.chrg_cnter_cd = chrg_cnter_cd;
	}
	public String getExpect_reqre_time() {
		return expect_reqre_time;
	}
	public void setExpect_reqre_time(String expect_reqre_time) {
		this.expect_reqre_time = expect_reqre_time;
	}
	public String getExpect_mvmn_dstnc() {
		return expect_mvmn_dstnc;
	}
	public void setExpect_mvmn_dstnc(String expect_mvmn_dstnc) {
		this.expect_mvmn_dstnc = expect_mvmn_dstnc;
	}
	public String getExpect_cychg() {
		return expect_cychg;
	}
	public void setExpect_cychg(String expect_cychg) {
		this.expect_cychg = expect_cychg;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}
	public String getTotal_count() {
		return total_count;
	}
	public void setTotal_count(String total_count) {
		this.total_count = total_count;
	}
	public String getRnum() {
		return rnum;
	}
	public void setRnum(String rnum) {
		this.rnum = rnum;
	}
	public String getTotal_mvmn_dstnc() {
		return total_mvmn_dstnc;
	}
	public void setTotal_mvmn_dstnc(String total_mvmn_dstnc) {
		this.total_mvmn_dstnc = total_mvmn_dstnc;
	}
	public String getTotal_cychg() {
		return total_cychg;
	}
	public void setTotal_cychg(String total_cychg) {
		this.total_cychg = total_cychg;
	}
	@Override
	public String toString() {
		return "RceptHist [resve_dt=" + resve_dt + ", mber_id=" + mber_id + ", cnter_cd=" + cnter_cd + ", rcept_se_cd="
				+ rcept_se_cd + ", online_at=" + online_at + ", pbtrnsp_trnsit_at=" + pbtrnsp_trnsit_at
				+ ", roundtrip_at=" + roundtrip_at + ", mvmn_purps_cd=" + mvmn_purps_cd + ", wheelchair_se_cd="
				+ wheelchair_se_cd + ", mvmn_ty_cd=" + mvmn_ty_cd + ", brdng_nmpr=" + brdng_nmpr + ", start_lc_crdnt_x="
				+ start_lc_crdnt_x + ", start_lc_crdnt_y=" + start_lc_crdnt_y + ", arvl_lc_crdnt_x=" + arvl_lc_crdnt_x
				+ ", arvl_lc_crdnt_y=" + arvl_lc_crdnt_y + ", strtpnt_adres=" + strtpnt_adres + ", aloc_adres="
				+ aloc_adres + ", chrg_cnter_cd=" + chrg_cnter_cd + ", expect_reqre_time=" + expect_reqre_time
				+ ", expect_mvmn_dstnc=" + expect_mvmn_dstnc + ", expect_cychg=" + expect_cychg + ", rm=" + rm
				+ ", total_count=" + total_count + ", rnum=" + rnum + ", total_mvmn_dstnc=" + total_mvmn_dstnc
				+ ", total_cychg=" + total_cychg + ", year_month=" + year_month + "]";
	}

}