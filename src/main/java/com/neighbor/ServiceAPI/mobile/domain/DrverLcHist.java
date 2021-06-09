package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

@Alias("drverLcHist")
public class DrverLcHist {

	private String colct_dt;
	private String mber_id;
	private String cnter_cd;
	private String lc_crdnt_x;
	private String lc_crdnt_y;
	private String ve;	
	private String gps_ennc;
	private String mvmn_drc;
	private String wheelchair_fixing_sttus_cd;
	public String getColct_dt() {
		return colct_dt;
	}
	public void setColct_dt(String colct_dt) {
		this.colct_dt = colct_dt;
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
	public String getVe() {
		return ve;
	}
	public void setVe(String ve) {
		this.ve = ve;
	}
	public String getGps_ennc() {
		return gps_ennc;
	}
	public void setGps_ennc(String gps_ennc) {
		this.gps_ennc = gps_ennc;
	}
	public String getMvmn_drc() {
		return mvmn_drc;
	}
	public void setMvmn_drc(String mvmn_drc) {
		this.mvmn_drc = mvmn_drc;
	}
	public String getWheelchair_fixing_sttus_cd() {
		return wheelchair_fixing_sttus_cd;
	}
	public void setWheelchair_fixing_sttus_cd(String wheelchair_fixing_sttus_cd) {
		this.wheelchair_fixing_sttus_cd = wheelchair_fixing_sttus_cd;
	}

}
