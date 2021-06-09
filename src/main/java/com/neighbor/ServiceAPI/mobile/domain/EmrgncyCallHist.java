package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

@Alias("emrgncyCallHist")
public class EmrgncyCallHist {

	private String colct_dt;
	private String mber_id;
	private String cnter_cd;
	private String lc_crdnt_x;
	private String lc_crdnt_y;
	private String adres;
	private String cnfirm_at;
	private String cnfrmr_id;
	private String cnfirm_dt;
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
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getCnfirm_at() {
		return cnfirm_at;
	}
	public void setCnfirm_at(String cnfirm_at) {
		this.cnfirm_at = cnfirm_at;
	}
	public String getCnfrmr_id() {
		return cnfrmr_id;
	}
	public void setCnfrmr_id(String cnfrmr_id) {
		this.cnfrmr_id = cnfrmr_id;
	}
	public String getCnfirm_dt() {
		return cnfirm_dt;
	}
	public void setCnfirm_dt(String cnfirm_dt) {
		this.cnfirm_dt = cnfirm_dt;
	}
	@Override
	public String toString() {
		return "EmrgncyCallHist [colct_dt=" + colct_dt + ", mber_id=" + mber_id + ", cnter_cd=" + cnter_cd
				+ ", lc_crdnt_x=" + lc_crdnt_x + ", lc_crdnt_y=" + lc_crdnt_y + ", adres=" + adres + ", cnfirm_at="
				+ cnfirm_at + ", cnfrmr_id=" + cnfrmr_id + ", cnfirm_dt=" + cnfirm_dt + "]";
	}
	
}
