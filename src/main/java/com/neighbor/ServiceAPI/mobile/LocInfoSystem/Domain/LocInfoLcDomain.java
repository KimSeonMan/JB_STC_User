package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain;

import org.apache.ibatis.type.Alias;

@Alias("locInfoLcDomain")
public class LocInfoLcDomain {
	private String cnter_cd;
	private String mber_id;
	private String lc_crdnt_x;
	private String lc_crdnt_y;
	private String drver_nm;
	private String vhcle_no;
	private String handi_mber_nm;
	private String trobl;
	private String cnter_nm;
	private String drver_sttus_cd;
	
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
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
	public String getDrver_nm() {
		return drver_nm;
	}
	public void setDrver_nm(String drver_nm) {
		this.drver_nm = drver_nm;
	}
	public String getVhcle_no() {
		return vhcle_no;
	}
	public void setVhcle_no(String vhcle_no) {
		this.vhcle_no = vhcle_no;
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
	public String getCnter_nm() {
		return cnter_nm;
	}
	public void setCnter_nm(String cnter_nm) {
		this.cnter_nm = cnter_nm;
	}
	public String getDrver_sttus_cd() {
		return drver_sttus_cd;
	}
	public void setDrver_sttus_cd(String drver_sttus_cd) {
		this.drver_sttus_cd = drver_sttus_cd;
	}
	@Override
	public String toString() {
		return "LocInfoLcDomain [cnter_cd=" + cnter_cd + ", mber_id=" + mber_id + ", lc_crdnt_x=" + lc_crdnt_x
				+ ", lc_crdnt_y=" + lc_crdnt_y + ", drver_nm=" + drver_nm + ", vhcle_no=" + vhcle_no
				+ ", handi_mber_nm=" + handi_mber_nm + ", trobl=" + trobl + ", cnter_nm=" + cnter_nm + "]";
	}
	
}
